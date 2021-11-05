package at.fhv.hotelmanagement.view;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;


@Controller
public class BookingViewController {

    private static final String ALL_BOOKINGS_URL = "/bookings";
    private static final String BOOKINGS_URL = "/booking";

    private static final String CREATE_BOOKING_URL = "/bookings/create";
    private static final String ERROR_URL = "/displayerror";

    private static final String POST_BOOKING_URL = "/bookings/create";

    private static final String ALL_BOOKINGS_VIEW = "allBookings";
    private static final String BOOKING_VIEW = "booking";
    private static final String CREATE_BOOKING_VIEW = "createBooking";
    private static final String ERROR_VIEW = "errorView";

    @Autowired
    private BookingsService bookingsService;


    @GetMapping(ALL_BOOKINGS_URL)
    public String allBookings(Model model) {
        final List<BookingDTO> bookings = bookingsService.getAll();

        if(bookings.isEmpty()){
            redirectError("No Bookings found");
        }

        model.addAttribute("bookings", bookings);
        return ALL_BOOKINGS_VIEW;
    }

    @GetMapping(BOOKINGS_URL)
    public ModelAndView booking(
            @RequestParam("id") String bookingNr,
            Model model){

        final Optional<BookingDetailsDTO> bookingDetail = bookingsService.getDetailsByBookingNr(bookingNr);

        if(bookingDetail.isEmpty()){
            redirectError("Booking with id: " + bookingNr + " not found");
        }

        model.addAttribute("bookingDetail", bookingDetail.get());
        return new ModelAndView(BOOKING_VIEW);
    }



    @GetMapping(CREATE_BOOKING_URL)
    public String createBooking(Model model) {

        return CREATE_BOOKING_VIEW;
    }

    @GetMapping(ERROR_URL)
    public String displayError(@RequestParam("msg") String msg, Model model) {
        model.addAttribute("msg", msg);
        return ERROR_VIEW;
    }

    @SuppressWarnings("unused")
    private static ModelAndView redirectError(String msg) {
        return new ModelAndView("redirect:" + ERROR_URL + "?msg=" + msg);
    }

    /*
    // TODO: inject Application Service Interfaces

    @GetMapping(ALL_CUSTOMERS_URL)
    public String allCustomers(Model model) {
        // TODO: make a call to an Application Service to get all Customers
        // TODO: this is fake test data, remove when implementing
        final List<CustomerDTO> customers = Arrays.asList(
                CustomerDTO.builder()
                        .withName("Max Mustermann")
                        .withId("1")
                        .build()
        );
        model.addAttribute("customers", customers);

        return ALL_CUSTOMERS_VIEW;
    }

    @GetMapping(CUSTOMER_URL)
    public ModelAndView customer(
            @RequestParam("id") String customerId,
            Model model) {

        // TODO: make a call to an Application Service to get the customer details for customer with customerId
        // TODO: redirect to the error page in case of an error situation - use redirectError("SOME MESSAGE"); for that
        // TODO: this is fake test data, remove when implementing
        final CustomerDetailsDTO customerDetails = CustomerDetailsDTO.builder()
                .withCustomer(CustomerDTO.builder()
                        .withName("Max Mustermann")
                        .withId("1")
                        .build())
                .addAccount(AccountDetailsDTO.builder()
                        .withBalance(1000)
                        .withIban("AT12 12345 01234567890")
                        .withType(AccountType.GIRO)
                        .build())
                .addAccount(AccountDetailsDTO.builder()
                        .withBalance(1500)
                        .withIban("AT12 12345 01234567891")
                        .withType(AccountType.SAVINGS)
                        .build())
                .build();
        model.addAttribute("customer", customerDetails);
        return new ModelAndView(CUSTOMER_VIEW);
    }

    @GetMapping(ACCOUNT_URL)
    public ModelAndView accountInfo(
            @RequestParam("iban") String iban,
            @RequestParam("id") String customerId,
            @RequestParam("name") String customerName,
            Model model) {

        // TODO: make a call to an Application Service to get the Account with iban
        // TODO: redirect to the error page in case of an error situation - use redirectError("SOME MESSAGE"); for that
        // TODO: this is fake test data, remove when implementing
        final AccountDTO account = AccountDTO.builder()
                .withDetails(AccountDetailsDTO.builder()
                        .withBalance(1000)
                        .withIban(iban)
                        .withType(AccountType.GIRO)
                        .build())
                .addTXLine(TXLineDTO.builder()
                        .ofAmount(100)
                        .withIban("AT98 54321 09876543210")
                        .withName("Maria Musterfrau")
                        .atTime(LocalDateTime.now())
                        .withReference("Miete")
                        .build())
                .build();

        final AccountForm form = new AccountForm(customerId, customerName, iban);

        model.addAttribute("account", account);
        model.addAttribute("form", form);
        return new ModelAndView(ACCOUNT_VIEW);
    }

    @PostMapping(POST_DEPOSIT_URL)
    public ModelAndView depositAccount(
            @ModelAttribute AccountForm form,
            Model model) {

        // TODO: make a call to an Application Service to deposit form.getAmount() into the Account with form.getIban()
        // TODO: redirect to the error page in case of an error situation - use redirectError("SOME MESSAGE"); for that

        return redirectToAccount(form);
    }

    @PostMapping(POST_WITHDRAW_URL)
    public ModelAndView withdrawAccount(
            @ModelAttribute AccountForm form,
            Model model) {

        // TODO: make a call to an Application Service to withdraw form.getAmount() into the Account with form.getIban()
        // TODO: redirect to the error page in case of an error situation - use redirectError("SOME MESSAGE"); for that

        return redirectToAccount(form);
    }

    @PostMapping(POST_TRANSFER_URL)
    public ModelAndView transferAccount(@ModelAttribute AccountForm form,
                                        @RequestParam("receivingIban") String receivingIban,
                                        @RequestParam("reference") String reference,
                                        Model model) {

        // TODO: make a call to an Application Service to transfer form.getAmount() from an account with form.getIban() to an account with
        //       receivingIban with a specific reference
        // TODO: redirect to the error page in case of an error situation - use redirectError("SOME MESSAGE"); for that

        return redirectToAccount(form);
    }

    @GetMapping(ERROR_URL)
    public String displayError(@RequestParam("msg") String msg, Model model) {
        model.addAttribute("msg", msg);
        return ERROR_VIEW;
    }

    // NOTE: used to redirect to an error page displaying an error message
    @SuppressWarnings("unused")
    private static ModelAndView redirectError(String msg) {
        return new ModelAndView("redirect:" + ERROR_URL + "?msg=" + msg);
    }

    private static ModelAndView redirectToAccount(final AccountForm form) {
        return new ModelAndView("redirect:" +
                ACCOUNT_URL +
                "?iban=" +
                form.getIban() +
                "&id=" +
                form.getCustomerId() +
                "&name=" +
                form.getCustomerName());
    }

    */

}
