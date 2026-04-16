package com.example.demo.wishlist;

import com.example.demo.movies.MovieResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 1. Replaced the hardcoded URL with an injected property
@CrossOrigin(origins = "${frontend.allowed.origin}")
@RestController
@RequestMapping(path = "api/v1/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<MovieResponseDTO>> getWishlist() {
        return ResponseEntity.ok(wishlistService.getUserWishlist());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    // 2. Replaced <?> with <Void> to explicitly show no body is returned
    public ResponseEntity<Void> addToWishlist(@RequestBody WishlistRequest request) {
        wishlistService.addToWishlist(request.movieId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "{movieId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    // 3. Replaced <?> with <Void> to explicitly show no body is returned
    public ResponseEntity<Void> removeFromWishlist(@PathVariable("movieId") Integer movieId) {
        wishlistService.removeFromWishlist(movieId);
        return ResponseEntity.ok().build();
    }
}