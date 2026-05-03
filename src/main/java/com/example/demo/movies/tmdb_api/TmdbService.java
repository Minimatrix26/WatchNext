package com.example.demo.movies.tmdb_api;

import com.example.demo.movies.MovieRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TmdbService {

    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private final String apiKey = System.getenv("TMDB_API_KEY");
    private final RestTemplate restTemplate;

    public TmdbResponseDTO getDetailsFromApi(MovieRequestDTO movieRequestDTO) {
        int internalMovieId = getInternalMovieId(movieRequestDTO.title());

        String url = buildMovieDetailsUrl(internalMovieId);
        return fetchResponse(url, TmdbResponseDTO.class,
                "No details found for movie: " + movieRequestDTO.title());
    }

    public PosterPathDTO getPosterDTOByTitle(String title) {
        String url = buildSearchUrl(title);

        TMDBPosterResponse response = fetchResponse(url, TMDBPosterResponse.class,
                "No poster found for movie: " + title);

        List<TMDBPosterResult> results = response.getResults();

        if (results == null || results.isEmpty()) {
            throw new RuntimeException("No poster found for movie: " + title);
        }

        return new PosterPathDTO(results.get(0).getPoster_path());
    }

    private int getInternalMovieId(String title) {
        String url = buildSearchUrl(title);

        TMDBPartialResponse response = fetchResponse(url, TMDBPartialResponse.class,
                "No movie found with title: " + title);

        if (response.results() == null || response.results().isEmpty()) {
            throw new RuntimeException("No movie found with title: " + title);
        }

        return response.results().get(0).id();
    }

    private String buildSearchUrl(String title) {
        String encodedTitle = UriUtils.encode(title, StandardCharsets.UTF_8);
        return BASE_URL + "/search/movie?api_key=" + apiKey + "&query=" + encodedTitle;
    }

    private String buildMovieDetailsUrl(int movieId) {
        return BASE_URL + "/movie/" + movieId + "?api_key=" + apiKey;
    }

    private <T> T fetchResponse(String url, Class<T> responseType, String errorMessage) {
        T response = restTemplate.getForObject(url, responseType);

        if (response == null) {
            throw new RuntimeException(errorMessage);
        }

        return response;
    }
}