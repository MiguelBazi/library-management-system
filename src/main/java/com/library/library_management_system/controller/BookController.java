package com.library.library_management_system.controller;

import com.library.library_management_system.dto.request.BookRequestDTO;
import com.library.library_management_system.dto.response.BookResponseDTO;
import com.library.library_management_system.enums.Category;
import com.library.library_management_system.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO dto) {
        return ResponseEntity.ok(bookService.createBook(dto));
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequestDTO dto) {
        return ResponseEntity.ok(bookService.updateBook(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<BookResponseDTO>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.searchByTitle(title));
    }

    @GetMapping("/search/category")
    public ResponseEntity<List<BookResponseDTO>> searchByCategory(@RequestParam Category category) {
        return ResponseEntity.ok(bookService.searchByCategory(category));
    }

    @GetMapping("/search/author")
    public ResponseEntity<List<BookResponseDTO>> searchByAuthor(@RequestParam Long authorId) {
        return ResponseEntity.ok(bookService.searchByAuthor(authorId));
    }
}