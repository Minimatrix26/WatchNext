package com.example.demo.movies;

import org.springframework.stereotype.Service;


@Service
public class MovieDTOMapper {

    // Convert Movie to DTO
    public MovieResponseDTO toResponseDTO(Movie movie) {
        return new MovieResponseDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getCategoryId(),
                movie.getImdbId(),
                movie.getImdbScore(),
                movie.getDescription(),
                movie.getReleaseDate()
        );
    }


    // Convert RequestDTO to Movie
    public Movie toEntity(MovieRequestDTO movieResponseDTO) {
        return new Movie(
                movieResponseDTO.title(),
                movieResponseDTO.categoryId(),
                movieResponseDTO.imdbId(),
                movieResponseDTO.imdbScore(),
                movieResponseDTO.description(),
                movieResponseDTO.releaseDate()
        );
    }
}
