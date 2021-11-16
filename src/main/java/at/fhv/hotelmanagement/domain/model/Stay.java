package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.Board;

public class Stay {
    // generated hibernate id
    private Long id;
    private StayId stayId;
    private String name;
    private String description;
    private Board board;

    // required for hibernate
    private Stay() {}

    public Stay(StayId stayId, String name, String description, Board board) {
        this.stayId = stayId;
        this.name = name;
        this.description = description;
        this.board = board;
    }

    public StayId getStayId() {
        return this.stayId;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Board getBoard() {
        return this.board;
    }
}