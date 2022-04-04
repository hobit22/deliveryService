package com.individual.deliveryservice.dto;

import com.individual.deliveryservice.model.Restaurant;
import lombok.*;

import java.util.List;

public class OrderDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        private Long restaurantId;
        private List<FoodOrderDto.Request> foods;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String restaurantName;
        private List<FoodOrderDto.Response> foods;
        private Long deliveryFee;
        private Long totalPrice;

        public void addFoods(FoodOrderDto.Response foodOrderDtoResponse){
            this.foods.add(foodOrderDtoResponse);
        }
    }

}
