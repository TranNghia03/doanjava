package com.hutech.demo.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private double price;
    private String address;
    private String number;
    private String email;
    private String note;
    private  String thanhtoan;

    @JoinColumn(name = "user_id")
    private String name;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;


}
