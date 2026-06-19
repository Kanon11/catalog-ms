package com.example.catalogms.dto;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 * Stable pagination envelope. {@code page} is 1-based (page=1 is the first page) to match
 * the API contract, even though Spring Data's {@link Page} is 0-based internally.
 */
public record PagedResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last) {

    public static <T> PagedResponse<T> from(Page<T> page) {
        return new PagedResponse<>(
                page.getContent(),
                page.getNumber() + 1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast());
    }
}
