package com.library.library_management_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BorrowerRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email format")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;
}