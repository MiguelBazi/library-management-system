package com.library.library_management_system;

import com.library.library_management_system.dto.request.AuthorRequestDTO;
import com.library.library_management_system.dto.response.AuthorResponseDTO;
import com.library.library_management_system.entity.Author;
import com.library.library_management_system.exception.EntityNotFoundException;
import com.library.library_management_system.repository.AuthorRepository;
import com.library.library_management_system.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AuthorService authorService;

    private Author author;
    private AuthorRequestDTO requestDTO;
    private AuthorResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("George Orwell");
        author.setBiography("English novelist");

        requestDTO = new AuthorRequestDTO();
        requestDTO.setName("George Orwell");
        requestDTO.setBiography("English novelist");

        responseDTO = new AuthorResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("George Orwell");
        responseDTO.setBiography("English novelist");
    }

    @Test
    void createAuthor_ShouldReturnAuthorResponseDTO() {
        when(modelMapper.map(requestDTO, Author.class)).thenReturn(author);
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        when(modelMapper.map(author, AuthorResponseDTO.class)).thenReturn(responseDTO);

        AuthorResponseDTO result = authorService.createAuthor(requestDTO);

        assertNotNull(result);
        assertEquals("George Orwell", result.getName());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void getAuthorById_WhenExists_ShouldReturnAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(modelMapper.map(author, AuthorResponseDTO.class)).thenReturn(responseDTO);

        AuthorResponseDTO result = authorService.getAuthorById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getAuthorById_WhenNotExists_ShouldThrowEntityNotFoundException() {
        when(authorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> authorService.getAuthorById(999L));
    }

    @Test
    void deleteAuthor_WhenHasBooks_ShouldThrowBusinessRuleException() {
        author.setBooks(java.util.List.of(new com.library.library_management_system.entity.Book()));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        assertThrows(com.library.library_management_system.exception.BusinessRuleException.class,
                () -> authorService.deleteAuthor(1L));
    }
}