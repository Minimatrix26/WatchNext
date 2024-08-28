package com.example.demo.movies;


import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record MovieRequestDTO(
        @NotBlank
        String title,
        @NotBlank
        Integer categoryId,
        @NotBlank
        String imdbId,
        @NotBlank
        Double imdbScore,
        @NotBlank
        String description,
        @NotBlank
        LocalDate releaseDate
) {
}
