package com.ironhack.IronBooking.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewUpdateDTO {
    @NotNull(message = "Please add your rating.")
    @Min(1)
    @Max(5)
    private Integer rating;

    @Size(max = 500, message = "Your message cannot have more than 500 characters.")
    private String comment;

}
