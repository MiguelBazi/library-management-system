package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.AuthorRequestDTO;
import com.library.library_management_system.dto.response.AuthorResponseDTO;
import org.springframework.data.domain.Page;
import java.util.List;

public interface IAuthorService {
    AuthorResponseDTO createAuthor(AuthorRequestDTO dto);
    Page<AuthorResponseDTO> getAllAuthors(int page, int size);
    AuthorResponseDTO getAuthorById(Long id);
    AuthorResponseDTO updateAuthor(Long id, AuthorRequestDTO dto);
    void deleteAuthor(Long id);
}