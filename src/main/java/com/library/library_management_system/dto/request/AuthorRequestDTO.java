package com.library.library_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    private String biography;
}