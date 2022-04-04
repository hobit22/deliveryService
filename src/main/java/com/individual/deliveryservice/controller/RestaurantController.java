package com.individual.deliveryservice.controller;

import com.individual.deliveryservice.dto.RestaurantDto;
import com.individual.deliveryservice.model.Restaurant;
import com.individual.deliveryservice.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantRepository repository;

    @GetMapping("/restaurants")
    @ResponseBody
    public List<Restaurant> getRestaurant(){
        return repository.findAll();
    }

    @PostMapping("/restaurant/register")
    @ResponseBody
    @Transactional
    public Restaurant registerRestaurant(@RequestBody RestaurantDto dto){
        return repository.save(new Restaurant(dto));
    }

}
