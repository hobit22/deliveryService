package com.individual.deliveryservice.dto;

import com.individual.deliveryservice.model.FoodOrder;
import lombok.*;

public class FoodOrderDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String name;
        private Long quantity;
        private Long price;

        public Response(FoodOrder foodOrder){
            this.name = foodOrder.getName();
            this.quantity = foodOrder.getQuantity();
            this.price = foodOrder.getPrice();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private Long id;
        private Long quantity;
    }
}
