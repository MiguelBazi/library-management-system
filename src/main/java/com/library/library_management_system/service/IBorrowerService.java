package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.BorrowerRequestDTO;
import com.library.library_management_system.dto.response.BorrowerResponseDTO;
import org.springframework.data.domain.Page;
import java.util.List;

public interface IBorrowerService {
    BorrowerResponseDTO createBorrower(BorrowerRequestDTO dto);
    Page<BorrowerResponseDTO> getAllBorrowers(int page, int size);
    BorrowerResponseDTO getBorrowerById(Long id);
    BorrowerResponseDTO updateBorrower(Long id, BorrowerRequestDTO dto);
    void deleteBorrower(Long id);
}