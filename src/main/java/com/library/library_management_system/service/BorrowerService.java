package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.BorrowerRequestDTO;
import com.library.library_management_system.dto.response.BorrowerResponseDTO;
import com.library.library_management_system.entity.Borrower;
import com.library.library_management_system.exception.BusinessRuleException;
import com.library.library_management_system.exception.EntityNotFoundException;
import com.library.library_management_system.repository.BorrowerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerResponseDTO createBorrower(BorrowerRequestDTO dto) {
        log.info("Creating borrower with email: {}", dto.getEmail());
        if (borrowerRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessRuleException("Borrower already exists with email: " + dto.getEmail());
        }
        Borrower borrower = new Borrower();
        borrower.setName(dto.getName());
        borrower.setEmail(dto.getEmail());
        borrower.setPhoneNumber(dto.getPhoneNumber());
        return mapToDTO(borrowerRepository.save(borrower));
    }

    public List<BorrowerResponseDTO> getAllBorrowers() {
        log.info("Fetching all borrowers");
        return borrowerRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public BorrowerResponseDTO getBorrowerById(Long id) {
        log.info("Fetching borrower with id: {}", id);
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Borrower not found with id: " + id));
        return mapToDTO(borrower);
    }

    public BorrowerResponseDTO updateBorrower(Long id, BorrowerRequestDTO dto) {
        log.info("Updating borrower with id: {}", id);
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Borrower not found with id: " + id));
        if (!borrower.getEmail().equals(dto.getEmail()) &&
                borrowerRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessRuleException("Borrower already exists with email: " + dto.getEmail());
        }
        borrower.setName(dto.getName());
        borrower.setEmail(dto.getEmail());
        borrower.setPhoneNumber(dto.getPhoneNumber());
        return mapToDTO(borrowerRepository.save(borrower));
    }

    public void deleteBorrower(Long id) {
        log.info("Deleting borrower with id: {}", id);
        borrowerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Borrower not found with id: " + id));
        borrowerRepository.deleteById(id);
    }

    private BorrowerResponseDTO mapToDTO(Borrower borrower) {
        BorrowerResponseDTO dto = new BorrowerResponseDTO();
        dto.setId(borrower.getId());
        dto.setName(borrower.getName());
        dto.setEmail(borrower.getEmail());
        dto.setPhoneNumber(borrower.getPhoneNumber());
        return dto;
    }
}