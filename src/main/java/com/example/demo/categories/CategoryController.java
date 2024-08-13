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
    public List<Category> getAllCategories () {
        return categoryService.getCategories();
    }

    @PostMapping
    public void registerNewCategory (@RequestBody Category category) {
        categoryService.addNewCategory(category);
    }

    @DeleteMapping (path = "{categoryId}")
    public void deleteCategory (@PathVariable("categoryId") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    @PutMapping (path = "{categoryId}")
    public void updateCategory (@PathVariable("categoryId") Integer categoryId, @RequestParam String name) {
        categoryService.updateCategory(categoryId, name);
    }
}
