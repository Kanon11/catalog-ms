package com.example.catalogms.service;

import java.time.LocalDate;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.catalogms.dto.PagedResponse;
import com.example.catalogms.entity.Application;
import com.example.catalogms.exception.ApplicationNotFoundException;
import com.example.catalogms.repository.ApplicationRepository;

@Service
public class ApplicationService {

    private final ApplicationRepository repository;

    public ApplicationService(ApplicationRepository repository) {
        this.repository = repository;
    }

    private static final int MAX_PAGE_SIZE = 100;

    /**
     * Returns a page of applications. {@code page} is 1-based; {@code page}/{@code size} are
     * clamped to sane bounds. When {@code workerId} is non-null, only that worker's
     * applications are returned. Results are sorted by applicationId so paging is stable.
     */
    public PagedResponse<Application> findAll(int page, int size, Long workerId) {
        int safePage = Math.max(page, 1);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        Pageable pageable = PageRequest.of(safePage - 1, safeSize, Sort.by("applicationId").ascending());
        if (workerId != null) {
            return PagedResponse.from(repository.findByWorkerId(workerId, pageable));
        }
        return PagedResponse.from(repository.findAll(pageable));
    }

    public Application findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    /**
     * Submits a new application. The id is server-assigned; when the caller omits
     * {@code applicationDate} it defaults to today, and a fresh submission starts
     * as {@code PENDING} unless an explicit status is provided.
     */
    public Application create(Application application) {
        application.setApplicationId(null);
        if (application.getApplicationDate() == null) {
            application.setApplicationDate(LocalDate.now());
        }
        if (application.getStatus() == null || application.getStatus().isBlank()) {
            application.setStatus("PENDING");
        }
        return repository.save(application);
    }

    public Application update(Long id, Application update) {
        Application existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
        existing.setWorkerId(update.getWorkerId());
        existing.setApplicationDate(update.getApplicationDate());
        existing.setReasonForUnemployment(update.getReasonForUnemployment());
        existing.setStatus(update.getStatus());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ApplicationNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
