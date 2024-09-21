package com.example.demo.movies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query("SELECT m FROM Movie m WHERE m.title = ?1")
    Optional<Movie> findMovieByTitle(String title);

    @Query("SELECT m FROM Movie m WHERE " +
            "(m.releaseDate >= ?1) AND " +
            "(m.releaseDate <= ?2) " +
            "ORDER BY m.releaseDate ASC")
    Page<Movie> findMoviesByReleaseDateRange(LocalDate from, LocalDate to, Pageable pageable);
}
