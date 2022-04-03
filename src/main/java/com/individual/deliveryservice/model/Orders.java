package com.individual.deliveryservice.model;

import com.individual.deliveryservice.dto.OrderResponseDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @OneToMany
    private List<OrderItem> foods = new ArrayList<>();

    public Orders(Restaurant restaurant, ){
        this.restaurant = restaurant;


    }
}