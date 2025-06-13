package com.example.demo.movies.tmdb_api;

import com.example.demo.movies.MovieRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TmdbService {

    private final String apiKey = System.getenv("TMDB_API_KEY");
    private final RestTemplate restTemplate;

    private TMDBPartialResponse fetchInternalMovieId(String movieTitle) {
        String api_url = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=" + movieTitle;

        return restTemplate.getForObject(api_url, TMDBPartialResponse.class);
    }

    public TmdbResponseDTO getDetailsFromApi(MovieRequestDTO movieRequestDTO) {

        TMDBPartialResponse searchResponse = fetchInternalMovieId(movieRequestDTO.title());
        if (searchResponse == null || searchResponse.results().isEmpty()) {
            throw new RuntimeException("TmdbService: No movie found with title " + movieRequestDTO.title());
        }

        int internalMovieId = searchResponse.results().get(0).id();

        String apiUrl = "https://api.themoviedb.org/3/movie/" + internalMovieId + "?api_key=" + apiKey;
        return restTemplate.getForObject(apiUrl, TmdbResponseDTO.class);

    }

    public PosterPathDTO getPosterDTOByTitle(String title) {
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey +
                "&query=" + UriUtils.encode(title, StandardCharsets.UTF_8);

        TMDBPosterResponse response = restTemplate.getForObject(url, TMDBPosterResponse.class);
        List<TMDBPosterResult> results = Objects.requireNonNull(response).getResults();

        if (results != null && !results.isEmpty()) {
            return new PosterPathDTO(results.get(0).getPoster_path());
        } else {
            throw new RuntimeException("TmdbService: Filmul" + title + "nu a fost gasit pentru a returna posterul...");
        }
    }
}
