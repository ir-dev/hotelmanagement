package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.HotelmanagementApplication;
import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.enums.RoomState;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelmanagementApplication.class)
class HibernateCategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findCategoryRoomsByState() {
        String categoryName = "Honeymoon Suite DZ";
        List<Room> categoryRooms = this.categoryRepository.findCategoryRoomsByState(categoryName, RoomState.OCCUPIED);
        System.out.println("Occupied rooms in " + categoryName + ":");
        for(Room room: categoryRooms){
            System.out.println(room.getRoomNumber().getNumber());
        }
    }
}