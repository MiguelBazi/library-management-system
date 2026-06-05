package com.library.library_management_system.dto.response;

import lombok.Data;

@Data
public class BorrowerResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
}