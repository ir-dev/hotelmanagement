package at.fhv.hotelmanagement.domain.repositories;

import at.fhv.hotelmanagement.domain.model.Stay;
import at.fhv.hotelmanagement.domain.model.StayId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StayRepository {
    StayId nextIdentity();

    List<Stay> findAll();

    Optional<Stay> findById(StayId stayId);

    void store(Stay stay);
}
