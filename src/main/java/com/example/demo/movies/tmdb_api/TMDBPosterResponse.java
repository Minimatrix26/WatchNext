package com.example.demo.movies.tmdb_api;

import java.util.List;

public class TMDBPosterResponse {
    private List<TMDBPosterResult> results;

    public List<TMDBPosterResult> getResults() {
        return results;
    }

    public void setResults(List<TMDBPosterResult> results) {
        this.results = results;
    }
}
