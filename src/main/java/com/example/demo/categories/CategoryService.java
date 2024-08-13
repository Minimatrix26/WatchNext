package com.example.demo.categories;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDTOMapper categoryDTOMapper;

    public List<CategoryResponseDTO> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryDTOMapper::toResponseDTO)
                .toList();
    }

    public CategoryResponseDTO addNewCategory(CategoryRequestDTO categoryRequestDTO) {
        Optional<Category> categoryOptional = categoryRepository
                .findCategoryByName(categoryRequestDTO.name());

        if (categoryOptional.isPresent()) {
            throw new IllegalStateException("This Category name is taken");
        }

        Category category = categoryDTOMapper.toEntity(categoryRequestDTO);
        Category toSave = categoryRepository.save(category);

        return categoryDTOMapper.toResponseDTO(toSave);

    }

    public CategoryResponseDTO deleteCategory(Integer categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + categoryId + " not found"));

        categoryRepository.delete(category);

        return categoryDTOMapper.toResponseDTO(category);
    }

    @Transactional
    public CategoryResponseDTO updateCategory(Integer categoryId, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category with id" + categoryId + "does not exist"));

        if (categoryRequestDTO.name() != null &&
                !categoryRequestDTO.name().isEmpty() &&
            !category.getName().equals(categoryRequestDTO.name()) ) {
            category.setName(categoryRequestDTO.name());
        }

        return categoryDTOMapper.toResponseDTO(category);
    }
}
