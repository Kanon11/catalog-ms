package com.example.catalogms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.catalogms.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
