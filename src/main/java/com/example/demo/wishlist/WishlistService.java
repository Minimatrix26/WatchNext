package com.example.demo.wishlist;

import com.example.demo.movies.Movie;
import com.example.demo.movies.MovieDTOMapper;
import com.example.demo.movies.MovieRepository;
import com.example.demo.movies.MovieResponseDTO;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final MovieDTOMapper movieDTOMapper;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void addToWishlist(Integer movieId) {
        User user = getCurrentUser();
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Filmul nu există"));

        wishlistRepository.findByUserAndMovie(user, movie)
                .orElseGet(() -> wishlistRepository.save(new Wishlist(null, user, movie)));
    }

    @Transactional
    public void removeFromWishlist(Integer movieId) {
        User user = getCurrentUser();
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Filmul nu există"));

        wishlistRepository.deleteByUserAndMovie(user, movie);
    }

    public List<MovieResponseDTO> getUserWishlist() {
        User user = getCurrentUser();
        return wishlistRepository.findAllByUser(user).stream()
                .map(Wishlist::getMovie)
                .map(movieDTOMapper::toResponseDTO)
                .toList();
    }
}
