package com.individual.deliveryservice.repository;

import com.individual.deliveryservice.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
}
