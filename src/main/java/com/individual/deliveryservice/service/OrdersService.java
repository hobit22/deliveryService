package com.individual.deliveryservice.service;

import com.individual.deliveryservice.model.Orders;
import com.individual.deliveryservice.model.Restaurant;
import com.individual.deliveryservice.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final RestaurantRepository restaurantRepository;

    public void getOrders(Model model){
//        System.out.println(model.addAttribute("restaurantName"));
//        Restaurant restaurant = restaurantRepository.findByName(model.getAttribute("restaurantName"));
//        Orders orders = new Orders();
//        //orders.setName(restaurant.getName());
        //return orders;
    }
}
