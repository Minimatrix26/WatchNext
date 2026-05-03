package com.example.demo.wishlist;

import com.example.demo.movies.MovieResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = WishlistController.FRONTEND_URL)
@RestController
@RequestMapping(path = "api/v1/wishlist")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
public class WishlistController {

    static final String FRONTEND_URL = "http://localhost:4200";

    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> getWishlist() {
        return ResponseEntity.ok(wishlistService.getUserWishlist());
    }

    @PostMapping
    public ResponseEntity<Void> addToWishlist(@RequestBody WishlistRequest request) {
        wishlistService.addToWishlist(request.movieId());
        return buildOkResponse();
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Integer movieId) {
        wishlistService.removeFromWishlist(movieId);
        return buildOkResponse();
    }

    private ResponseEntity<Void> buildOkResponse() {
        return ResponseEntity.ok().build();
    }
}