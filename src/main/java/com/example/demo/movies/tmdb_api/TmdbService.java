package com.example.demo.movies.tmdb_api;

import com.example.demo.movies.MovieRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class TmdbService {

    // 1. Let Spring handle the environment variables via application.properties / application.yml
    @Value("${tmdb.api.key}")
    private String apiKey;

    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3";
    private final RestTemplate restTemplate;

    public TmdbResponseDTO getDetailsFromApi(MovieRequestDTO movieRequestDTO) {
        int internalMovieId = getInternalMovieId(movieRequestDTO.title());

        // 2. Use UriComponentsBuilder for safe URL construction
        String apiUrl = UriComponentsBuilder.fromHttpUrl(TMDB_BASE_URL)
                .path("/movie/{id}")
                .queryParam("api_key", apiKey)
                .buildAndExpand(internalMovieId)
                .toUriString();

        return restTemplate.getForObject(apiUrl, TmdbResponseDTO.class);
    }

    public PosterPathDTO getPosterDTOByTitle(String title) {
        TMDBPosterResponse response = fetchSearchResponse(title, TMDBPosterResponse.class);

        if (response == null || response.getResults() == null || response.getResults().isEmpty()) {
            log.error("TmdbService: Filmul '{}' nu a fost gasit pentru a returna posterul.", title);
            throw new RuntimeException("TmdbService: Filmul " + title + " nu a fost gasit.");
        }

        return new PosterPathDTO(response.getResults().get(0).getPoster_path());
    }

    // --- Helper Methods to reduce Cognitive Complexity ---

    private int getInternalMovieId(String title) {
        TMDBPartialResponse response = fetchSearchResponse(title, TMDBPartialResponse.class);

        if (response == null || response.results() == null || response.results().isEmpty()) {
            log.error("TmdbService: No movie found with title '{}'", title);
            throw new RuntimeException("TmdbService: No movie found with title " + title);
        }

        return response.results().get(0).id();
    }

    /**
     * 3. A generic helper method to handle ALL searches.
     * This guarantees consistent URL encoding and prevents code duplication.
     */
    private <T> T fetchSearchResponse(String query, Class<T> responseType) {
        String url = UriComponentsBuilder.fromHttpUrl(TMDB_BASE_URL)
                .path("/search/movie")
                .queryParam("api_key", apiKey)
                .queryParam("query", query)
                .toUriString(); // UriComponentsBuilder handles the UTF-8 encoding automatically!

        return restTemplate.getForObject(url, responseType);
    }
}