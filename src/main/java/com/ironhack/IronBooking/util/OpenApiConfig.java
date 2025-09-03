package com.ironhack.IronBooking.util;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API IronBooking")
                        .version("1.0.0")
                        .description("This API manages a booking platform that connects owners and clients for place reservations. " +
                                "It includes features for managing users, available places, bookings, and reviews, all through resource-oriented endpoints.\n\n" +
                                "Main features:\n" +
                                "- Users: Manage users with owner and client roles. Allows CRUD operations, filtering by type and email, and retrieving associated bookings.\n" +
                                "- Places: Manage available places for booking, including search by type, capacity, country, city, and owner.\n" +
                                "- Bookings: CRUD operations for bookings, status queries, and management by user or place.\n" +
                                "- Reviews: System for ratings and comments on places, allowing users to provide feedback and consult reviews per place.\n" +
                                "- Advanced filters: Search for places by various criteria such as minimum capacity, country, city, type, etc.\n\n" +
                                "This API is designed to cover the complete lifecycle of a booking system: from managing users and places, to creating bookings, to collecting client reviews."
                        )
                        .contact(new Contact()
                                .name("Repository - IronBooking")
                                .url("https://github.com/AidaG91/IronBooking/"))
                        .license(new License()
                                .name("Licencia MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
