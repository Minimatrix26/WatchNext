package com.example.demo.movies;


import com.example.demo.movies.tmdb_api.PosterPathDTO;
import com.example.demo.movies.tmdb_api.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping (path = "api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final TmdbService tmdbService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<MovieResponseDTO> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public MovieResponseDTO getMovie(@PathVariable Integer id) {
        return movieService.getMovie(id);
    }

    @GetMapping(path = "/search-by-title")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<MovieResponseDTO> getMoviesByTitle(@RequestParam String title) {
        return movieService.searchByTitleContaining(title);
    }

    @GetMapping(path = "/poster")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<PosterPathDTO> getPosterPath(@RequestParam String title) {
        PosterPathDTO dto = tmdbService.getPosterDTOByTitle(title);
        return ResponseEntity.ok(dto);
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

    @GetMapping("/by-category")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Page<MovieResponseDTO>> getMoviesByCategory(
            @RequestParam Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        return ResponseEntity.ok(movieService.getMoviesByCategory(categoryId, page, size));
    }

}
