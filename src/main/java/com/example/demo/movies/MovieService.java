package com.example.demo.movies;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public void addNewMovie(Movie movie) {
        Optional<Movie> movieOptional = movieRepository
                .findMovieByTitle(movie.getTitle());

        if (movieOptional.isPresent()) {
            throw new IllegalStateException("This movie already exists");
        }

        movieRepository.save(movie);
    }

    public void deleteMovie(Integer movieId) {

        boolean exists = movieRepository.existsById(movieId);

        if (!exists) {
            throw new IllegalStateException("Movie with id " + movieId + " does not exist");
        }

        movieRepository.deleteById(movieId);
    }

    @Transactional
    public void updateMovie(Integer movieId, String title, Integer categoryId, String imdbId, Double imdbScore, String description, LocalDate releaseDate) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalStateException("Movie with id " + movieId + " does not exist"));

        if ( title != null &&
            !title.isEmpty() &&
            !title.equals(movie.getTitle()) ) {
            movie.setTitle(title);
        }

        if (categoryId != null && categoryId != movie.getCategoryId()) {
            movie.setCategoryId(categoryId);
        }

        if (imdbId != null &&
            !imdbId.isEmpty() &&
            !imdbId.equals(movie.getImdbId()) ) {
            movie.setImdbId(imdbId);
        }

        if (imdbScore != null &&
            imdbScore >= 0) {
            movie.setImdbScore(imdbScore);
        }

        if (description != null &&
        !description.isEmpty() &&
        !description.equals(movie.getDescription())) {
            movie.setDescription(description);
        }

        if (releaseDate != null) {
            movie.setReleaseDate(releaseDate);
        }

        //TODO: more accurate logging
    }
}
