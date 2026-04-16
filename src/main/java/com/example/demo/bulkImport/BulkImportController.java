package com.example.demo.bulkImport;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/bulk")
@RequiredArgsConstructor
public class BulkImportController {

    private final BulkImportService bulkImportService;

    @PostMapping(path = "/bulk-import")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> importMovies() {
        try {
            bulkImportService.importMoviesFromExcel();
            return ResponseEntity.ok("Import finalizat cu succes.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Eroare la import: " + e.getMessage());
        }
    }
}