package com.example.catalogms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.catalogms.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
