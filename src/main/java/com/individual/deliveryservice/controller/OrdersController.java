package com.individual.deliveryservice.controller;

import com.individual.deliveryservice.dto.FoodOrderRequestDto;
import com.individual.deliveryservice.dto.OrderRequestDto;
import com.individual.deliveryservice.dto.OrderResponseDto;
import com.individual.deliveryservice.model.Food;
import com.individual.deliveryservice.model.Restaurant;
import com.individual.deliveryservice.repository.FoodRepository;
import com.individual.deliveryservice.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrdersController {
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    @GetMapping("/orders")
    public OrderResponseDto getOrders(){


    }

    @PostMapping("/order/request")
    @ResponseBody
    public OrderResponseDto getOrders(@RequestBody OrderRequestDto dto){
        Long restaurantId = dto.getRestaurantId();
        List<FoodOrderRequestDto> foods = dto.getFoods();
        Restaurant restaurant =restaurantRepository.findById(restaurantId).orElseThrow(
                ()-> new IllegalArgumentException("가게가 존재하지 않습니다.")
        );

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setRestaurantName(restaurant.getName());
        Long deliveryFee = orderResponseDto.getDeliveryFee();
        Long totalFoodPrice = 0L;
        for(FoodOrderRequestDto foodOrderRequestDto : foods){
            Long foodId = foodOrderRequestDto.getId();
            Long foodQuantity = foodOrderRequestDto.getQuantity();

            //id 존재 체크
            Food food = foodRepository.findById(foodId).orElseThrow(
                    ()->new IllegalArgumentException("음식이 존재하지 않습니다.")
            );
            //quantity 숫자 체크
            if(foodQuantity < 1 || foodQuantity > 100){
                throw new IllegalArgumentException("음식 수량이 올바르지 않습니다.");
            }
            //total price 에 더하기

            totalFoodPrice += food.getPrice() * foodQuantity;
        }

        if(totalFoodPrice < restaurant.getMinOrderPrice()){
            throw new IllegalArgumentException("최소주문 금액보다 작습니다.");
        }

        orderResponseDto.setTotalPrice(totalFoodPrice + deliveryFee);
        System.out.println(orderResponseDto.getTotalPrice());

        return orderResponseDto;
    }
}
