package com.ironhack.IronBooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.IronBooking.dto.review.ReviewRequestDTO;
import com.ironhack.IronBooking.dto.review.ReviewResponseDTO;
import com.ironhack.IronBooking.service.interfaces.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    @DisplayName(("GET /reviews/{id}"))
    void getReviewById_ok() throws Exception {
        var review = resp(2L, 3, "We are testing getting review by id", 24L, "Colin", "Firth", 22L, "Barcelona Studio" +
                " 3");


        when(reviewService.getReviewById(2L)).thenReturn(review);

        mvc.perform(get("/reviews/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2L));

        verify(reviewService).getReviewById(2L);
    }

    // ------- TESTING POST -------
    @Test
    @DisplayName("POST")
    void createReview() throws Exception {
        var req = new ReviewRequestDTO();
        req.setRating(4);
        req.setComment("Testing that this will be posted.");
        req.setPlaceId(1L);
        req.setUserId(11L);

        var created = resp(1L, 4, "Testing that this will be posted.", 11L, "Test", "User", 1L, "Test Place");
        when(reviewService.createReview(any())).thenReturn(created);


        var result = mvc.perform(
                        post("/reviews")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(req))
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/reviews/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.comment").value("Testing that this will be posted."))
                .andReturn();


        ArgumentCaptor<ReviewRequestDTO> captor = ArgumentCaptor.forClass(ReviewRequestDTO.class);
        verify(reviewService).createReview((captor.capture()));
        var sent = captor.getValue();
        assertThat(sent.getRating()).isEqualTo(4);
        assertThat(sent.getComment()).isEqualTo("Testing that this will be posted.");

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