package com.example.demo.movies.recommendations;

import com.example.demo.movies.Movie;
import com.example.demo.movies.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RecommendationService {

    private final MovieRepository movieRepository;
    private final List<List<Double>> similarityMatrix;
    private final List<String> titles;

    public RecommendationService(MovieRepository movieRepository) throws IOException {
        this.movieRepository = movieRepository;
        this.titles = loadTitles();
        this.similarityMatrix = loadSimilarityMatrix();
    }

    private List<String> loadTitles() throws IOException {
        List<String> titleList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/data/movies.csv"))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length > 1) {
                    titleList.add(parts[1].trim());
                }
            }
        }
        return titleList;
    }

    private List<List<Double>> loadSimilarityMatrix() throws IOException {
        List<List<Double>> matrix = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/data/similarity.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Double> row = Arrays.stream(line.split(","))
                        .map(Double::parseDouble)
                        .collect(Collectors.toList());
                matrix.add(row);
            }
        }
        return matrix;
    }

    private int findIndexByTitle(String title) {
        String normalizedInput = normalize(title);
        for (int i = 0; i < titles.size(); i++) {
            if (normalize(titles.get(i)).equals(normalizedInput)) {
                return i;
            }
        }
        return -1;
    }

    private String normalize(String input) {
        return input.toLowerCase().trim().replaceAll("[^a-z0-9]", "");
    }

    public List<Movie> recommendByTitle(String title, int count) {
        int index = findIndexByTitle(title);
        if (index == -1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Filmul \"" + title + "\" nu a fost găsit în fișierul movies.csv");
        }

        List<Double> similarities = similarityMatrix.get(index);
        List<Integer> sortedIndices = IntStream.range(0, similarities.size())
                .boxed()
                .sorted((i, j) -> Double.compare(similarities.get(j), similarities.get(i)))
                .collect(Collectors.toList());

        List<Movie> recommendations = new ArrayList<>();
        int found = 0;
        for (int i = 1; i < sortedIndices.size() && found < count; i++) {
            String recommendedTitle = titles.get(sortedIndices.get(i));
            Optional<Movie> movieOpt = movieRepository.findByTitleIgnoreCase(recommendedTitle);
            if (movieOpt.isPresent()) {
                recommendations.add(movieOpt.get());
                found++;
            }
        }
        return recommendations;
    }
}
