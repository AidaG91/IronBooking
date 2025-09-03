package com.ironhack.IronBooking.controller;

import com.ironhack.IronBooking.dto.review.ReviewRequestDTO;
import com.ironhack.IronBooking.dto.review.ReviewResponseDTO;
import com.ironhack.IronBooking.dto.review.ReviewUpdateDTO;
import com.ironhack.IronBooking.service.interfaces.ReviewService;
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
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews(){
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }


    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(@Valid @RequestBody ReviewRequestDTO body) {
        ReviewResponseDTO created = reviewService.createReview(body);
        URI location = URI.create("/reviews/" + created.getId());
        return ResponseEntity.created(location).body(created); // 201 + Location
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable @Positive Long id,
                                                          @Valid @RequestBody ReviewUpdateDTO reviewUpdate) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable @Positive Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/list/{id}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewByUserId(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(reviewService.getReviewByUserId(id));
    }
}
