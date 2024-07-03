package com.hutech.demo.service;


import com.hutech.demo.models.OrderDetail;
import com.hutech.demo.models.Product;
import com.hutech.demo.models.Revenue;
import com.hutech.demo.repository.RevenueRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RevenueService {

    @Autowired
    private RevenueRepository revenueRepository;

    public void calculateAndStoreRevenue(OrderDetail orderDetail) {
        Product product = orderDetail.getProduct();
        double revenue = orderDetail.getQuantity() * orderDetail.getPrice();

        // Check if there's an existing entry for this product in revenue table
        Revenue existingRevenue = revenueRepository.findByProduct(product);
        if (existingRevenue != null) {
            existingRevenue.setTotalSales(existingRevenue.getTotalSales() + orderDetail.getQuantity());
            existingRevenue.setTotalRevenue(existingRevenue.getTotalRevenue() + revenue);
        } else {
            Revenue newRevenue = new Revenue();
            newRevenue.setProduct(product);
            newRevenue.setTotalSales(orderDetail.getQuantity());
            newRevenue.setTotalRevenue(revenue);
            revenueRepository.save(newRevenue);
        }
    }

    public List<Revenue> getAllRevenues() {
        return revenueRepository.findAll();
    }
}