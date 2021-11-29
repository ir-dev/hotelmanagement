package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDateTime;
import java.util.Optional;

public class Price {
    // generated hibernate id
    private Long id;
    private Integer halfBoard;
    private Integer fullBoard;

    // required for hibernate
    private Price() {}

    public Price(int halfBoard, int fullBoard) {
        this.halfBoard = halfBoard;
        this.fullBoard = fullBoard;
    }

    public Integer getHalfBoard() {
        return this.halfBoard;
    }

    public Integer getFullBoard() {
        return this.fullBoard;
    }
}
