package com.example.demo.movies.tmdb_api;

public class PosterPathDTO {
    private String posterPath;

    public PosterPathDTO() {}

    public PosterPathDTO(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}