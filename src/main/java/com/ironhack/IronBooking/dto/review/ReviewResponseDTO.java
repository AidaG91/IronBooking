package com.ironhack.IronBooking.dto.review;

import lombok.Data;

@Data
public class ReviewResponseDTO {

    private Long id;

    private Integer rating;

    private String comment;

    private Long userId;
    private String userName;

    private Long placeId;
    private String placeName;

}
