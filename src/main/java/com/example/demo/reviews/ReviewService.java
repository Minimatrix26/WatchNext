package com.example.demo.reviews;

import com.example.demo.movies.Movie;
import com.example.demo.movies.MovieRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void addReview(ReviewRequest request) {
        User user = getCurrentUser();
        Movie movie = movieRepository.findById(request.movieId())
                .orElseThrow(() -> new RuntimeException("Filmul nu există"));

        Review review = Review.builder()
                .movie(movie)
                .user(user)
                .text(request.text())
                .build();

        reviewRepository.save(review);
    }

    public List<ReviewResponse> getReviewsForMovie(Integer movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Filmul nu există"));

        return reviewRepository.findAllByMovie(movie).stream()
                .map(r -> new ReviewResponse(r.getUser().getUsername(), r.getText()))
                .toList();
    }
}
