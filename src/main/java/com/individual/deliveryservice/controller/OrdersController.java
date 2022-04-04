package com.individual.deliveryservice.controller;

import com.individual.deliveryservice.dto.FoodOrderDto;
import com.individual.deliveryservice.dto.OrderDto;
import com.individual.deliveryservice.model.Food;
import com.individual.deliveryservice.model.FoodOrder;
import com.individual.deliveryservice.model.Orders;
import com.individual.deliveryservice.model.Restaurant;
import com.individual.deliveryservice.repository.FoodOrderRepository;
import com.individual.deliveryservice.repository.FoodRepository;
import com.individual.deliveryservice.repository.OrdersRepository;
import com.individual.deliveryservice.repository.RestaurantRepository;
import com.individual.deliveryservice.service.FoodService;
import com.individual.deliveryservice.service.OrdersService;
import com.individual.deliveryservice.service.RestaurantService;
import com.individual.deliveryservice.validator.FoodOrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrdersController {
    private final RestaurantService restaurantService;
    private final OrdersService ordersService;

    @GetMapping("/orders")
    public List<OrderDto.Response> getOrders() {
        return ordersService.getAllOrderDtoResponseList();
    }

    @PostMapping("/order/request")
    @Transactional
    public OrderDto.Response getOrders(@RequestBody OrderDto.Request request) {
        Long restaurantId = request.getRestaurantId();
        List<FoodOrderDto.Request> foods = request.getFoods();

        //가게 존재 확인
        Restaurant restaurant = restaurantService.isValidRestaurant(restaurantId);
        Long minOrderPrice = restaurant.getMinOrderPrice();
        Long totalOrderPrice = ordersService.getTotalOrderPrice(foods);
        // 최소주문금액 확인
        FoodOrderValidator.validMinOrderPrice(minOrderPrice, totalOrderPrice);

        Long deliveryFee = restaurant.getDeliveryFee();
        totalOrderPrice += deliveryFee;

        //주문 저장
        Orders orders = ordersService.saveOrders(foods, restaurant, totalOrderPrice, deliveryFee);

        return ordersService.getOrderDtoResponse(restaurant, totalOrderPrice, deliveryFee, orders);
    }

}
