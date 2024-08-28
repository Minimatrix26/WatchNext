package com.example.demo.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (path = "api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseDTO> getAllCategories () {
        return categoryService.getCategories();
    }

    @PostMapping
    public CategoryResponseDTO createNewCategory (@RequestBody CategoryRequestDTO categoryRequestDTO) {

        return categoryService.addNewCategory(categoryRequestDTO);
    }

    @DeleteMapping (path = "{categoryId}")
    public ResponseEntity<Void> deleteCategory (@PathVariable("categoryId") Integer categoryId) {

        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build(); // Return code 204 (no content)
    }

    @PutMapping (path = "{categoryId}")
    public CategoryResponseDTO updateCategory (@PathVariable("categoryId") Integer categoryId, @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return categoryService.updateCategory(categoryId, categoryRequestDTO);
    }
}
