package com.hutech.demo;


public class ProductRevenue {
    private Long productId;
    private String productName;
    private double totalRevenue;
    private int totalQuantitySold;

    public ProductRevenue(Long productId, String productName, double totalRevenue, int totalQuantitySold) {
        this.productId = productId;
        this.productName = productName;
        this.totalRevenue = totalRevenue;
        this.totalQuantitySold = totalQuantitySold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
//    // Getters
//    public Long getProductId() {
//        return productId;
//    }
//
//    public String getProductName() {
//        return productName;
//    }
//
//    public Double getRevenue() {
//        return revenue;
//    }
//
//    public Long getTotalSales() {
//        return totalSales;
//    }
//
//    // Setters
//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public void setRevenue(Double revenue) {
//        this.revenue = revenue;
//    }
//
//    public void setTotalSales(Long totalSales) {
//        this.totalSales = totalSales;
//    }
}

