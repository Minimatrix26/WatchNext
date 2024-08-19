package com.example.demo.categories;

import org.springframework.stereotype.Service;

@Service
public class CategoryDTOMapper {

    // Convert Category to DTO
    public CategoryResponseDTO toResponseDTO(Category category) {
        return new CategoryResponseDTO(category.getId(), category.getName());
    }


    // Convert Request DTO to Category
    public Category toEntity(CategoryRequestDTO categoryRequestDTO) {
        return new Category(categoryRequestDTO.name());
    }
}
