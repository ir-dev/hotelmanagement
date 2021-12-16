package at.fhv.hotelmanagement.domain.model.stay;

import at.fhv.hotelmanagement.domain.model.PriceCurrencyMismatchException;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class Stay {
    private static final double INVOICE_TAX_RATE = 0.1;
    private static final long INVOICE_DUE_DATE_DAYS = 14L;

    // generated hibernate id
    private Long id;
    private StayId stayId;
    private BookingNo bookingNo;
    private StayState stayState;
    private LocalDateTime checkedInAt;
    private LocalDateTime checkedOutAt;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private LocalTime arrivalTime;
    private Integer numberOfPersons;
    private Map<String, Integer> selectedCategoriesRoomCount;
    private GuestId guestId;
    private PaymentInformation paymentInformation;
    private Set<Invoice> invoices;

    // required for hibernate
    private Stay() {}

    Stay(StayId stayId, BookingNo bookingNo, LocalDate arrivalDate, LocalDate departureDate, LocalTime arrivalTime, Integer numberOfPersons, Map<String, Integer> selectedCategoriesRoomCount, GuestId guestId, PaymentInformation paymentInformation) {
        this.stayId = stayId;
        this.bookingNo = bookingNo;
        this.stayState = StayState.CHECKED_IN;
        this.checkedInAt = LocalDateTime.now();
        this.checkedOutAt = null;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.arrivalTime = arrivalTime;
        this.numberOfPersons = numberOfPersons;
        this.selectedCategoriesRoomCount = selectedCategoriesRoomCount;
        this.guestId = guestId;
        this.paymentInformation = paymentInformation;
        this.invoices = new HashSet<>();
    }

    public boolean isCheckedIn() {
        return (this.stayState == StayState.CHECKED_IN);
    }

    public boolean isBilled() {
        return billableLineItemCounts().size() == 0;
    }

    public void checkout() throws BillingOpenException, IllegalStateException {
        if (!isCheckedIn()) {
            throw new IllegalStateException("Only checked-in stays can be checked-out.");
        }

        if (!isBilled()) {
            throw new BillingOpenException();
        }

        this.checkedOutAt = LocalDateTime.now();
        this.stayState = StayState.CHECKED_OUT;
    }

    private InvoiceNo nextInvoiceNo() {
        return new InvoiceNo(String.format("%s_%04d", this.stayId.getId(), this.invoices.size() + 1));
    }

    public Invoice composeInvoice(Map<Category, Integer> selectedLineItemProductsCount) throws PriceCurrencyMismatchException, IllegalStateException {
        if (isBilled()) {
            throw new IllegalStateException("Stay has already been billed.");
        }

        Invoice invoice = generateInvoice(selectedLineItemProductsCount);
        this.invoices.add(invoice);

        return invoice;
    }

    public Invoice generateInvoice(Map<Category, Integer> selectedLineItemProductsCount) throws PriceCurrencyMismatchException {
        Set<InvoiceLine> billedCategoryLineItems = billedLineItems();
        Set<InvoiceLine> lineItems = new HashSet<>();

        for (Map.Entry<Category, Integer> selectedLineItemProductCount : selectedLineItemProductsCount.entrySet()) {
            for (Map.Entry<String, Integer> billableLineItemCount : billableLineItemCounts().entrySet()) {
                if (selectedLineItemProductCount.getKey().getName().equals(billableLineItemCount.getKey())) {
                    if (selectedLineItemProductCount.getValue() <= 0 && billableLineItemCount.getValue() < selectedLineItemProductCount.getValue()) {
                        throw new RuntimeException("Selected position not billable");
                    }

                    lineItems.add(new InvoiceLine(
                            ProductType.CATEGORY,
                            selectedLineItemProductCount.getKey().getName(),
                            selectedLineItemProductCount.getKey().getDescription(),
                            selectedLineItemProductCount.getValue(),
                            selectedLineItemProductCount.getKey().getFullBoardPrice()));
                }
            }
        }

        return new Invoice(nextInvoiceNo(), lineItems, this.arrivalDate, this.departureDate, INVOICE_TAX_RATE, INVOICE_DUE_DATE_DAYS);
    }

    private Set<InvoiceLine> billedLineItems() {
        Map<String, Integer> billedCategoryCounts = new HashMap<>();
        List<InvoiceLine> lineCategoryItems = new ArrayList<>();

        for (Invoice invoice : this.invoices) {
            for (InvoiceLine lineItem : invoice.getLineItems()) {
                if (lineItem.getType() == ProductType.CATEGORY) {
                    lineCategoryItems.add(lineItem);

                    String lineItemCategoryName = lineItem.getProduct();
                    Integer lineItemQuantity = lineItem.getQuantity();

                    if (billedCategoryCounts.containsKey(lineItemCategoryName)) {
                        Integer quantityBefore = billedCategoryCounts.get(lineItemCategoryName);
                        billedCategoryCounts.put(lineItemCategoryName, quantityBefore + lineItemQuantity);
                    } else {
                        billedCategoryCounts.put(lineItemCategoryName, lineItemQuantity);
                    }
                }
            }
        }

        Set<InvoiceLine> billedLineItems = new HashSet<>();

        for (Map.Entry<String, Integer> billedLineItem : billedCategoryCounts.entrySet()) {
            for (InvoiceLine lineCategoryItem : lineCategoryItems) {
                if (lineCategoryItem.getProduct().equals(billedLineItem.getKey())) {
                    billedLineItems.add(new InvoiceLine(ProductType.CATEGORY,
                            lineCategoryItem.getProduct(),
                            lineCategoryItem.getDescription(),
                            billedLineItem.getValue(),
                            lineCategoryItem.getPrice()));
                }
            }
        }

        return billedLineItems;
    }

    // Line items that are not paid fully (selectedCategoryRoomsCount - billedLineItems)
    public Map<String, Integer> billableLineItemCounts() {
        /*
            billableLineItems -> positionen, die noch offen sind
            1. selectedCategoryRoomsCount iterieren -> jeweils prüfen, ob entsprechendes billedLineItem vorhanden
            -> wenn ja: neues LineItem mit "subtrahierter Quantity"
            -> wenn nein: neues LineItem mit voller Quantity von selectedCategoryRoomsCount
         */
        Map<String, Integer> billableLineItemCounts = new HashMap<>();
        Set<InvoiceLine> billedLineItems = billedLineItems();

        for (Map.Entry<String, Integer> selectedCategoryRoomCount : this.selectedCategoriesRoomCount.entrySet()) {
            String categoryName = selectedCategoryRoomCount.getKey();
            Integer roomCount = selectedCategoryRoomCount.getValue();

            boolean lineItemFound = false;
            for (InvoiceLine billedLineItem : billedLineItems) {

                if (billedLineItem.getType() == ProductType.CATEGORY && billedLineItem.getProduct().equals(categoryName)) {
                    int toBillCount = roomCount - billedLineItem.getQuantity();

                    if (toBillCount > 0) {
                        billableLineItemCounts.put(categoryName, toBillCount);
                    }

                    lineItemFound = true;
                }
            }

            if (!lineItemFound) {
                billableLineItemCounts.put(categoryName, roomCount);
            }
        }

        return billableLineItemCounts;
    }

    public Integer getNumberOfBookedRooms() {
        return this.selectedCategoriesRoomCount.values().stream().mapToInt(i->i).sum();
    }


    public StayId getStayId() {
        return this.stayId;
    }

    public Optional<BookingNo> getBookingNo() {
        return Optional.ofNullable(this.bookingNo);
    }

    public StayState getStayState() {
        return this.stayState;
    }

    public LocalDateTime getCheckedInAt() {
        return this.checkedInAt;
    }

    public Optional<LocalDateTime> getCheckedOutAt() {
        return Optional.ofNullable(this.checkedOutAt);
    }

    public LocalDate getArrivalDate() {
        return this.arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return this.departureDate;
    }

    public LocalTime getArrivalTime() {
        return this.arrivalTime;
    }

    public Integer getNumberOfPersons() {
        return this.numberOfPersons;
    }

    public Map<String, Integer> getSelectedCategoriesRoomCount() {
        return this.selectedCategoriesRoomCount;
    }

    public GuestId getGuestId() {
        return this.guestId;
    }

    public PaymentInformation getPaymentInformation() {
        return this.paymentInformation;
    }

    public Set<Invoice> getInvoices() {
        return Collections.unmodifiableSet(this.invoices);
    }
}
