package com.example.demo.movies.recommendations;

import com.example.demo.movies.MovieDTOMapper;
import com.example.demo.movies.MovieResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final MovieDTOMapper movieDTOMapper;

    @GetMapping("/by-title")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<MovieResponseDTO> recommendByTitle(@RequestParam String title,
                                                   @RequestParam(defaultValue = "5") int count) {
        return recommendationService.recommendByTitle(title, count)
                .stream()
                .map(movieDTOMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
