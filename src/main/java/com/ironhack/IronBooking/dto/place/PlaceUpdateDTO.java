package com.ironhack.IronBooking.dto.place;

import com.ironhack.IronBooking.enums.PlaceType;
import com.ironhack.IronBooking.model.vo.Address;
import com.ironhack.IronBooking.model.vo.Price;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlaceUpdateDTO {
    @Size(max = 80) private String name;
    private Address address;
    private Price price;
    private PlaceType placeType;
    @Positive private Integer capacity;
}
