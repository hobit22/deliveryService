package com.individual.deliveryservice.model;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn
    private Orders orders;

//    @Column(nullable = false)
//    private String name;

    @ManyToOne
    @JoinColumn
    private Food food;

    @Column(nullable = false)
    private Long quantity; // 상품 개수

    @Column(nullable = false)
    private Long price;
}