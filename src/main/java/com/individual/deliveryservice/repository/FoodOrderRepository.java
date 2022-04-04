package com.individual.deliveryservice.repository;

import com.individual.deliveryservice.model.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {

}
