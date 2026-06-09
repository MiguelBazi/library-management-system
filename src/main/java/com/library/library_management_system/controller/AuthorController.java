package com.library.library_management_system.controller;

import com.library.library_management_system.dto.request.AuthorRequestDTO;
import com.library.library_management_system.dto.response.ApiResponse;
import com.library.library_management_system.dto.response.AuthorResponseDTO;
import com.library.library_management_system.service.AuthorService;
import com.library.library_management_system.service.IAuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final IAuthorService authorService;

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorResponseDTO>> createAuthor(@Valid @RequestBody AuthorRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Author created successfully", authorService.createAuthor(dto)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AuthorResponseDTO>>> getAllAuthors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Authors retrieved successfully", authorService.getAllAuthors(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponseDTO>> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Author retrieved successfully", authorService.getAuthorById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponseDTO>> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Author updated successfully", authorService.updateAuthor(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Author deleted successfully", null));
    }
}