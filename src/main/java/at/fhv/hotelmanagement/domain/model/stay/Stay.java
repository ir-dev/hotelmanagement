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
        Set<InvoiceLine> billedCategoryLineItems = this.invoices.stream()
                .flatMap(invoice -> invoice.getLineItems().stream())
                .filter(lineItem -> (lineItem.getType() == ProductType.CATEGORY))
                .collect(Collectors.toSet());

        for (Map.Entry<String, Integer> selectedCategoryRoomCount : this.selectedCategoriesRoomCount.entrySet()) {
            if (billedCategoryLineItems.stream()
                    .noneMatch(bcli ->
                            bcli.getProduct().equals(selectedCategoryRoomCount.getKey()) &&
                            bcli.getQuantity().equals(selectedCategoryRoomCount.getValue()))) {
                return false;
            }
        }

        return true;
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
        return new InvoiceNo(String.format("%s_%04d", this.stayId.getId(), this.invoices.size()+1));
    }

    public Invoice composeInvoice(List<Category> billableCategories) throws PriceCurrencyMismatchException, IllegalStateException {
        if (isBilled()) {
            throw new IllegalStateException("Stay have been already billed.");
        }

        Invoice invoice = generateInvoice(billableCategories);
        this.invoices.add(invoice);

        return invoice;
    }

    public Invoice generateInvoice(List<Category> billableCategories) throws PriceCurrencyMismatchException {
        Set<InvoiceLine> billedCategoryLineItems = this.invoices.stream()
                .flatMap(invoice -> invoice.getLineItems().stream())
                .filter(lineItem -> (lineItem.getType() == ProductType.CATEGORY))
                .collect(Collectors.toSet());

        Set<InvoiceLine> lineItems = new HashSet<>();

        for (Category billableCategory : billableCategories) {
            if (this.selectedCategoriesRoomCount.containsKey(billableCategory.getName()) &&
                    billedCategoryLineItems.stream()
                            .noneMatch(bcli -> bcli.getProduct().equals(billableCategory.getName()))) {
                lineItems.add(new InvoiceLine(
                        ProductType.CATEGORY,
                        billableCategory.getName(),
                        billableCategory.getDescription(),
                        this.selectedCategoriesRoomCount.get(billableCategory.getName()),
                        billableCategory.getFullBoardPrice()));
            }
        }

        return new Invoice(nextInvoiceNo(), lineItems, this.arrivalDate, this.departureDate, INVOICE_TAX_RATE, INVOICE_DUE_DATE_DAYS);
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
