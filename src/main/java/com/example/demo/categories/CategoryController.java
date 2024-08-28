package com.example.demo.categories;

import lombok.RequiredArgsConstructor;
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
    public CategoryResponseDTO deleteCategory (@PathVariable("categoryId") Integer categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

    @PutMapping (path = "{categoryId}")
    public CategoryResponseDTO updateCategory (@PathVariable("categoryId") Integer categoryId, @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return categoryService.updateCategory(categoryId, categoryRequestDTO);
    }
}
