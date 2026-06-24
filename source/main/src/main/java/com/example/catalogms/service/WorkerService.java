package com.example.catalogms.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.catalogms.dto.PagedResponse;
import com.example.catalogms.entity.Worker;
import com.example.catalogms.exception.WorkerNotFoundException;
import com.example.catalogms.repository.WorkerRepository;

@Service
public class WorkerService {

    private final WorkerRepository repository;

    public WorkerService(WorkerRepository repository) {
        this.repository = repository;
    }

    private static final int MAX_PAGE_SIZE = 100;

    /**
     * Returns a page of workers. {@code page} is 1-based; {@code page}/{@code size} are
     * clamped to sane bounds. Results are sorted by workerId so paging is stable.
     */
    public PagedResponse<Worker> findAll(int page, int size) {
        int safePage = Math.max(page, 1);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        Pageable pageable = PageRequest.of(safePage - 1, safeSize, Sort.by("workerId").ascending());
        return PagedResponse.from(repository.findAll(pageable));
    }

    public Worker findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new WorkerNotFoundException(id));
    }

    public Worker create(Worker worker) {
        worker.setWorkerId(null);
        return repository.save(worker);
    }

    public Worker update(Long id, Worker update) {
        Worker existing = repository.findById(id)
                .orElseThrow(() -> new WorkerNotFoundException(id));
        existing.setNid(update.getNid());
        existing.setWorkerName(update.getWorkerName());
        existing.setMobileNo(update.getMobileNo());
        existing.setFactoryId(update.getFactoryId());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new WorkerNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
