package com.example.demo.movies;

import com.example.demo.movies.tmdb_api.TmdbResponseDTO;
import com.example.demo.movies.tmdb_api.TmdbService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieDTOMapper movieDTOMapper;
    private final TmdbService tmdbService;

    public List<MovieResponseDTO> getMovies() {
        return movieRepository.findAll()
                .stream()
                .map(movieDTOMapper::toResponseDTO)
                .toList();
    }

    public MovieResponseDTO addNewMovie(MovieRequestDTO movieRequestDTO) {
        Optional<Movie> movieOptional = movieRepository
                .findMovieByTitle(movieRequestDTO.title());

        if (movieOptional.isPresent()) {
            throw new IllegalStateException("This movie already exists");
        }

        Movie movie = movieDTOMapper.toEntity(movieRequestDTO);
        TmdbResponseDTO tmdbResponseDTO = tmdbService.getDetailsFromApi(movieRequestDTO);
        movie.setImdbId(tmdbResponseDTO.imdb_id());
        movie.setImdbScore(tmdbResponseDTO.vote_average());
        movie.setReleaseDate(LocalDate.parse(tmdbResponseDTO.release_date()));
        movie.setDescription(tmdbResponseDTO.overview());

        Movie toSave = movieRepository.save(movie);

        return movieDTOMapper.toResponseDTO(toSave);

    }

    public MovieResponseDTO deleteMovie(Integer movieId) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("This movie does not exist"));

        movieRepository.delete(movie);
        return movieDTOMapper.toResponseDTO(movie);
    }

    @Transactional
    public MovieResponseDTO updateMovie(Integer movieId, MovieRequestDTO movieRequestDTO) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie with id " + movieId + " does not exist"));

        if (movieRequestDTO.title() != null &&
            !movieRequestDTO.title().isEmpty() &&
            !movieRequestDTO.title().equals(movie.getTitle()) ) {
            movie.setTitle(movieRequestDTO.title());
        }

        if (movieRequestDTO.categoryId() != null && movieRequestDTO.categoryId() != movie.getCategoryId()) {
            movie.setCategoryId(movieRequestDTO.categoryId());
        }

        if (movieRequestDTO.imdbId() != null &&
            !movieRequestDTO.imdbId().isEmpty() &&
            !movieRequestDTO.imdbId().equals(movie.getImdbId()) ) {
            movie.setImdbId(movieRequestDTO.imdbId());
        }

        if (movieRequestDTO.imdbScore() != null &&
                movieRequestDTO.imdbScore() >= 0) {
            movie.setImdbScore(movieRequestDTO.imdbScore());
        }

        if (movieRequestDTO.description() != null &&
        !movieRequestDTO.description().isEmpty() &&
        !movieRequestDTO.description().equals(movie.getDescription())) {
            movie.setDescription(movieRequestDTO.description());
        }

        if (movieRequestDTO.releaseDate() != null) {
            movie.setReleaseDate(movieRequestDTO.releaseDate());
        }

        return movieDTOMapper.toResponseDTO(movie);

        //TODO: more accurate logging
    }

    public List<MovieResponseDTO> findMoviesByQuery(int limit, LocalDate from, LocalDate to) {
        Pageable pageable = PageRequest.of(0, limit);
        Page<Movie> movies = movieRepository.findMoviesByReleaseDateRange(from, to, pageable);

        return movies.stream()
                .map(movieDTOMapper::toResponseDTO)
                .toList();
    }
}
