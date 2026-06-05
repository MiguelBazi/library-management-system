package com.library.library_management_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowerRequestDTO {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email format")
    private String email;

    @NotNull(message = "Phone number is required")
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;
}