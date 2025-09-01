package com.ironhack.IronBooking.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ReviewUpdateDTO {

    @Min(1)
    @Max(5)
    private Integer rating;

    private String comment;

}
