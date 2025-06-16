package com.example.demo.reviews;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<?> addReview(@RequestBody ReviewRequest request) {
        reviewService.addReview(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path ="{movieId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<ReviewResponse>> getReviews(@PathVariable Integer movieId) {
        return ResponseEntity.ok(reviewService.getReviewsForMovie(movieId));
    }
}
