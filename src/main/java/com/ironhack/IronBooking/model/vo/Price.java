package com.ironhack.IronBooking.model.vo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Price {

    @NotNull
    @PositiveOrZero
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotBlank
    @Pattern(regexp = "[A-Za-z]{3}", message = "Currency must be a 3-letter code (e.g., EUR, USD)")
    @Column(nullable = false, length = 3)
    private String currency;
}
