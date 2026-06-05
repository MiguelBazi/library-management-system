package com.library.library_management_system.dto.request;

import com.library.library_management_system.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Category is required")
    private Category category;

    @NotNull(message = "Author ID is required")
    private Long authorId;
}