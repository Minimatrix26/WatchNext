package com.example.demo.movies;

import java.time.LocalDate;

public record MovieRequestDTO(
        String title,
        Integer categoryId,
        String imdbId,
        Double imdbScore,
        String description,
        LocalDate releaseDate
) {
}
