package com.individual.deliveryservice.validator;

import com.individual.deliveryservice.dto.FoodOrderDto;
import org.springframework.stereotype.Component;

@Component
public class FoodOrderValidator {

    public static void validateFoodOrderInput(FoodOrderDto.Request dto) {
        // 입력값 Validation
        Long id = dto.getId();
        Long quantity = dto.getQuantity();
        if (quantity < 1 || quantity > 100) {
            throw new IllegalArgumentException("수량 범위가 올바르지 않습니다.");
        }
    }


    public static void validMinOrderPrice(Long minOrderPrice, Long totalOrderPrice) {
        if (totalOrderPrice < minOrderPrice) {
            throw new IllegalArgumentException("최소주문 금액보다 작습니다.");
        }
    }



}

