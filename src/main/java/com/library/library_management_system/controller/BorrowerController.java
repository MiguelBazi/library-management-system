package com.library.library_management_system.controller;

import com.library.library_management_system.dto.request.BorrowerRequestDTO;
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
    public ResponseEntity<BorrowerResponseDTO> createBorrower(@Valid @RequestBody BorrowerRequestDTO dto) {
        return ResponseEntity.ok(borrowerService.createBorrower(dto));
    }

    @GetMapping
    public ResponseEntity<List<BorrowerResponseDTO>> getAllBorrowers() {
        return ResponseEntity.ok(borrowerService.getAllBorrowers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowerResponseDTO> getBorrowerById(@PathVariable Long id) {
        return ResponseEntity.ok(borrowerService.getBorrowerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowerResponseDTO> updateBorrower(@PathVariable Long id, @Valid @RequestBody BorrowerRequestDTO dto) {
        return ResponseEntity.ok(borrowerService.updateBorrower(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrower(@PathVariable Long id) {
        borrowerService.deleteBorrower(id);
        return ResponseEntity.noContent().build();
    }
}