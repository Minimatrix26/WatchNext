package com.example.demo.reviews;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = ReviewController.FRONTEND_URL)
@RestController
@RequestMapping(path = "api/v1/reviews")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
public class ReviewController {

    static final String FRONTEND_URL = "http://localhost:4200";

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> addReview(@RequestBody ReviewRequest request) {
        reviewService.addReview(request);
        return buildOkResponse();
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<List<ReviewResponse>> getReviews(@PathVariable Integer movieId) {
        return ResponseEntity.ok(reviewService.getReviewsForMovie(movieId));
    }

    private ResponseEntity<Void> buildOkResponse() {
        return ResponseEntity.ok().build();
    }
}