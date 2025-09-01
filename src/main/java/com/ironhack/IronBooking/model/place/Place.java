package com.ironhack.IronBooking.model.place;

import com.ironhack.IronBooking.enums.PlaceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "places", indexes = {
        @Index(name = "idx_places_type", columnList = "placeType"),
        @Index(name = "idx_places_capacity", columnList = "capacity")
})
@PrimaryKeyJoinColumn(name = "id")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Place extends PlaceBase {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 80)
    private PlaceType placeType;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer capacity;
}
