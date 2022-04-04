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
import com.individual.deliveryservice.service.OrdersService;
import com.individual.deliveryservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OrdersController {
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final OrdersRepository ordersRepository;
    private final FoodOrderRepository foodOrderRepository;
    private final RestaurantService restaurantService;
    private final OrdersService ordersService;

    @GetMapping("/orders")
    public List<OrderDto.Response> getOrders() {
        return ordersService.getOrderDtoResponseList();
    }

    @PostMapping("/order/request")
    @Transactional
    public OrderDto.Response getOrders(@RequestBody OrderDto.Request request) {
        Long restaurantId = request.getRestaurantId();
        List<FoodOrderDto.Request> foods = request.getFoods();

        //가게 존재 확인
        Restaurant restaurant = restaurantService.isValidRestaurant(restaurantId);

        Long deliveryFee = restaurant.getDeliveryFee();
        Long minOrderPrice = restaurant.getMinOrderPrice();
        Long totalOrderPrice = 0L;
        //음식 확인
        for (FoodOrderDto.Request foodOrderRequestDto : foods) {
            Long foodId = foodOrderRequestDto.getId();
            Long foodQuantity = foodOrderRequestDto.getQuantity();

            Optional<Food> food = foodRepository.findByRestaurantAndId(restaurant, foodId);
            if (!food.isPresent()) {
                throw new IllegalArgumentException("음식이 존재하지 않습니다.");
            }

            if (foodQuantity < 1 || foodQuantity > 100) {
                throw new IllegalArgumentException("주문 수량 범위가 올바르지 않습니다.");
            }

            long foodPrice = food.get().getPrice() * foodQuantity;

            totalOrderPrice += foodPrice;
        }

        // 최소주믄금액 확인
        if (totalOrderPrice < minOrderPrice) {
            throw new IllegalArgumentException("최소주문 금액보다 작습니다.");
        }

        totalOrderPrice += deliveryFee;

        //Orders, FoodOrder 생성
        Orders orders = new Orders();
        orders.setRestaurant(restaurant);
        ordersRepository.save(orders);

        OrderDto.Response orderDtoResponse = new OrderDto.Response();
        List<FoodOrderDto.Response> foodOrderDtoResponses = new ArrayList<>();
        for (FoodOrderDto.Request foodOrderRequestDto : foods) {
            Optional<Food> food = foodRepository.findByRestaurantAndId(restaurant, foodOrderRequestDto.getId());

            Long foodQuantity = foodOrderRequestDto.getQuantity();
            Long foodPrice = food.get().getPrice() * foodQuantity;
            String foodname = food.get().getName();


            FoodOrder foodOrder = FoodOrder.builder()
                    .orders(orders)
                    .name(foodname)
                    .quantity(foodQuantity)
                    .price(foodPrice)
                    .build();

            FoodOrderDto.Response foodorderDtoResponse = new FoodOrderDto.Response(foodOrder);
            foodOrderDtoResponses.add(foodorderDtoResponse);
            foodOrderRepository.save(foodOrder);
        }


        orders.setDeliveryFee(deliveryFee);
        orders.setTotalPrice(totalOrderPrice);

        ordersRepository.save(orders);

        orderDtoResponse = OrderDto.Response.builder()
                .foods(foodOrderDtoResponses)
                .restaurantName(restaurant.getName())
                .deliveryFee(deliveryFee)
                .totalPrice(totalOrderPrice)
                .build();

        return orderDtoResponse;
    }
}
