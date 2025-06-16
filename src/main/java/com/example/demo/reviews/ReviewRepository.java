package com.example.demo.reviews;

import com.example.demo.movies.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByMovie(Movie movie);
}
