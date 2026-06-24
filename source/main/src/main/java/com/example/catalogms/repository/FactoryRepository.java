package com.example.catalogms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.catalogms.entity.Factory;

public interface FactoryRepository extends JpaRepository<Factory, Long> {
}
