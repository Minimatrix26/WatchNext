package com.example.demo.categories;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CategoryRequestDTO(
        @NotNull(message = "Name in the CategoryRequestDTO must NOT be null!")
        @NotEmpty(message = "Name in the CategoryRequestDTO must NOT be empty!")
        String name
) {
}
