package com.example.catalogms.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.catalogms.dto.PagedResponse;
import com.example.catalogms.entity.Factory;
import com.example.catalogms.exception.FactoryNotFoundException;
import com.example.catalogms.repository.FactoryRepository;

@Service
public class FactoryService {

    private final FactoryRepository repository;

    public FactoryService(FactoryRepository repository) {
        this.repository = repository;
    }

    private static final int MAX_PAGE_SIZE = 100;

    /**
     * Returns a page of factories. {@code page} is 1-based; {@code page}/{@code size} are
     * clamped to sane bounds. Results are sorted by id so paging is stable.
     */
    public PagedResponse<Factory> findAll(int page, int size) {
        int safePage = Math.max(page, 1);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        Pageable pageable = PageRequest.of(safePage - 1, safeSize, Sort.by("id").ascending());
        return PagedResponse.from(repository.findAll(pageable));
    }

    public Factory findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new FactoryNotFoundException(id));
    }

    public Factory create(Factory factory) {
        factory.setId(null);
        return repository.save(factory);
    }

    public Factory update(Long id, Factory update) {
        Factory existing = repository.findById(id)
                .orElseThrow(() -> new FactoryNotFoundException(id));
        existing.setName(update.getName());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new FactoryNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
