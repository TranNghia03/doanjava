package com.hutech.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "revenue")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "total_sales")
    private int totalSales;

    @Column(name = "total_revenue")
    private double totalRevenue;

    // Constructors, getters, and setters
    // Ensure to have appropriate constructors and getter/setter methods
}