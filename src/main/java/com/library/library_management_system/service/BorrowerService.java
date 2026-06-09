package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.BorrowerRequestDTO;
import com.library.library_management_system.dto.response.BorrowerResponseDTO;
import com.library.library_management_system.entity.Borrower;
import com.library.library_management_system.exception.BusinessRuleException;
import com.library.library_management_system.exception.EntityNotFoundException;
import com.library.library_management_system.repository.BorrowerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class BorrowerService implements IBorrowerService {

    private final BorrowerRepository borrowerRepository;
    private final ModelMapper modelMapper;

    public BorrowerResponseDTO createBorrower(BorrowerRequestDTO dto) {
        log.info("Creating borrower with email: {}", dto.getEmail());
        if (borrowerRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessRuleException("Borrower already exists with email: " + dto.getEmail());
        }
        Borrower borrower = modelMapper.map(dto, Borrower.class);
        return modelMapper.map(borrowerRepository.save(borrower), BorrowerResponseDTO.class);
    }

    public Page<BorrowerResponseDTO> getAllBorrowers(int page, int size) {
        log.info("Fetching all borrowers page: {} size: {}", page, size);
        return borrowerRepository.findAll(PageRequest.of(page, size))
                .map(borrower -> modelMapper.map(borrower, BorrowerResponseDTO.class));
    }

    public BorrowerResponseDTO getBorrowerById(Long id) {
        log.info("Fetching borrower with id: {}", id);
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Borrower not found with id: " + id));
        return modelMapper.map(borrower, BorrowerResponseDTO.class);
    }

    public BorrowerResponseDTO updateBorrower(Long id, BorrowerRequestDTO dto) {
        log.info("Updating borrower with id: {}", id);
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Borrower not found with id: " + id));
        if (!borrower.getEmail().equals(dto.getEmail()) &&
                borrowerRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessRuleException("Borrower already exists with email: " + dto.getEmail());
        }
        modelMapper.map(dto, borrower);
        return modelMapper.map(borrowerRepository.save(borrower), BorrowerResponseDTO.class);
    }

    public void deleteBorrower(Long id) {
        log.info("Deleting borrower with id: {}", id);
        borrowerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Borrower not found with id: " + id));
        borrowerRepository.deleteById(id);
    }
}