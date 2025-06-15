package com.example.demo.wishlist;

import com.example.demo.movies.Movie;
import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findAllByUser(User user);
    Optional<Wishlist> findByUserAndMovie(User user, Movie movie);
    void deleteByUserAndMovie(User user, Movie movie);
}
