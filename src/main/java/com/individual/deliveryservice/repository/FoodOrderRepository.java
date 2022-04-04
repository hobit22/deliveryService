package com.individual.deliveryservice.repository;

import com.individual.deliveryservice.model.FoodOrder;
import com.individual.deliveryservice.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {

    List<FoodOrder> findAllByOrders(Orders orders);
}
