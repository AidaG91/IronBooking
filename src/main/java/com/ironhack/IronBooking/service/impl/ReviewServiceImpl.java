package com.ironhack.IronBooking.service.impl;

import com.ironhack.IronBooking.dto.review.ReviewRequestDTO;
import com.ironhack.IronBooking.dto.review.ReviewResponseDTO;
import com.ironhack.IronBooking.dto.review.ReviewUpdateDTO;
import com.ironhack.IronBooking.model.Review;
import com.ironhack.IronBooking.repository.ReviewRepository;
import com.ironhack.IronBooking.service.interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public List<ReviewResponseDTO> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public ReviewResponseDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found. ID: " + id));
        return mapToResponseDTO(review);
    }

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequest) {
        Review review = new Review();
        review.setUser(reviewRequest.getUser());
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());
        review.setDate(reviewRequest.setDate());

        Review savedReview=reviewRepository.save(review);
        return mapToResponseDTO(reviewRepository.save(savedReview));
    }

    @Override
    public ReviewResponseDTO updateReview(Long id, ReviewUpdateDTO reviewUpdate) {
        Review review =reviewRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Review not found with id: " +id));

        if (reviewUpdate.getRating() !=null) review.setRating(reviewUpdate.getRating());
        if (reviewUpdate.getComment()!=null) review.setComment(reviewUpdate.getComment());
        Review updatedReview = reviewRepository.save(review);
        return mapToResponseDTO(updatedReview);


    }

    @Override
    public void deleteReview(Long id) {
        if(!reviewRepository.existsById(id)) {
            throw new RuntimeException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);

    }

    @Override
    public List<ReviewResponseDTO> getReviewByUserId(Long userId) {
        return reviewRepository.getReviewByUserId(userId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());

    }

    //metodo auxiliar
    private ReviewResponseDTO mapToResponseDTO(Review review){
        ReviewResponseDTO dto=new ReviewResponseDTO();
        dto.setId(review.getId());
        dto.setUser(review.getUser());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
    }
}


