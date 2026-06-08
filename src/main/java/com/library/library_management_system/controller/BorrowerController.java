package com.library.library_management_system.controller;

import com.library.library_management_system.dto.request.BorrowerRequestDTO;
import com.library.library_management_system.dto.response.ApiResponse;
import com.library.library_management_system.dto.response.BorrowerResponseDTO;
import com.library.library_management_system.service.BorrowerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/borrowers")
@RequiredArgsConstructor
public class BorrowerController {

    private final BorrowerService borrowerService;

    @PostMapping
    public ResponseEntity<ApiResponse<BorrowerResponseDTO>> createBorrower(@Valid @RequestBody BorrowerRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Borrower created successfully", borrowerService.createBorrower(dto)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BorrowerResponseDTO>>> getAllBorrowers() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Borrowers retrieved successfully", borrowerService.getAllBorrowers()));
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