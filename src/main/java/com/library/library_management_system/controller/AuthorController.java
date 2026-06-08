package com.library.library_management_system.controller;

import com.library.library_management_system.dto.request.AuthorRequestDTO;
import com.library.library_management_system.dto.response.ApiResponse;
import com.library.library_management_system.dto.response.AuthorResponseDTO;
import com.library.library_management_system.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorResponseDTO>> createAuthor(@Valid @RequestBody AuthorRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Author created successfully", authorService.createAuthor(dto)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorResponseDTO>>> getAllAuthors() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Authors retrieved successfully", authorService.getAllAuthors()));
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