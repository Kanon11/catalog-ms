package com.example.catalogms.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.catalogms.dto.PagedResponse;
import com.example.catalogms.entity.Product;
import com.example.catalogms.exception.ProductNotFoundException;
import com.example.catalogms.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    private static final int MAX_PAGE_SIZE = 100;

    /**
     * Returns a page of products. {@code page} is 1-based; {@code page}/{@code size} are
     * clamped to sane bounds. Results are sorted by id so paging is stable.
     */
    public PagedResponse<Product> findAll(int page, int size) {
        int safePage = Math.max(page, 1);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        Pageable pageable = PageRequest.of(safePage - 1, safeSize, Sort.by("id").ascending());
        return PagedResponse.from(repository.findAll(pageable));
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product create(Product product) {
        product.setId(null);
        return repository.save(product);
    }

    public Product update(Long id, Product update) {
        Product existing = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        existing.setName(update.getName());
        existing.setPrice(update.getPrice());
        existing.setDescription(update.getDescription());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
