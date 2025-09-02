package com.ironhack.IronBooking.util;

import com.ironhack.IronBooking.enums.*;
import com.ironhack.IronBooking.model.*;
import com.ironhack.IronBooking.model.place.Place;
import com.ironhack.IronBooking.model.vo.Address;
import com.ironhack.IronBooking.model.vo.Price;
import com.ironhack.IronBooking.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    // Añade aquí los repositorios cuando estén implementados:
    private final UserRepository userRepository;
    // private final BookingRepository bookingRepository;
    private final PlaceRepository placeRepository;
    // private final ReviewRepository reviewRepository;

    @Override
    public void run(String... args) {
        // ---------------------------------------
        // 1) Users: 5 CLIENT + 5 OWNER (если пусто)
        // ---------------------------------------
        if (userRepository.count() == 0) {
            // CLIENTS
            User c1 = User.builder().firstName("Alice").lastName("Smith")
                    .email("alice@mail.com").passwordHash("pass1").userType(UserType.CLIENT).build();
            User c2 = User.builder().firstName("Charlie").lastName("Davis")
                    .email("charlie@mail.com").passwordHash("pass2").userType(UserType.CLIENT).build();
            User c3 = User.builder().firstName("Emily").lastName("Johnson")
                    .email("emily@mail.com").passwordHash("pass3").userType(UserType.CLIENT).build();
            User c4 = User.builder().firstName("Frank").lastName("Wilson")
                    .email("frank@mail.com").passwordHash("pass4").userType(UserType.CLIENT).build();
            User c5 = User.builder().firstName("Grace").lastName("Lee")
                    .email("grace@mail.com").passwordHash("pass5").userType(UserType.CLIENT).build();

            // OWNERS
            User o1 = User.builder().firstName("Bob").lastName("Brown")
                    .email("bob@mail.com").passwordHash("pass6").userType(UserType.OWNER).build();
            User o2 = User.builder().firstName("Diana").lastName("Miller")
                    .email("diana@mail.com").passwordHash("pass7").userType(UserType.OWNER).build();
            User o3 = User.builder().firstName("Ethan").lastName("Clark")
                    .email("ethan@mail.com").passwordHash("pass8").userType(UserType.OWNER).build();
            User o4 = User.builder().firstName("Fiona").lastName("Adams")
                    .email("fiona@mail.com").passwordHash("pass9").userType(UserType.OWNER).build();
            User o5 = User.builder().firstName("George").lastName("King")
                    .email("george@mail.com").passwordHash("pass10").userType(UserType.OWNER).build();

            userRepository.saveAll(List.of(c1, c2, c3, c4, c5, o1, o2, o3, o4, o5));
            System.out.println("+++++++++++++++ Sample users inserted: 5 CLIENT, 5 OWNER.");
        }

        // Берём ровно 5 владельцев (если вдруг меньше — создадим недостающих)
        List<User> owners = userRepository.findByUserType(UserType.OWNER);
        while (owners.size() < 5) {
            int idx = owners.size() + 1;
            owners.add(userRepository.save(User.builder()
                    .firstName("Owner").lastName("Auto" + idx)
                    .email("owner.auto" + idx + "@mail.com")
                    .passwordHash("pass" + (10 + idx))
                    .userType(UserType.OWNER).build()));
        }
        User o1 = owners.get(0), o2 = owners.get(1), o3 = owners.get(2), o4 = owners.get(3), o5 = owners.get(4);

        // ---------------------------------------
        // 2) Places: 5 Barcelona, 5 Malaga, 5 Madrid (если пусто)
        // ---------------------------------------
        if (placeRepository.count() == 0) {
            List<Place> places = List.of(
                    // Barcelona (owners rotate)
                    newPlace("Barcelona Loft 1",    "Barcelona", "Spain", 120.00, PlaceType.APARTMENT,    3, o1),
                    newPlace("Barcelona Studio 2",  "Barcelona", "Spain",  90.00, PlaceType.STUDIO,       2, o2),
                    newPlace("Barcelona Office 3",  "Barcelona", "Spain", 150.00, PlaceType.OFFICE,       6, o3),
                    newPlace("Barcelona Meeting 4", "Barcelona", "Spain",  80.00, PlaceType.MEETING_ROOM, 8, o4),
                    newPlace("Barcelona House 5",   "Barcelona", "Spain", 200.00, PlaceType.HOUSE,        5, o5),

                    // Malaga
                    newPlace("Malaga Apartment 1",  "Malaga",    "Spain", 110.00, PlaceType.APARTMENT,    3, o1),
                    newPlace("Malaga Studio 2",     "Malaga",    "Spain",  85.00, PlaceType.STUDIO,       2, o2),
                    newPlace("Malaga Office 3",     "Malaga",    "Spain", 140.00, PlaceType.OFFICE,       5, o3),
                    newPlace("Malaga Meeting 4",    "Malaga",    "Spain",  75.00, PlaceType.MEETING_ROOM, 6, o4),
                    newPlace("Malaga House 5",      "Malaga",    "Spain", 180.00, PlaceType.HOUSE,        4, o5),

                    // Madrid
                    newPlace("Madrid Apartment 1",  "Madrid",    "Spain", 130.00, PlaceType.APARTMENT,    4, o1),
                    newPlace("Madrid Studio 2",     "Madrid",    "Spain",  95.00, PlaceType.STUDIO,       2, o2),
                    newPlace("Madrid Office 3",     "Madrid",    "Spain", 160.00, PlaceType.OFFICE,       8, o3),
                    newPlace("Madrid Meeting 4",    "Madrid",    "Spain",  85.00, PlaceType.MEETING_ROOM,10, o4),
                    newPlace("Madrid House 5",      "Madrid",    "Spain", 220.00, PlaceType.HOUSE,        6, o5)
            );

            placeRepository.saveAll(places);
            System.out.println("+++++++++++++++ Sample places inserted: 5 Barcelona, 5 Malaga, 5 Madrid (rotated across 5 owners).");
        }
    }


    private Place newPlace(String name, String city, String country,
                           double amount, PlaceType type, int capacity, User owner) {
        Address address = Address.builder()
                .city(city)
                .country(country)
                .build();

        Price price = Price.builder()
                .amount(new BigDecimal(String.format(Locale.ROOT, "%.2f", amount)))
                .currency("EUR")
                .build();

        Place p = Place.builder()
                .placeType(type)
                .capacity(capacity)
                .build();
        p.setName(name);
        p.setAddress(address);
        p.setPrice(price);
        p.setOwner(owner);
        return p;
    }

    // 3 - Create Bookings
    //     // TODO: Implement sample bookings, relate with users and places
    //     // Booking booking1 = Booking.builder()...build();
    //     // bookingRepository.saveAll(List.of(booking1, ...));
    //     System.out.println("+++++++++++++++ Sample bookings inserted correctly into the database.");

    // 4 - Create Reviews
    //     // TODO: Implement sample reviews, relate with users (author), bookings/places
    //     // Review review1 = Review.builder()...build();
    //     // reviewRepository.saveAll(List.of(review1, ...));
    //     System.out.println("+++++++++++++++ Sample reviews inserted correctly into the database.");

}

