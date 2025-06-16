package com.example.demo.movies;

import com.example.demo.categories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MovieDTOMapper {

    private final CategoryRepository categoryRepository;

    // Convert Movie to DTO
    public MovieResponseDTO toResponseDTO(Movie movie) {

        String categoryName = categoryRepository.findById(movie.getCategoryId()).get().getName();
        return new MovieResponseDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getCategoryId(),
                categoryName,
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
