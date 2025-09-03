package com.ironhack.IronBooking.controller;

import com.ironhack.IronBooking.dto.review.ReviewRequestDTO;
import com.ironhack.IronBooking.dto.review.ReviewResponseDTO;
import com.ironhack.IronBooking.dto.review.ReviewUpdateDTO;
import com.ironhack.IronBooking.service.interfaces.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@Validated
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    @Operation(summary = "Get all reviews",
            description = "Retrieve a list of all reviews in the system.")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews(){
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get review by ID",
            description = "Retrieve the details of a review by its unique positive ID.")
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new review",
            description = "Create a new review for a place and return the created review with a Location header.")
    public ResponseEntity<ReviewResponseDTO> createReview(@Valid @RequestBody ReviewRequestDTO body) {
        ReviewResponseDTO created = reviewService.createReview(body);
        URI location = URI.create("/reviews/" + created.getId());
        return ResponseEntity.created(location).body(created); // 201 + Location
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update review by ID",
            description = "Update the content or rating of an existing review by its positive ID.")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable @Positive Long id,
                                                          @Valid @RequestBody ReviewUpdateDTO reviewUpdate) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewUpdate));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete review by ID",
            description = "Delete a review by its unique positive ID.")
    public ResponseEntity<Void> deleteReview(@PathVariable @Positive Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list/{id}")
    @Operation(summary = "Get reviews by user ID",
            description = "Retrieve all reviews made by a specific user, given the user's positive ID.")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewByUserId(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(reviewService.getReviewByUserId(id));
    }
}
