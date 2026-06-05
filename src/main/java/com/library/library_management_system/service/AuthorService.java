package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.AuthorRequestDTO;
import com.library.library_management_system.dto.response.AuthorResponseDTO;
import com.library.library_management_system.entity.Author;
import com.library.library_management_system.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorResponseDTO createAuthor(AuthorRequestDTO dto) {
        log.info("Creating author with name: {}", dto.getName());
        Author author = new Author();
        author.setName(dto.getName());
        author.setBiography(dto.getBiography());
        Author saved = authorRepository.save(author);
        return mapToDTO(saved);
    }

    public List<AuthorResponseDTO> getAllAuthors() {
        log.info("Fetching all authors");
        return authorRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AuthorResponseDTO getAuthorById(Long id) {
        log.info("Fetching author with id: {}", id);
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        return mapToDTO(author);
    }

    public AuthorResponseDTO updateAuthor(Long id, AuthorRequestDTO dto) {
        log.info("Updating author with id: {}", id);
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        author.setName(dto.getName());
        author.setBiography(dto.getBiography());
        return mapToDTO(authorRepository.save(author));
    }

    public void deleteAuthor(Long id) {
        log.info("Deleting author with id: {}", id);
        authorRepository.deleteById(id);
    }

    private AuthorResponseDTO mapToDTO(Author author) {
        AuthorResponseDTO dto = new AuthorResponseDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setBiography(author.getBiography());
        return dto;
    }
}