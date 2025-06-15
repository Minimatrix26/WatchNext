package com.example.demo.wishlist;

import com.example.demo.movies.MovieResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<?> addToWishlist(@RequestBody WishlistRequest request) {
        wishlistService.addToWishlist(request.movieId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "{movieId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<?> removeFromWishlist(@PathVariable("movieId") Integer movieId) {
        wishlistService.removeFromWishlist(movieId);
        return ResponseEntity.ok().build();
    }
}
