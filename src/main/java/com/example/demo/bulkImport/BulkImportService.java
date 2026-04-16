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
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BulkImportService {

    private final MovieService movieService;
    private final CategoryRepository categoryRepository;

    public void importMoviesFromExcel() throws Exception {
        // Using try-with-resources so SonarQube doesn't flag unclosed streams
        try (InputStream inputStream = new ClassPathResource("data/movies_dataset.xlsx").getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 9851; i <= 9990; i++) {
                processRow(sheet.getRow(i));
            }
        }
    }

    private void processRow(Row row) {
        if (row == null) return;

        String title = row.getCell(1).getStringCellValue().trim();
        String genreCell = row.getCell(2).getStringCellValue().trim();

        if (title.isEmpty() || genreCell.isEmpty()) return;

        String firstGenre = genreCell.split(",")[0].trim();
        Optional<Category> categoryOpt = categoryRepository.findCategoryByName(firstGenre);

        if (categoryOpt.isEmpty()) {
            log.warn("Categorie inexistentă: {} (film: {})", firstGenre, title);
            return;
        }

        saveMovie(title, categoryOpt.get().getId());
    }

    private void saveMovie(String title, Integer categoryId) {
        MovieRequestDTO dto = new MovieRequestDTO(
                title,
                categoryId,
                null,
                0.0,
                null,
                null
        );

        try {
            movieService.addNewMovie(dto);
        } catch (Exception e) {
            log.error("Eroare la filmul '{}': {}", title, e.getMessage());
        }
    }
}