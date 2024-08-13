package com.example.demo.movies;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping (path = "api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }

    @PostMapping
    public void addNewMovie(@RequestBody Movie movie) {
        movieService.addNewMovie(movie);
    }

    @DeleteMapping (path = "{movieId}")
    public void deleteMovie(@PathVariable("movieId") Integer movieId) {
        movieService.deleteMovie(movieId);
    }

    @PutMapping (path = "{movieId}")
    public void updateMovie(@PathVariable("movieId") Integer movieId,
                            @RequestParam(required = false) String title,
                            @RequestParam(required = false) Integer categoryId,
                            @RequestParam(required = false) String imdbId,
                            @RequestParam(required = false) Double imdbScore,
                            @RequestParam(required = false) String description,
                            @RequestParam(required = false) LocalDate releaseDate) {
        movieService.updateMovie(movieId, title, categoryId, imdbId, imdbScore, description, releaseDate);
    }
}
