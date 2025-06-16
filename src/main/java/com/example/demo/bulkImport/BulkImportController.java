package com.example.demo.bulkImport;

import com.example.demo.categories.Category;
import com.example.demo.categories.CategoryRepository;
import com.example.demo.movies.MovieRequestDTO;
import com.example.demo.movies.MovieService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Optional;

@RestController
@RequestMapping (path = "api/v1/bulk")
@RequiredArgsConstructor
public class BulkImportController {

    private final MovieService movieService;
    private final CategoryRepository categoryRepository;

    @PostMapping(path = "/bulk-import")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> importMovies() {
        try {
            InputStream inputStream = new ClassPathResource("data/movies_dataset.xlsx").getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 9851; i <= 9990 ; i++) { // index 137 = linia 138 în Excel
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String title = row.getCell(1).getStringCellValue().trim();
                String genreCell = row.getCell(2).getStringCellValue().trim();

                if (title.isEmpty() || genreCell.isEmpty()) continue;

                String firstGenre = genreCell.split(",")[0].trim();
                Optional<Category> categoryOpt = categoryRepository.findCategoryByName(firstGenre);
                if (categoryOpt.isEmpty()) {
                    System.out.println("Categorie inexistentă: " + firstGenre + " (film: " + title + ")");
                    continue;
                }

                Integer categoryId = categoryOpt.get().getId();
                MovieRequestDTO dto = new MovieRequestDTO(
                        title,
                        categoryId,
                        null, // imdbId - completat în service
                        0.0, // imdbScore - completat în service
                        null, // description - completat în service
                        null  // releaseDate - completat în service
                );

                try {
                    movieService.addNewMovie(dto);
                } catch (Exception e) {
                    System.out.println("Eroare la filmul '" + title + "': " + e.getMessage());
                    // continuăm cu următorul
                }
            }

            return ResponseEntity.ok("Import finalizat cu succes.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Eroare la import,"  + e.getMessage());
        }
    }
}
