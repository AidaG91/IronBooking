package com.ironhack.IronBooking.model.vo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Address {

    @NotBlank
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String city;

    @NotBlank
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String country;
}
