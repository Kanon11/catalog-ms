package com.example.catalogms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.catalogms.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Page<Application> findByWorkerId(Long workerId, Pageable pageable);
}
