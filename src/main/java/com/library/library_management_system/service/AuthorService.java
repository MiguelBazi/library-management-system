package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.AuthorRequestDTO;
import com.library.library_management_system.dto.response.AuthorResponseDTO;
import com.library.library_management_system.entity.Author;
import com.library.library_management_system.exception.BusinessRuleException;
import com.library.library_management_system.exception.EntityNotFoundException;
import com.library.library_management_system.repository.AuthorRepository;
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
public class AuthorService implements IAuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorResponseDTO createAuthor(AuthorRequestDTO dto) {
        log.info("Creating author with name: {}", dto.getName());
        Author author = modelMapper.map(dto, Author.class);
        return modelMapper.map(authorRepository.save(author), AuthorResponseDTO.class);
    }

    public Page<AuthorResponseDTO> getAllAuthors(int page, int size) {
        log.info("Fetching all authors page: {} size: {}", page, size);
        return authorRepository.findAll(PageRequest.of(page, size))
                .map(author -> modelMapper.map(author, AuthorResponseDTO.class));
    }

    public AuthorResponseDTO getAuthorById(Long id) {
        log.info("Fetching author with id: {}", id);
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
        return modelMapper.map(author, AuthorResponseDTO.class);
    }

    public AuthorResponseDTO updateAuthor(Long id, AuthorRequestDTO dto) {
        log.info("Updating author with id: {}", id);
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
        modelMapper.map(dto, author);
        return modelMapper.map(authorRepository.save(author), AuthorResponseDTO.class);
    }

    public void deleteAuthor(Long id) {
        log.info("Deleting author with id: {}", id);
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            throw new BusinessRuleException("Cannot delete author with id: " + id + " because they have books in the system");
        }
        authorRepository.deleteById(id);
    }
}