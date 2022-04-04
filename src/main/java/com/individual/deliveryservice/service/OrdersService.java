package com.individual.deliveryservice.service;

import com.individual.deliveryservice.dto.FoodOrderDto;
import com.individual.deliveryservice.dto.OrderDto;
import com.individual.deliveryservice.model.FoodOrder;
import com.individual.deliveryservice.model.Orders;
import com.individual.deliveryservice.model.Restaurant;
import com.individual.deliveryservice.repository.OrdersRepository;
import com.individual.deliveryservice.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final RestaurantRepository restaurantRepository;
    private final OrdersRepository ordersRepository;


    public List<OrderDto.Response> getOrderDtoResponseList() {
        List<OrderDto.Response> orderDtoResponseList = new ArrayList<>();

        List<Orders> ordersList = ordersRepository.findAll();

        for (Orders orders : ordersList) {
            OrderDto.Response orderDtoResponse = new OrderDto.Response();

            orderDtoResponse.setRestaurantName(orders.getRestaurant().getName());
            List<FoodOrderDto.Response> foodOrderResponseList = new ArrayList<>();
            for (FoodOrder foodOrder : orders.getFoodOrders()) {
                foodOrderResponseList.add(new FoodOrderDto.Response(foodOrder));
            }
            orderDtoResponse.setFoods(foodOrderResponseList);
            orderDtoResponse.setDeliveryFee(orders.getDeliveryFee());
            orderDtoResponse.setTotalPrice(orders.getTotalPrice());
            orderDtoResponseList.add(orderDtoResponse);
        }
        return orderDtoResponseList;
    }
}
