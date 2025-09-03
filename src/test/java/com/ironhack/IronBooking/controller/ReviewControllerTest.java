package com.ironhack.IronBooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.IronBooking.dto.review.ReviewRequestDTO;
import com.ironhack.IronBooking.dto.review.ReviewResponseDTO;
import com.ironhack.IronBooking.dto.review.ReviewUpdateDTO;
import com.ironhack.IronBooking.service.interfaces.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
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

    // ------- TESTING GET /reviews -------
    @Test
    @DisplayName("GET /reviews -> 200 OK with list of reviews")
    void getAllReviews_ok() throws Exception {
        var r1 = resp(1L, 4, "Muy bueno", 24L, "Juan", "Palomo", 300L, "La Sagrada Familia");
        var r2 = resp(3L, 2, "Regular", 12L, "Paquita", "Salas", 299L, "La Alhambra");

        when(reviewService.getAllReviews()).thenReturn(List.of(r1, r2));

        mvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2)) // 2 because we have only prepared 2 reviews (r1 and r2).
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].userLastName").value("Salas"));

        verify(reviewService).getAllReviews();
    }

    // ------- TESTING GET /reviews/{id} -------
    @Test
    @DisplayName("GET /reviews/{id} -> 200 OK with review")
    void getReviewById_ok() throws Exception {
        var review = resp(2L, 3, "Interesting", 24L, "Colin", "Firth", 22L, "Barcelona Studio 3");

        when(reviewService.getReviewById(2L)).thenReturn(review);

        mvc.perform(get("/reviews/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.placeName").value("Barcelona Studio 3"));

        verify(reviewService).getReviewById(2L);
    }

    // ------- TESTING POST /reviews -------
    @Test
    @DisplayName("POST /reviews -> 201 Created + Location when body is valid")
    void createReview_created_withLocation() throws Exception {
        var req = new ReviewRequestDTO();
        req.setRating(5);
        req.setComment("Nice place");
        req.setPlaceId(10L);
        req.setUserId(20L);

        var created = resp(1L, 5, "Nice place", 20L, "Ana", "García", 10L, "Museo del Prado");
        when(reviewService.createReview(any())).thenReturn(created);

        var result = mvc.perform(post("/reviews")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/reviews/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.comment").value("Nice place"))
                .andReturn();


        ArgumentCaptor<ReviewRequestDTO> captor = ArgumentCaptor.forClass(ReviewRequestDTO.class);
        verify(reviewService).createReview((captor.capture()));
        var sent = captor.getValue();
        assertThat(sent.getRating()).isEqualTo(5);
        assertThat(sent.getComment()).isEqualTo("Nice place");
    }

    @Test
    @DisplayName("POST /reviews -> 400 Bad Request if body doesn't fulfil @Valid")
    void createReview_badRequest_whenInvalidBody() throws Exception {
        var req = new ReviewRequestDTO();
        req.setRating(6); // out of range
        req.setComment("x".repeat(600)); // too long
        req.setPlaceId(null); // mandatory
        req.setUserId(null);  // mandatory

        mvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isBadRequest());

        verify(reviewService, never()).createReview(any());
    }

    // -------- TESTING PUT /reviews/{id} --------
    @Test
    @DisplayName("PUT /reviews/{id} -> 200 OK when body is valid")
    void updateReview_ok() throws Exception {
        var req = new ReviewUpdateDTO();
        req.setRating(4);
        req.setComment("Updated");

        var updated = resp(2L, 4, "Updated", 20L, "Ana", "García", 10L, "Museo del Prado");
        when(reviewService.updateReview(eq(2L), any())).thenReturn(updated);

        mvc.perform(put("/reviews/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.comment").value("Updated"));

        verify(reviewService).updateReview(eq(2L), any());
    }

    // -------- TESTING DELETE /reviews/{id} --------
    @Test
    @DisplayName("DELETE /reviews/{id} -> 204 No Content")
    void deleteReview_noContent() throws Exception {
        doNothing().when(reviewService).deleteReview(3L);

        mvc.perform(delete("/reviews/{id}", 3))
                .andExpect(status().isNoContent());

        verify(reviewService).deleteReview(3L);
    }

    // -------- GET /reviews/list/{id} --------
    @Test
    @DisplayName("GET /reviews/list/{id} -> 200 OK with list of user reviews")
    void getReviewByUserId_ok() throws Exception {
        var r1 = resp(1L, 5, "I loved it", 42L, "Lucía", "Martínez", 100L, "Madrid House 5");
        var r2 = resp(2L, 4, "Pretty nice", 42L, "Lucía", "Martínez", 101L, "Madrid Studio 2");

        when(reviewService.getReviewByUserId(42L)).thenReturn(List.of(r1, r2));

        mvc.perform(get("/reviews/list/{id}", 42))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].userFirstName").value("Lucía"))
                .andExpect(jsonPath("$[1].placeName").value("Madrid Studio 2"));

        verify(reviewService).getReviewByUserId(42L);
    }

    @Test
    @DisplayName("GET /reviews/list/{id} -> 200 OK with an empty list if there are no reviews")
    void getReviewByUserId_emptyList() throws Exception {
        when(reviewService.getReviewByUserId(99L)).thenReturn(List.of());

        mvc.perform(get("/reviews/list/{id}", 99))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        verify(reviewService).getReviewByUserId(99L);
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