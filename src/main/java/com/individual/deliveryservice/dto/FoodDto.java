package com.individual.deliveryservice.dto;

import com.individual.deliveryservice.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto {
    private String name;
    private Long price;
    private Restaurant restaurant;
}
