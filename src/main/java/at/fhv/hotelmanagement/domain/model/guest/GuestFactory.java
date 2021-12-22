package at.fhv.hotelmanagement.domain.model.guest;

import java.time.LocalDate;

public class GuestFactory {
    private GuestFactory() {}

    public static Guest createGuest(GuestId guestId,
                                    Organization organization,
                                    String salutation,
                                    String firstName,
                                    String lastName,
                                    LocalDate dateOfBirth,
                                    Address address,
                                    String specialNotes) throws CreateGuestException {

        // Age (Birthday) must be equal or greater than 18 years
        if (!(dateOfBirth.isBefore(LocalDate.now().minusYears(18).plusDays(1)))) {
            throw new CreateGuestException("Age (Birthday) must be equal or greater than 18 years");
        }

        return new Guest(
                guestId,
                organization,
                salutation,
                firstName,
                lastName,
                dateOfBirth,
                address,
                specialNotes
        );
    }
}
