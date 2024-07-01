package com.hutech.demo.repository;

import com.hutech.demo.models.Category;
import com.hutech.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByCategory(Category category);
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByPriceDesc();
    @Query("SELECT p FROM Product p WHERE p.isDelete = false")
    List<Product> findAllNotDeleted();

    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.isDelete = false")
    Optional<Product> findByIdAndNotDeleted(Long id);

    @Query("SELECT p FROM Product p WHERE p.isDelete = true")
    List<Product> findAllDeleted();

    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.isDelete = false")
    List<Product> findAllActiveAndNotDeleted();
}
