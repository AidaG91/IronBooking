package com.ironhack.IronBooking.dto.place;

import com.ironhack.IronBooking.enums.PlaceType;
import com.ironhack.IronBooking.model.vo.Address;
import com.ironhack.IronBooking.model.vo.Price;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlaceResponseDTO {
    private Long id;
    private String name;
    private Address address;
    private Price price;
    private PlaceType placeType;
    private Integer capacity;
}
