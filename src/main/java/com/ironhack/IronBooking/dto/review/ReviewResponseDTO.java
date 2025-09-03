package com.ironhack.IronBooking.dto.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDTO {

    private Long id;

    private Integer rating;

    private String comment;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime date;

    private Long userId;
    private String userFirstName;
    private String userLastName;

    private Long placeId;
    private String placeName;

}
