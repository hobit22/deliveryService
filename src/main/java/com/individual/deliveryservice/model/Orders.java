package com.individual.deliveryservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @ManyToOne(optional = false)
//    @JoinColumn(nullable = false)
//    private Restaurant restaurant;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<FoodOrder> foodOrders = new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @Column
    private Long deliveryFee =0L;

    @Column
    private Long totalPrice = 0L;

    public void addOrderItem(FoodOrder foodOrder){
        this.getFoodOrders().add(foodOrder);
        foodOrder.setOrders(this);
    }
}