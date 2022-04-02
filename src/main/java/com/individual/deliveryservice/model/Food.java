package com.individual.deliveryservice.model;

import com.individual.deliveryservice.dto.FoodDto;
import com.individual.deliveryservice.validator.FoodValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Food extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    public Food(FoodDto dto) {
        //FoodValidator.validateFoodInput(dto);
        this.name=dto.getName();
        this.price=dto.getPrice();
        this.restaurant=dto.getRestaurant();
    }
}
