package com.library.library_management_system.dto.response;

import com.library.library_management_system.enums.Category;
import lombok.Data;

@Data
public class BookResponseDTO {

    private Long id;
    private String title;
    private String isbn;
    private Category category;
    private String authorName;
    private boolean available;
}