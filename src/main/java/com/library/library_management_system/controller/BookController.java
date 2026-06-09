package com.library.library_management_system.controller;

import com.library.library_management_system.dto.request.BookRequestDTO;
import com.library.library_management_system.dto.response.ApiResponse;
import com.library.library_management_system.dto.response.BookResponseDTO;
import com.library.library_management_system.enums.Category;
import com.library.library_management_system.service.BookService;
import com.library.library_management_system.service.IBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final IBookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponseDTO>> createBook(@Valid @RequestBody BookRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Book created successfully", bookService.createBook(dto)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BookResponseDTO>>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Books retrieved successfully", bookService.getAllBooks(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponseDTO>> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Book retrieved successfully", bookService.getBookById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponseDTO>> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Book updated successfully", bookService.updateBook(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Book deleted successfully", null));
    }

    @GetMapping("/search/title")
    public ResponseEntity<ApiResponse<List<BookResponseDTO>>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Books retrieved successfully", bookService.searchByTitle(title)));
    }

    @GetMapping("/search/category")
    public ResponseEntity<ApiResponse<List<BookResponseDTO>>> searchByCategory(@RequestParam Category category) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Books retrieved successfully", bookService.searchByCategory(category)));
    }

    @GetMapping("/search/author")
    public ResponseEntity<ApiResponse<List<BookResponseDTO>>> searchByAuthor(@RequestParam Long authorId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Books retrieved successfully", bookService.searchByAuthor(authorId)));
    }
}