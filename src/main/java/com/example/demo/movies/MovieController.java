package com.example.demo.movies;


import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping (path = "api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<MovieResponseDTO> getMovies() {
        return movieService.getMovies();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public MovieResponseDTO addNewMovie(@RequestBody MovieRequestDTO movieRequestDTO) {
        return movieService.addNewMovie(movieRequestDTO);
    }

    @DeleteMapping (path = "{movieId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public MovieResponseDTO deleteMovie(@PathVariable("movieId") Integer movieId) {
        return movieService.deleteMovie(movieId);
    }

    @PutMapping (path = "{movieId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public MovieResponseDTO updateMovie(@PathVariable("movieId") Integer movieId,
                            @RequestBody MovieRequestDTO movieRequestDTO) {
       return movieService.updateMovie(movieId, movieRequestDTO);
    }

    @GetMapping(path = "/query")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<MovieResponseDTO>> getMoviesByQuery(
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        List<MovieResponseDTO> movies = movieService.findMoviesByQuery(limit, from, to);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
}
