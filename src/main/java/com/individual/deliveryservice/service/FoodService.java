package com.individual.deliveryservice.service;

import com.individual.deliveryservice.dto.FoodDto;
import com.individual.deliveryservice.model.Food;
import com.individual.deliveryservice.model.Restaurant;
import com.individual.deliveryservice.repository.FoodRepository;
import com.individual.deliveryservice.repository.RestaurantRepository;
import com.individual.deliveryservice.validator.FoodValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final FoodValidator foodValidator;
    private final RestaurantService restaurantService;

    @Transactional
    public void createFood(Long restaurantId, List<FoodDto> dtoList) {
        Restaurant restaurant = restaurantService.isValidRestaurant(restaurantId);
        for (FoodDto dto : dtoList) {
            foodValidator.validateFoodInput(dto);
            dto.setRestaurant(restaurant);
            checkSameFoodInRestaurant(restaurant, dto);
            Food food = new Food(dto);
            foodRepository.save(food);
        }
    }

    private void checkSameFoodInRestaurant(Restaurant restaurant, FoodDto dto) {
        Optional<Food> checkFood = foodRepository.findByRestaurantAndName(restaurant, dto.getName());
        if (checkFood.isPresent()) {
            throw new IllegalArgumentException("같은 음식점에 같은 음식이 존재합니다.");
        }
    }

    public List<Food> getFoodList(Long restaurantId) {
        Restaurant restaurant = restaurantService.isValidRestaurant(restaurantId);

        return foodRepository.findAllByRestaurant(restaurant);
    }

    public Food isValidFood(Long foodId) {
        return foodRepository.findById(foodId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 음식입니다.")
        );
    }
}
