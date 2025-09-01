package com.ironhack.IronBooking.service.interfaces;

import com.ironhack.IronBooking.dto.review.ReviewRequestDTO;
import com.ironhack.IronBooking.dto.review.ReviewResponseDTO;
import com.ironhack.IronBooking.dto.review.ReviewUpdateDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewResponseDTO> getAllReviews();

    ReviewResponseDTO getReviewById(Long id);

    ReviewResponseDTO createReview(ReviewRequestDTO reviewRequest);

    ReviewResponseDTO updateReview(Long id, ReviewUpdateDTO reviewUpdate);

    void deleteReview(Long id);

    List<ReviewResponseDTO> getReviewByUserId(Long userId);
}
