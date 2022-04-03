package com.individual.deliveryservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private String restaurantName;
    private List<FoodOrderResponseDto> foods;
    private Long deliveryFee;
    private Long totalPrice = 0L;

}
