package com.individual.deliveryservice.validator;

import com.individual.deliveryservice.dto.FoodDto;
import com.individual.deliveryservice.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class FoodValidator {
//    @Autowired
//    public FoodValidator(RestaurantRepository restaurantRepository){
//        this.restaurantRepository = restaurantRepository;
//    }
    public void validateFoodInput(FoodDto requestDto) {
        // 입력값 Validation
        String name = requestDto.getName();
        Long price = requestDto.getPrice();
        Restaurant restaurant = requestDto.getRestaurant();
        //Long restaurantId = requestDto.getRestaurantId();

        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("음식 명이 올바르지 않습니다.");
        }

        if (price < 100 || price > 1_000_000){
            throw new IllegalArgumentException("음식가격의 범위가 올바르지 않습니다.");
        }

        if (price % 100 != 0) {
            throw new IllegalArgumentException("음식가격의 단위가 올바르지 않습니다.");
        }
    }

}
