package com.example.catalogms.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.catalogms.dto.PagedResponse;
import com.example.catalogms.entity.Book;
import com.example.catalogms.exception.BookNotFoundException;
import com.example.catalogms.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    private static final int MAX_PAGE_SIZE = 100;

    /**
     * Returns a page of books. {@code page} is 1-based; {@code page}/{@code size} are
     * clamped to sane bounds. Results are sorted by id so paging is stable.
     */
    public PagedResponse<Book> findAll(int page, int size) {
        int safePage = Math.max(page, 1);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        Pageable pageable = PageRequest.of(safePage - 1, safeSize, Sort.by("id").ascending());
        return PagedResponse.from(repository.findAll(pageable));
    }

    public Book findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book create(Book book) {
        book.setId(null);
        return repository.save(book);
    }

    public Book update(Long id, Book update) {
        Book existing = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        existing.setTitle(update.getTitle());
        existing.setAuthor(update.getAuthor());
        existing.setIsbn(update.getIsbn());
        existing.setPrice(update.getPrice());
        existing.setDescription(update.getDescription());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
