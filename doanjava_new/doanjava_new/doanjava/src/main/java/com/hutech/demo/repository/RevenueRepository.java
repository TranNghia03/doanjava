package com.hutech.demo.repository;

import com.hutech.demo.models.Product;
import com.hutech.demo.models.Revenue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RevenueRepository extends JpaRepository<Revenue, Long> {
    Revenue findByProduct(Product product);

    @Query("SELECT r FROM Revenue r WHERE r.product.isActive = true ORDER BY r.totalSales DESC")
    List<Revenue> findTop4ByTotalSales(@Param("limit") int limit);
    // You can add custom query methods if needed
}