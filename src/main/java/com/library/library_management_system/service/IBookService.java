package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.BookRequestDTO;
import com.library.library_management_system.dto.response.BookResponseDTO;
import com.library.library_management_system.enums.Category;
import org.springframework.data.domain.Page;
import java.util.List;

public interface IBookService {
    BookResponseDTO createBook(BookRequestDTO dto);
    Page<BookResponseDTO> getAllBooks(int page, int size);
    BookResponseDTO getBookById(Long id);
    BookResponseDTO updateBook(Long id, BookRequestDTO dto);
    void deleteBook(Long id);
    List<BookResponseDTO> searchByTitle(String title);
    List<BookResponseDTO> searchByCategory(Category category);
    List<BookResponseDTO> searchByAuthor(Long authorId);
}