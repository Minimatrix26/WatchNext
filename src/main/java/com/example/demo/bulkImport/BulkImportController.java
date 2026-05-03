package com.example.demo.bulkImport;

import com.example.demo.categories.Category;
import com.example.demo.categories.CategoryRepository;
import com.example.demo.movies.MovieRequestDTO;
import com.example.demo.movies.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/bulk")
@RequiredArgsConstructor
@Slf4j
public class BulkImportController {

    private final MovieService movieService;
    private final CategoryRepository categoryRepository;

    private static final String FILE_PATH = "data/movies_dataset.xlsx";
    private static final int START_ROW = 9851;
    private static final int END_ROW = 9990;

    @PostMapping(path = "/bulk-import")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> importMovies() {
        try (InputStream inputStream = new ClassPathResource(FILE_PATH).getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            processSheet(sheet);

            return ResponseEntity.ok("Import finalizat cu succes.");
        } catch (Exception e) {
            log.error("Eroare la import: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Eroare la import: " + e.getMessage());
        }
    }

    private void processSheet(Sheet sheet) {
        for (int i = START_ROW; i <= END_ROW; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            processRow(row);
        }
    }

    private void processRow(Row row) {
        try {
            String title = getCellValue(row, 1);
            String genreCell = getCellValue(row, 2);

            if (title.isEmpty() || genreCell.isEmpty()) return;

            String firstGenre = extractFirstGenre(genreCell);
            Optional<Category> categoryOpt = categoryRepository.findCategoryByName(firstGenre);

            if (categoryOpt.isEmpty()) {
                log.warn("Categorie inexistentă: {} (film: {})", firstGenre, title);
                return;
            }

            MovieRequestDTO dto = buildMovieDTO(title, categoryOpt.get().getId());
            movieService.addNewMovie(dto);

        } catch (Exception e) {
            log.error("Eroare la procesarea rândului: {}", e.getMessage(), e);
        }
    }

    private String getCellValue(Row row, int cellIndex) {
        return row.getCell(cellIndex).getStringCellValue().trim();
    }

    private String extractFirstGenre(String genreCell) {
        return genreCell.split(",")[0].trim();
    }

    private MovieRequestDTO buildMovieDTO(String title, Integer categoryId) {
        return new MovieRequestDTO(
                title,
                categoryId,
                null,
                0.0,
                null,
                null
        );
    }
}