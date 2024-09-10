package com.example.demo.movies.tmdb_api;

import com.example.demo.movies.MovieRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}
