package com.ironhack.IronBooking.model.place;

import com.ironhack.IronBooking.model.User;
import com.ironhack.IronBooking.model.vo.Address;
import com.ironhack.IronBooking.model.vo.Price;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "place_base")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public abstract class PlaceBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String name;

    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "address_city", length = 80, nullable = false)),
            @AttributeOverride(name = "country", column = @Column(name = "address_country", length = 80, nullable = false))
    })
    private Address address;

    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount", precision = 12, scale = 2, nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "price_currency", length = 3, nullable = false))
    })
    private Price price;

    // Owner (User) of this place.
    // LAZY: do not fetch owner unless accessed (faster list queries).
    // optional=false + nullable=false: every place must have an owner (FK NOT NULL).
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
}
