package com.ironhack.IronBooking.dto.place;

import com.ironhack.IronBooking.enums.PlaceType;
import com.ironhack.IronBooking.model.vo.Address;
import com.ironhack.IronBooking.model.vo.Price;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlaceRequestDTO {
    @NotBlank @Size(max = 80) private String name;
    @NotNull private Address address;
    @NotNull private Price price;
    @NotNull private PlaceType placeType;
    @NotNull @Positive private Integer capacity;
}
