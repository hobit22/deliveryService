package com.individual.deliveryservice.controller;

import com.individual.deliveryservice.dto.FoodDto;
import com.individual.deliveryservice.model.Food;
import com.individual.deliveryservice.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @PostMapping("/restaurant/{restaurantId}/food/register")
    @ResponseBody
    public void registerFood(@PathVariable Long restaurantId, @RequestBody List<FoodDto> dtoList){
        foodService.createFood(restaurantId, dtoList);
    }

    @GetMapping("/restaurant/{restaurantId}/foods")
    @ResponseBody
    public List<Food> getFoods(@PathVariable Long restaurantId){
        return foodService.getFoodList(restaurantId);
    }
}
