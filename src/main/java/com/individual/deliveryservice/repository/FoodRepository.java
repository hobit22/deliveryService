package com.individual.deliveryservice.repository;

import com.individual.deliveryservice.model.Food;
import com.individual.deliveryservice.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findByRestaurantAndName(Restaurant restaurant, String name);

    List<Food> findAllByRestaurant(Restaurant restaurant);

    Food findByName(String name);
}
