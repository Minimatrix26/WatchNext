package com.example.demo.movies;

import java.time.LocalDate;

public record MovieResponseDTO(
        Integer id,
        String title,
        Integer categoryId,
        String categoryName, // pt a afisa numele categoriei
        String imdbId,
        Double imdbScore,
        String description,
        LocalDate releaseDate
) {
}
