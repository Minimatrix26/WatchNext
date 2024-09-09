package com.example.demo.movies.tmdb_api;

public record TmdbResponseDTO(
        String id,
        String imdb_id,
        Double vote_average,
        String release_date,
        String overview
) {
}
