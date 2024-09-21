package com.example.demo.movies.tmdb_api;

import java.util.List;

public record TMDBPartialResponse(
        List<TMDBPartialResult> results
) {
}
