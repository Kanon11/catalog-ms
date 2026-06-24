package com.example.catalogms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.catalogms.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}
