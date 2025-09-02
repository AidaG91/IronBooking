package com.ironhack.IronBooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.IronBooking.dto.review.ReviewResponseDTO;
import com.ironhack.IronBooking.service.interfaces.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatcher.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReviewController.class)
class ReviewControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @MockBean
    ReviewService reviewService;

    // ------- TESTING GET -------
    @Test
    @DisplayName("GET /reviews")
    void getAllReviews_ok() throws Exception {
        var r1 = resp(1L, 4, "This is a review test.", 24L, "Juan", "Palomo", 300L, "La Sagrada Familia");
        var r2 = resp(3L, 2, "This is a review test 2.", 12L, "Paquita", "Salas", 299L, "La Alhambra");

        when(reviewService.getAllReviews()).thenReturn(List.of(r1, r2));

        mvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2)) // 2 because we have only prepared 2 reviews (r1 and r2).
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].rating").value(2))
                .andExpect(jsonPath("$[0].userFirstName").value("Juan"))
                .andExpect(jsonPath("$[1].userLastName").value("Salas"));

        verify(reviewService).getAllReviews();

    }

    @Test
    void getReviewById() {
    }

    @Test
    void createReview() {
    }

    @Test
    void updateReview() {
    }

    @Test
    void deleteReview() {
    }

    @Test
    void getReviewByUserId() {
    }


    // -------- HELPERS --------
    private static ReviewResponseDTO resp(Long id, Integer rating, String comment, Long userId, String userFirstName,
                                          String userLastName, Long placeId, String placeName) {
        var dto = new ReviewResponseDTO();
        dto.setId(id);
        dto.setRating(rating);
        dto.setComment(comment);
        dto.setUserId(userId);
        dto.setUserFirstName(userFirstName);
        dto.setUserLastName(userLastName);
        dto.setPlaceId(placeId);
        dto.setPlaceName(placeName);

        return dto;
    }

}