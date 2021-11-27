package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDateTime;
import java.util.Optional;

public class Price {
    // generated hibernate id
    private Long id;
    private Integer singleOccupancy;
    private Integer multipleOccupancy;

    // required for hibernate
    private Price() {}

    public Price(Integer singleOccupancy) {
        this(singleOccupancy, null);
    }

    public Price(Integer singleOccupancy, Integer multipleOccupancy) {
        this.singleOccupancy = singleOccupancy;
        this.multipleOccupancy = multipleOccupancy;
    }

    public Integer getSingleOccupancy() {
        return this.singleOccupancy;
    }


    public Optional<Integer> getMultipleOccupancy() {
        return Optional.ofNullable(this.multipleOccupancy);
    }
}
