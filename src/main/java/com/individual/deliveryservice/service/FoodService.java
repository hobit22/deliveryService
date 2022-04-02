package com.individual.deliveryservice.service;

import com.individual.deliveryservice.dto.FoodDto;
import com.individual.deliveryservice.model.Food;
import com.individual.deliveryservice.model.Restaurant;
import com.individual.deliveryservice.repository.FoodRepository;
import com.individual.deliveryservice.repository.RestaurantRepository;
import com.individual.deliveryservice.validator.FoodValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final FoodValidator foodValidator;

    public List<Food> createFood(Long restaurantId, List<FoodDto> dtoList) {
        System.out.println("/restaurant/{restaurantId}/food/register");
        System.out.println("restaurantId = " + restaurantId.toString());
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                ()->new IllegalArgumentException("존재하지 않는 가게입니다.")
        );
        List<Food> foodList = new ArrayList<>();
        for(FoodDto dto:dtoList){
            foodValidator.validateFoodInput(dto);
            dto.setRestaurant(restaurant);

            Optional<Food> checkFood = foodRepository.findByRestaurantAndName(restaurant,dto.getName());
//            if(checkFood.isPresent()){
//                System.out.println("이미 존재해");
//                System.out.println(checkFood);
//                throw new IllegalArgumentException("같은 음식점에 같은 음식이 존재합니다.");
//            }
            Food food = new Food(dto);
            foodRepository.save(food);
            foodList.add(food);
        }
        return foodList;
    }

    public boolean check1(Restaurant restaurant, FoodDto foodDto){
        System.out.println("같은 음식점 같은 음식 이름 체크");
        String name = foodDto.getName();
        if(foodRepository.findByRestaurantAndName(restaurant, name).isPresent()){
            System.out.println("같은 음식점");
            throw new IllegalArgumentException("같은 음식점에 같은 음식이 존재합니다.");
        }
        return true;
    }

}
