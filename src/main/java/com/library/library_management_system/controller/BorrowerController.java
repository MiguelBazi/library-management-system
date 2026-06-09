package com.library.library_management_system.controller;

import com.library.library_management_system.dto.request.BorrowerRequestDTO;
import com.library.library_management_system.dto.response.ApiResponse;
import com.library.library_management_system.dto.response.BorrowerResponseDTO;
import com.library.library_management_system.service.BorrowerService;
import com.library.library_management_system.service.IBorrowerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/borrowers")
@RequiredArgsConstructor
public class BorrowerController {

    private final IBorrowerService borrowerService;

    @PostMapping
    public ResponseEntity<ApiResponse<BorrowerResponseDTO>> createBorrower(@Valid @RequestBody BorrowerRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Borrower created successfully", borrowerService.createBorrower(dto)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BorrowerResponseDTO>>> getAllBorrowers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Borrowers retrieved successfully", borrowerService.getAllBorrowers(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BorrowerResponseDTO>> getBorrowerById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Borrower retrieved successfully", borrowerService.getBorrowerById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BorrowerResponseDTO>> updateBorrower(@PathVariable Long id, @Valid @RequestBody BorrowerRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Borrower updated successfully", borrowerService.updateBorrower(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBorrower(@PathVariable Long id) {
        borrowerService.deleteBorrower(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Borrower deleted successfully", null));
    }
}