package com.individual.deliveryservice.service;

import com.individual.deliveryservice.dto.FoodOrderDto;
import com.individual.deliveryservice.dto.OrderDto;
import com.individual.deliveryservice.model.Food;
import com.individual.deliveryservice.model.FoodOrder;
import com.individual.deliveryservice.model.Orders;
import com.individual.deliveryservice.model.Restaurant;
import com.individual.deliveryservice.repository.FoodOrderRepository;
import com.individual.deliveryservice.repository.FoodRepository;
import com.individual.deliveryservice.repository.OrdersRepository;
import com.individual.deliveryservice.validator.FoodOrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final FoodOrderRepository foodOrderRepository;
    private final FoodRepository foodRepository;
    private final FoodService foodService;

    public List<OrderDto.Response> getAllOrderDtoResponseList() {
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


    public OrderDto.Response getOrderDtoResponse(Restaurant restaurant, Long totalOrderPrice, Long deliveryFee, Orders orders) {
        List<FoodOrder> foodOrders = foodOrderRepository.findAllByOrders(orders);
        List<FoodOrderDto.Response> foodOrderDtoResponses = new ArrayList<>();
        for(FoodOrder foodOrder : foodOrders){
            foodOrderDtoResponses.add(new FoodOrderDto.Response(foodOrder));
        }

        return OrderDto.Response.builder()
                .foods(foodOrderDtoResponses)
                .restaurantName(restaurant.getName())
                .deliveryFee(deliveryFee)
                .totalPrice(totalOrderPrice)
                .build();
    }

    public Orders saveOrders(List<FoodOrderDto.Request> foods, Restaurant restaurant, Long totalOrderPrice, Long deliveryFee) {
        Orders orders = new Orders();
        orders.setRestaurant(restaurant);
        ordersRepository.save(orders);

        //음식-주문 저장
        saveFoodOrders(foods, restaurant, orders);

        orders.setDeliveryFee(deliveryFee);
        orders.setTotalPrice(totalOrderPrice);

        ordersRepository.save(orders);
        return orders;
    }

    public void saveFoodOrders(List<FoodOrderDto.Request> foods, Restaurant restaurant, Orders orders) {
        for (FoodOrderDto.Request foodOrderRequestDto : foods) {
            Food food = getFoodInRestaurant(restaurant, foodOrderRequestDto);

            Long foodQuantity = foodOrderRequestDto.getQuantity();
            Long foodPrice = food.getPrice() * foodQuantity;
            //String foodname = food.getName();
            //VALID MIN ORDER PRICE 고려

            FoodOrder foodOrder = FoodOrder.builder()
                    .orders(orders)
                    //.name(foodname)
                    .food(food)
                    .quantity(foodQuantity)
                    .price(foodPrice)
                    .build();

            foodOrderRepository.save(foodOrder);
        }
    }

    public Long getTotalOrderPrice(List<FoodOrderDto.Request> foods) {
        long totalOrderPrice = 0L;

        //음식 id 존재 확인, 음식 양 정상범위 확인
        for (FoodOrderDto.Request foodOrderRequestDto : foods) {
            Long foodId = foodOrderRequestDto.getId();
            Long foodQuantity = foodOrderRequestDto.getQuantity();

            Food food = foodService.isValidFood(foodId);
            FoodOrderValidator.validateFoodOrderInput(foodOrderRequestDto);

            long foodPrice = food.getPrice() * foodQuantity;

            totalOrderPrice += foodPrice;
        }
        return totalOrderPrice;
    }


    public Food getFoodInRestaurant(Restaurant restaurant, FoodOrderDto.Request foodOrderRequestDto) {
        return foodRepository.findByRestaurantIdAndId(restaurant.getId(), foodOrderRequestDto.getId()).orElseThrow(
                ()-> new IllegalArgumentException("해당가게에 음식이 존재하지 않습니다.")
        );
//        return foodRepository.findByRestaurantAndId(restaurant, foodOrderRequestDto.getId()).orElseThrow(
//                ()-> new IllegalArgumentException("해당가게에 음식이 존재하지 않습니다.")
//        );
    }
}
