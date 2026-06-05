package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Book;
import com.library.library_management_system.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByCategory(Category category);
    List<Book> findByAuthorId(Long authorId);
}