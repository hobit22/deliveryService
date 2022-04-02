package com.individual.deliveryservice.repository;

import com.individual.deliveryservice.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
