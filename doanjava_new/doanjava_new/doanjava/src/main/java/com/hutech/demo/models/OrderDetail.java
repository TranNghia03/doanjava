package com.hutech.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private double price;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;
//
//
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String name;


}


