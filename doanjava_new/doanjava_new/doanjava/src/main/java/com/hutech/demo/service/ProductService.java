package com.hutech.demo.service;

import com.hutech.demo.models.Category;
import com.hutech.demo.models.Product;
import com.hutech.demo.models.Revenue;
import com.hutech.demo.repository.CategoryRepository;
import com.hutech.demo.repository.ProductRepository;
import com.hutech.demo.repository.RevenueRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional

public class ProductService {
    private final ProductRepository productRepository;
    private final RevenueRepository revenueRepository;
    // Retrieve all products from the database
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public List<Product> getAllNotDeletedProducts() {
        return productRepository.findAllNotDeleted();
    }
    public List<Product> getAllActiveAndNotDeletedProducts() {
        return productRepository.findAllActiveAndNotDeleted();
    }
    public List<Product> getAllDeletedProducts() {
        return productRepository.findAllDeleted();
    }

    // Retrieve a product by its id
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Add a new product to the database
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Update an existing product
    public Product updateProduct(@NotNull Product product) {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " + product.getId() + " does not exist."));
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setImage(product.getImage());
        existingProduct.setNsx(product.getNsx());
        existingProduct.setXuatsu(product.getXuatsu());
        return productRepository.save(existingProduct);
    }
    @Async
    // Delete a product by its id
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }
    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }
    public void toggleProductActive(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Product not found"));
        product.setIsActive(!product.getIsActive());
        productRepository.save(product);
    }

    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }
    public List<Product> getAllProductsSortedByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    public List<Product> getAllProductsSortedByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }
    @PersistenceContext
    private EntityManager entityManager;
    public List<Revenue> getTop4SaleProducts() {
        TypedQuery<Revenue> query = entityManager.createQuery(
                "SELECT r FROM Revenue r WHERE r.product.isActive = true ORDER BY r.totalSales DESC",
                Revenue.class
        );
        query.setMaxResults(5);
        return query.getResultList();
    }

}