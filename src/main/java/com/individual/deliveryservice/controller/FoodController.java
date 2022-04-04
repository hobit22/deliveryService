package com.individual.deliveryservice.controller;

import com.individual.deliveryservice.dto.FoodDto;
import com.individual.deliveryservice.model.Food;
import com.individual.deliveryservice.model.Restaurant;
import com.individual.deliveryservice.repository.FoodRepository;
import com.individual.deliveryservice.repository.RestaurantRepository;
import com.individual.deliveryservice.service.FoodService;
import com.individual.deliveryservice.validator.FoodValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FoodController {
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodService foodService;
    private final FoodValidator foodValidator;

    @PostMapping("/restaurant/{restaurantId}/food/register")
    @ResponseBody
    public ResponseEntity registerFood(@PathVariable Long restaurantId, @RequestBody List<FoodDto> dtoList){
        List<Food> foodList = foodService.createFood(restaurantId, dtoList);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/foods")
    public ResponseEntity getFoods(@PathVariable Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                ()->new IllegalArgumentException("존재하지 않는 가게입니다.")
        );
        List<Food> foodsList = foodRepository.findAllByRestaurant(restaurant);
//
//        Food[] foodsArray = foodsList.toArray(new Food[foodsList.size()]);
        //List<Food> foodsList = restaurant.getFoods();

        return new ResponseEntity<>(foodsList, HttpStatus.OK);
    }

}
