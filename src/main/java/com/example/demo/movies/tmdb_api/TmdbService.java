package com.example.demo.movies.tmdb_api;

import com.example.demo.movies.MovieRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TmdbService {

    @Value("${tmdb.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;

    private TMDBPartialResponse fetchInternalMovieId(String movieTitle) {
        String api_url = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=" + movieTitle;

        return restTemplate.getForObject(api_url, TMDBPartialResponse.class);
    }

    public TmdbResponseDTO getDetailsFromApi(MovieRequestDTO movieRequestDTO) {
        //        int internalMovieId = fetchInternalMovieId(movieRequestDTO.title()).id();
//
//       String api_url = "https://api.themoviedb.org/3/movie/" + internalMovieId + "?api_key=" + apiKey;
//       return restTemplate.getForObject(api_url, TmdbResponseDTO.class);

        TMDBPartialResponse searchResponse = fetchInternalMovieId(movieRequestDTO.title());
        if (searchResponse == null || searchResponse.results().isEmpty()) {
            throw new RuntimeException("TmdbService: No movie found with title " + movieRequestDTO.title());
        }

        int internalMovieId = searchResponse.results().get(0).id();

        String apiUrl = "https://api.themoviedb.org/3/movie/" + internalMovieId + "?api_key=" + apiKey;
        return restTemplate.getForObject(apiUrl, TmdbResponseDTO.class);

    }
}
