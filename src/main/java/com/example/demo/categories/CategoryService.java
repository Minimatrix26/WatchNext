package com.example.demo.categories;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void addNewCategory(Category category) {
        Optional<Category> categoryOptional = categoryRepository
                .findCategoryByName(category.getName());

        if (categoryOptional.isPresent()) {
            throw new IllegalStateException("This Category name is taken");
        }
        categoryRepository.save(category);


    }

    public void deleteCategory(Integer categoryId) {
        boolean exists = categoryRepository.existsById(categoryId);

        if (!exists) {
            throw new IllegalStateException("Category with id" + categoryId + "does not exist");
        }

        categoryRepository.deleteById(categoryId);
    }

    @Transactional
    public void updateCategory(Integer categoryId, String name) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalStateException("Category with id" + categoryId + "does not exist"));

        if (name != null &&
                !name.isEmpty() &&
            !category.getName().equals(name)) {
            category.setName(name);
        }
    }
}
