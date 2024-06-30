package com.hutech.demo.models;

import jakarta.persistence.*;
import lombok.*;



@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private String description;
    private String nsx;
    private  String xuatsu;
    private String image;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;



    public long getProductId() {
        return id;
    }

}
