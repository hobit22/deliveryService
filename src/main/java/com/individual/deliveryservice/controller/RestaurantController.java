package com.individual.deliveryservice.controller;

import com.individual.deliveryservice.dto.RestaurantDto;
import com.individual.deliveryservice.model.Restaurant;
import com.individual.deliveryservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;

@Controller
public class RestaurantController {

    private final RestaurantRepository repository;

    @Autowired
    public RestaurantController(RestaurantRepository repository){
        this.repository = repository;
    }

    @GetMapping("/restaurants")
    @ResponseBody
    public ResponseEntity getRestaurant(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/restaurant/register")
    @ResponseBody
    @Transactional
    public ResponseEntity registerRestaurant(@RequestBody RestaurantDto dto){
        Restaurant restaurant = new Restaurant(dto);
        repository.save(restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
