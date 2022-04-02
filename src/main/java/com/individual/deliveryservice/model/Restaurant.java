package com.individual.deliveryservice.model;

import com.individual.deliveryservice.dto.RestaurantDto;
import com.individual.deliveryservice.validator.RestaurantValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long minOrderPrice;

    @Column(nullable = false)
    private Long deliveryFee;

//    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE)
//    List<Food> foods = new ArrayList<>();

    public Restaurant(RestaurantDto dto) {

        RestaurantValidator.validateRestaurantInput(dto);

        this.name=dto.getName();
        this.minOrderPrice=dto.getMinOrderPrice();
        this.deliveryFee=dto.getDeliveryFee();

    }
}
