package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.application.dto.CategoryDTO;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingState;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import at.fhv.hotelmanagement.domain.model.stay.Stay;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.repositories.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CategoryServiceImpl implements CategoryService {
    private static final double OVERBOOKING_RATIO = 0.1D;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    StayRepository stayRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ResourceLoader resourceLoader;

    @Transactional(readOnly = true)
    @Override
    public List<AvailableCategoryDTO> availableCategoriesForBooking(LocalDate arrivalDate, LocalDate departureDate) {
        List<Stay> stays = this.stayRepository.findAll();
        List<Booking> bookings = this.bookingRepository.findAll();
        List<Category> categories = this.categoryRepository.findAll();
        Map<String, Integer> reservedCategoriesRoomCount = new HashMap<>();

        for (Stay stay : stays) {
            if (stay.isCheckedIn()) {
                LocalDate arrDate = stay.getArrivalDate();
                LocalDate depDate = stay.getDepartureDate();
                Map<String, Integer> selectedCategoriesRoomCount = stay.getSelectedCategoriesRoomCount();

                // (analogue to room.isAvailableForPeriod(LocalDate fromDate, LocalDate toDate))
                for (Map.Entry<String, Integer> e : selectedCategoriesRoomCount.entrySet()) {
                    String k = e.getKey();
                    Integer v = e.getValue();

                    if (arrivalDate.compareTo(depDate) < 0 && departureDate.compareTo(arrDate) > 0) {
                        // recognise this bookings categories as reserved
                        reservedCategoriesRoomCount.put(k, v); // no duplicate keys at this point
                    }
                }
            }
        }

        for (Booking booking : bookings) {
            if (booking.getBookingState() == BookingState.PENDING) {
                LocalDate arrDate = booking.getArrivalDate();
                LocalDate depDate = booking.getDepartureDate();
                Map<String, Integer> selectedCategoriesRoomCount = booking.getSelectedCategoriesRoomCount();

                // (analogue to room.isAvailableForPeriod(LocalDate fromDate, LocalDate toDate))
                for (Map.Entry<String, Integer> e : selectedCategoriesRoomCount.entrySet()) {
                    String k = e.getKey();
                    Integer v = e.getValue();

                    if (arrivalDate.compareTo(depDate) < 0 && departureDate.compareTo(arrDate) > 0) {
                        // recognise this bookings categories as reserved
                        if (!reservedCategoriesRoomCount.containsKey(k)) {
                            reservedCategoriesRoomCount.put(k, v);
                        } else {
                            reservedCategoriesRoomCount.put(k, reservedCategoriesRoomCount.get(k) + v);
                        }
                    }
                }
            }
        }

        // allAvailableCategoriesRoomCount * floor(1 + OVERBOOKING_RATIO) = bookableCategoriesRoomCount
        Map<String, Integer> allAvailableCategoriesRoomCount = categories.stream()
                .collect(Collectors.toMap(Category::getName, c -> c.getAllRoomNumbers().size()));
        Map<String, Integer> bookableCategoriesRoomCount = new HashMap<>();
        for (Map.Entry<String, Integer> category : allAvailableCategoriesRoomCount.entrySet()) {
            // because of the rounding off, it is not always guaranteed that the overbooking rate
            // can be reached exactly, but it is guaranteed that it will not be exceeded
            bookableCategoriesRoomCount.put(category.getKey(), (int) (category.getValue() * (1 + OVERBOOKING_RATIO)));
        }

        // bookableCategoriesRoomCount - reservedCategoriesRoomCount = availableCategoriesRoomCount
        Map<String, Integer> availableCategoriesRoomCount = new HashMap<>();
        for (Map.Entry<String, Integer> category : bookableCategoriesRoomCount.entrySet()) {
            String k = category.getKey();
            Integer v =  category.getValue();

            if (!reservedCategoriesRoomCount.containsKey(k)) {
                availableCategoriesRoomCount.put(k, v);
            } else {
                int availableCategoryRoomCount = v - reservedCategoriesRoomCount.get(k);

                if (availableCategoryRoomCount > 0) {
                    availableCategoriesRoomCount.put(k, availableCategoryRoomCount);
                }
            }
        }

        List<AvailableCategoryDTO> availableCategoriesDto = new ArrayList<>();
        for (Category category : categories) {
            String k = category.getName();

            if (availableCategoriesRoomCount.containsKey(k)) {
                int availableRoomsCount = availableCategoriesRoomCount.get(k);

                if (availableRoomsCount > 0) {
                    availableCategoriesDto.add(
                            buildAvailableCategoryDto(category, availableRoomsCount)
                    );
                }
            }
        }

        return availableCategoriesDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AvailableCategoryDTO> availableCategories(LocalDate arrivalDate, LocalDate departureDate) {
        List<Category> categories = this.categoryRepository.findAll();
        List<AvailableCategoryDTO> availableCategoriesDto = new ArrayList<>();

        for (Category category : categories) {
            int availableRoomsCount = category.getAvailableRoomsCount(arrivalDate, departureDate);

            if (availableRoomsCount > 0) {
                availableCategoriesDto.add(
                        buildAvailableCategoryDto(category, availableRoomsCount)
                );
            }
        }

        return availableCategoriesDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDTO> allCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        List<CategoryDTO> allCategoriesDto = new ArrayList<>();

        for (Category category : categories) {
            allCategoriesDto.add(buildCategoryDto(category));
        }

        return allCategoriesDto;
    }

    @Transactional
    @Override
    public void manageCategory(String categoryName, String roomNumber, String roomState) throws IllegalArgumentException {
        Optional<Category> categoryOpt = this.categoryRepository.findByName(categoryName);
        if (categoryOpt.isEmpty()) {
            throw new IllegalArgumentException("Category not found");
        }
        Category category = categoryOpt.get();
        category.manageRoom(
                category.getAllRoomNumbers().stream()
                        .filter((roomNumber1 -> roomNumber1.getNumber().equalsIgnoreCase(roomNumber)))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Room number not found")),
                RoomState.valueOf(roomState));
    }

    private AvailableCategoryDTO buildAvailableCategoryDto(Category category, int availableRoomsCount) {
        String categoryNameBase64 = Base64.getUrlEncoder().encodeToString(category.getName().getBytes());
        Resource resource = this.resourceLoader.getResource("classpath:static/assets/images/category/" +categoryNameBase64+ ".jpg");

        String resourceUrl = "/assets/images/category/" +categoryNameBase64+ ".jpg";

        return AvailableCategoryDTO.builder()
                .withName(category.getName())
                .withDescription(category.getDescription())
                .withAvailableRoomsCount(availableRoomsCount)
                .withPrice(category.getFullBoardPrice())
                .withImageUrl(resourceUrl)
                .build();
    }

    private CategoryDTO buildCategoryDto(Category category) {
        return CategoryDTO.builder()
                .withName(category.getName())
                .withRooms(category.getAllRoomNumbersWithRoomStates())
                .build();
    }

}
