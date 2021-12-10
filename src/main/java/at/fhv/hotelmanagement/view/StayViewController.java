package at.fhv.hotelmanagement.view;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.api.StayService;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.InvoiceDTO;
import at.fhv.hotelmanagement.application.dto.StayDTO;
import at.fhv.hotelmanagement.application.impl.EntityNotFoundException;
import at.fhv.hotelmanagement.domain.model.PriceCurrencyMismatchException;
import at.fhv.hotelmanagement.domain.model.guest.CreateGuestException;
import at.fhv.hotelmanagement.domain.model.stay.CreateStayException;
import at.fhv.hotelmanagement.domain.model.category.RoomAssignmentException;
import at.fhv.hotelmanagement.domain.model.stay.BillingOpenException;
import at.fhv.hotelmanagement.view.forms.StayForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;

import static at.fhv.hotelmanagement.view.GenericViewController.redirect;
import static at.fhv.hotelmanagement.view.GenericViewController.redirectError;

@Controller
public class StayViewController {
    // stays urls
    private static final String ALL_STAYS_URL = "/stays";
    private static final String ALL_STAY_INVOICES_URL = "/stays/invoices";
    private static final String CREATE_STAY_INVOICE_URL = "/stays/invoices/create";
    private static final String STAY_INVOICE_URL = "/stays/invoice";
    private static final String CREATE_STAY_URL = "/checkin";
    private static final String CHECKOUT_STAY_URL = "/checkout";
    private static final String STAY_URL = "/stay";

    // stays views
    private static final String ALL_STAYS_VIEW = "allStays";
    private static final String ALL_STAY_INVOICES_VIEW = "allInvoices";
    private static final String INVOICE_INTERMEDIARY_VIEW = "invoicePreview";
    private static final String INVOICE_VIEW = "invoice";
    private static final String CREATE_STAY_VIEW = "createStay";
    private static final String STAY_VIEW = "stay";

    // create stay steps
    private static final String CREATE_STAY_STAY_DETAILS_STEP = "enterStayDetails";
    private static final String CREATE_STAY_ROOM_CATEGORIES_STEP = "enterRoomCategories";
    private static final String CREATE_STAY_GUEST_DETAILS_STEP = "enterGuestDetails";
    private static final String CREATE_STAY_PAYMENT_STEP = "enterPayment";
    private static final String CREATE_STAY_SUMMARY_STEP = "confirmSummary";
    private static final String CREATE_STAY_STORE_STEP = "store";
    private static final Set<String> wizardSteps = Set.of(
            CREATE_STAY_STAY_DETAILS_STEP,
            CREATE_STAY_ROOM_CATEGORIES_STEP,
            CREATE_STAY_GUEST_DETAILS_STEP,
            CREATE_STAY_PAYMENT_STEP,
            CREATE_STAY_SUMMARY_STEP,
            CREATE_STAY_STORE_STEP
    );

    @Autowired
    private BookingsService bookingsService;

    @Autowired
    private StayService stayService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(ALL_STAYS_URL)
    public String allStays(
            @RequestParam("msg") Optional<String> msg,
            Model model) {
        final List<StayDTO> stays = this.stayService.allStays();

        if (msg.isPresent()) {
            model.addAttribute("msg", msg);
        }

        model.addAttribute("stays", stays);

        return ALL_STAYS_VIEW;
    }

    private ModelAndView createStayForWalkInForm(Model model) {

        model.addAttribute("step", "enterStayDetails");
        model.addAttribute("form", new StayForm());
        model.addAttribute("arrivalDate", LocalDate.now());

        return new ModelAndView(CREATE_STAY_VIEW);
    }

    private ModelAndView createStayForBookingForm(String bookingNo, Model model) {

        final Optional<BookingDTO> optBooking = this.bookingsService.bookingByBookingNo(bookingNo);

        if (optBooking.isEmpty()) {
            return redirectError("Booking with no.: " + bookingNo + " not found");
        }

        StayForm form = new StayForm();
        form.addBooking(optBooking.get());

        model.addAttribute("step", "enterStayDetails");
        model.addAttribute("bookingNo", bookingNo);
        model.addAttribute("form", form);
        model.addAttribute("arrivalDate", LocalDate.now());

        return new ModelAndView(CREATE_STAY_VIEW);
    }

    @GetMapping(CREATE_STAY_URL)
    public ModelAndView createStayForm(
            @RequestParam("bookingNo") Optional<String> optBookingNo,
            Model model) {

        return optBookingNo.isPresent()
                ? createStayForBookingForm(optBookingNo.get(), model)
                : createStayForWalkInForm(model);
    }

    @PostMapping(CREATE_STAY_URL)
    public ModelAndView createStay(
            @RequestParam("step") String step,
            @RequestParam("bookingNo") Optional<String> optBookingNo,
            @ModelAttribute StayForm form,
            Model model) {

        // check if form step is a valid step of wizard
        if (!wizardSteps.contains(step)) {
            return redirectError("Invalid step in create stay wizard.");
        }

        String bookingNo = null;
        if (optBookingNo.isPresent()) {
            bookingNo = optBookingNo.get();

            Optional<BookingDTO> optBooking = this.bookingsService.bookingByBookingNo(bookingNo);

            if (optBooking.isEmpty()) {
                return redirectError("Booking with no.: " + bookingNo + " not found");
            }

            form.addBooking(optBooking.get());
            model.addAttribute("bookingNo", bookingNo);
        }

        switch (step) {
            case CREATE_STAY_STAY_DETAILS_STEP:
                model.addAttribute("arrivalDate", LocalDate.now());
                break;

            case CREATE_STAY_ROOM_CATEGORIES_STEP:
                // Fetch categories in given stay timespan for which rooms are available
                List<AvailableCategoryDTO> availableCategories = this.categoryService.availableCategories(LocalDate.now(), form.getDepartureDate());

                // Attach to "enter room categories" view
                model.addAttribute("categories", availableCategories);
                break;

            case CREATE_STAY_STORE_STEP:
                try {
                    if (bookingNo != null) {
                        this.stayService.createStayForBooking(bookingNo, form);
                    } else {
                        this.stayService.createStayForWalkIn(form);
                    }

                } catch (CreateStayException | CreateGuestException | RoomAssignmentException e) {
                    return redirectError(e.getMessage());
                }
                return redirect(ALL_STAYS_URL, "Check-In successful");
        }

        model.addAttribute("step", step);
        model.addAttribute("form", form);

        return new ModelAndView(CREATE_STAY_VIEW);
    }

    @GetMapping(STAY_URL)
    public ModelAndView stay(
            @RequestParam("id") String stayId,
            Model model) {

        Optional<StayDTO> stay = this.stayService.stayByStayId(stayId);

        if (stay.isEmpty()) {
            return redirectError("Stay with id.: " + stayId + " not found");
        }

        model.addAttribute("stay", stay.get());

        return new ModelAndView(STAY_VIEW);
    }

    @GetMapping(CHECKOUT_STAY_URL)
    public ModelAndView checkoutStay(
            @RequestParam("stayId") String stayId) {

        try {
            this.stayService.checkoutStay(stayId);

        } catch (EntityNotFoundException e) {
            return redirectError(e.getMessage());
        } catch (IllegalStateException e) {
            return redirectError("Stay with id.: " + stayId + " is not checked-in and therefore can't be checked-out");
        } catch (BillingOpenException e) {
            Map<String, String> redirectParams = new HashMap<>();
            redirectParams.put("stayId", stayId);

            return redirect(ALL_STAY_INVOICES_URL, redirectParams);
        }

        return redirect(ALL_STAYS_URL, "Stay checked-out successfully");
    }

    @GetMapping(ALL_STAY_INVOICES_URL)
    public ModelAndView allInvoices(
            @RequestParam("stayId") String stayId,
            Model model) {

        InvoiceDTO openPositionsInvoicePreview;
        List<InvoiceDTO> invoices;
        try {
            openPositionsInvoicePreview = this.stayService.chargeStayPreview(stayId);
            invoices = this.stayService.allStayInvoices(stayId);

        } catch (EntityNotFoundException e) {
            return redirectError(e.getMessage());
        } catch (PriceCurrencyMismatchException e) {
            return redirectError("The invoice for this stay cannot currently be generated because the product prices set for the stay have different currencies.");
        }

        model.addAttribute("isCheckedOut", this.stayService.stayByStayId(stayId).get().checkedOutAt());
        model.addAttribute("openPositionsInvoicePreview", openPositionsInvoicePreview);
        model.addAttribute("invoices", invoices);

        return new ModelAndView(ALL_STAY_INVOICES_VIEW);
    }

    // if flag "isPreview" is set to "true" no real invoice is created!
    @PostMapping(CREATE_STAY_INVOICE_URL)
    public ModelAndView createInvoice(
            @RequestParam("stayId") String stayId,
            @RequestParam(value="preview", required = false) boolean isPreview,
            Model model) {

        InvoiceDTO invoiceDto = null;
        try {
            if (!isPreview) {
                Map<String, String> redirectParams = new HashMap<>();
                redirectParams.put("no", this.stayService.chargeStay(stayId));

                return redirect(STAY_INVOICE_URL, redirectParams);
            }

            invoiceDto = this.stayService.chargeStayPreview(stayId);

        } catch (EntityNotFoundException e) {
            return redirectError(e.getMessage());
        } catch (PriceCurrencyMismatchException e) {
            return redirectError("The invoice for this stay cannot currently be generated because the product prices set for the stay have different currencies.");
        }

        model.addAttribute("invoice", invoiceDto);

        return new ModelAndView(INVOICE_INTERMEDIARY_VIEW);
    }

    @GetMapping(STAY_INVOICE_URL)
    public ModelAndView invoice(
            @RequestParam("no") String invoiceNo,
            Model model) {

        Optional<InvoiceDTO> optInvoiceDTO = this.stayService.invoiceByInvoiceNo(invoiceNo);

        if (optInvoiceDTO.isEmpty()) {
            return redirectError("Invoice with no.: " + invoiceNo + " not found");
        }

        model.addAttribute("invoice", optInvoiceDTO.get());

        return new ModelAndView(INVOICE_VIEW);
    }
}
