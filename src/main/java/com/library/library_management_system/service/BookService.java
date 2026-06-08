package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.BookRequestDTO;
import com.library.library_management_system.dto.response.BookResponseDTO;
import com.library.library_management_system.entity.Author;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.enums.Category;
import com.library.library_management_system.exception.BusinessRuleException;
import com.library.library_management_system.exception.EntityNotFoundException;
import com.library.library_management_system.repository.AuthorRepository;
import com.library.library_management_system.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final OpenLibraryService openLibraryService;

    public BookResponseDTO createBook(BookRequestDTO dto) {
        log.info("Creating book with title: {}", dto.getTitle());

        Author author;

        String fetchedAuthorName = openLibraryService.fetchAuthorName(dto.getIsbn());

        if (fetchedAuthorName != null) {
            author = authorRepository.findByNameIgnoreCase(fetchedAuthorName)
                    .orElseGet(() -> {
                        log.info("Creating new author from Open Library: {}", fetchedAuthorName);
                        Author newAuthor = new Author();
                        newAuthor.setName(fetchedAuthorName);
                        newAuthor.setBiography("Fetched from Open Library");
                        return authorRepository.save(newAuthor);
                    });
        } else {
            log.info("Open Library did not return author, using provided authorId: {}", dto.getAuthorId());
            author = authorRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + dto.getAuthorId()));
        }

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setCategory(dto.getCategory());
        book.setAuthor(author);
        book.setAvailable(true);

        return mapToDTO(bookRepository.save(book));
    }

    public List<BookResponseDTO> getAllBooks() {
        log.info("Fetching all books");
        return bookRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public BookResponseDTO getBookById(Long id) {
        log.info("Fetching book with id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        return mapToDTO(book);
    }

    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        log.info("Updating book with id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + dto.getAuthorId()));
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setCategory(dto.getCategory());
        book.setAuthor(author);
        return mapToDTO(bookRepository.save(book));
    }

    public void deleteBook(Long id) {
        log.info("Deleting book with id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        if (!book.isAvailable()) {
            throw new BusinessRuleException("Cannot delete book with id: " + id + " because it is currently borrowed");
        }
        bookRepository.deleteById(id);
    }

    public List<BookResponseDTO> searchByTitle(String title) {
        log.info("Searching books by title: {}", title);
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<BookResponseDTO> searchByCategory(Category category) {
        log.info("Searching books by category: {}", category);
        return bookRepository.findByCategory(category)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<BookResponseDTO> searchByAuthor(Long authorId) {
        log.info("Searching books by author id: {}", authorId);
        return bookRepository.findByAuthorId(authorId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private BookResponseDTO mapToDTO(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setCategory(book.getCategory());
        dto.setAuthorName(book.getAuthor().getName());
        dto.setAvailable(book.isAvailable());
        return dto;
    }
}