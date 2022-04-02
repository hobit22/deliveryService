package com.individual.deliveryservice.validator;

import com.individual.deliveryservice.dto.RestaurantDto;
import org.springframework.stereotype.Component;

@Component
public class RestaurantValidator {
    public static void validateRestaurantInput(RestaurantDto requestDto) {
        // 입력값 Validation
        String name = requestDto.getName();
        Long minOrderPrice = requestDto.getMinOrderPrice();
        Long deliveryFee = requestDto.getDeliveryFee();
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("가게 명이 올바르지 않습니다.");
        }

        if (minOrderPrice == null || minOrderPrice == 0) {
            throw new IllegalArgumentException("최소 주문가격이 올바르지 않습니다.");
        }

        if (minOrderPrice < 1000 || minOrderPrice > 100000) {
            throw new IllegalArgumentException("최소 주문가격의 범위가 올바르지 않습니다.");
        }

        if (minOrderPrice % 100 != 0) {
            throw new IllegalArgumentException("최소 주문가격의 단위가 올바르지 않습니다.");
        }

        if (deliveryFee == null) {
            throw new IllegalArgumentException("배달비가 올바르지 않습니다.");
        }

        if (deliveryFee < 0 || deliveryFee > 10000) {
            throw new IllegalArgumentException("배달비의 범위가 올바르지 않습니다.");
        }
        if (deliveryFee % 500 != 0) {
            throw new IllegalArgumentException("배달비의 단위가 올바르지 않습니다.");
        }

//        if (!URLValidator.isValidUrl(requestDto.getLink())) {
//            throw new IllegalArgumentException("상품 최저가 페이지 URL 포맷이 맞지 않습니다.");
//        }

//        if (requestDto.getLprice() <= 0) {
//            throw new IllegalArgumentException("상품 최저가가 0 이하입니다.");
//        }
    }
}
