package com.example.demo.movies;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping (path = "api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public List<MovieResponseDTO> getMovies() {
        return movieService.getMovies();
    }

    @PostMapping
    public MovieResponseDTO addNewMovie(@RequestBody MovieRequestDTO movieRequestDTO) {
        return movieService.addNewMovie(movieRequestDTO);
    }

    @DeleteMapping (path = "{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("movieId") Integer movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.noContent().build(); // Response code 204 (no content response)
    }

    @PutMapping (path = "{movieId}")
    public MovieResponseDTO updateMovie(@PathVariable("movieId") Integer movieId,
                            @RequestBody MovieRequestDTO movieRequestDTO) {
       return movieService.updateMovie(movieId, movieRequestDTO);
    }
}
