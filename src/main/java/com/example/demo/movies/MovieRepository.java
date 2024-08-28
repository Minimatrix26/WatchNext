package com.example.demo.movies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query("SELECT m FROM Movie m WHERE m.title = ?1")
    Optional<Movie> findMovieByTitle(String title);
}
