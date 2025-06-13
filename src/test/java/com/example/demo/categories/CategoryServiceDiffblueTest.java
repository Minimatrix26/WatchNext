package com.example.demo.categories;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CategoryService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CategoryServiceDiffblueTest {
    @MockBean
    private CategoryDTOMapper categoryDTOMapper;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    /**
     * Method under test: {@link CategoryService#getCategories()}
     */
    @Test
    void testGetCategories() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<CategoryResponseDTO> actualCategories = categoryService.getCategories();

        // Assert
        verify(categoryRepository).findAll();
        assertTrue(actualCategories.isEmpty());
    }

    /**
     * Method under test: {@link CategoryService#getCategories()}
     */
    @Test
    void testGetCategories2() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        when(categoryRepository.findAll()).thenReturn(categoryList);
        when(categoryDTOMapper.toResponseDTO(Mockito.<Category>any())).thenThrow(new IllegalStateException("foo"));

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> categoryService.getCategories());
        verify(categoryDTOMapper).toResponseDTO(isA(Category.class));
        verify(categoryRepository).findAll();
    }

    /**
     * Method under test: {@link CategoryService#addNewCategory(CategoryRequestDTO)}
     */
    @Test
    void testAddNewCategory() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.findCategoryByName(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> categoryService.addNewCategory(new CategoryRequestDTO("Name")));
        verify(categoryRepository).findCategoryByName(eq("Name"));
    }

    /**
     * Method under test: {@link CategoryService#addNewCategory(CategoryRequestDTO)}
     */
    @Test
    void testAddNewCategory2() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category);
        Optional<Category> emptyResult = Optional.empty();
        when(categoryRepository.findCategoryByName(Mockito.<String>any())).thenReturn(emptyResult);

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        when(categoryDTOMapper.toEntity(Mockito.<CategoryRequestDTO>any())).thenReturn(category2);
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(1, "Name");

        when(categoryDTOMapper.toResponseDTO(Mockito.<Category>any())).thenReturn(categoryResponseDTO);

        // Act
        CategoryResponseDTO actualAddNewCategoryResult = categoryService.addNewCategory(new CategoryRequestDTO("Name"));

        // Assert
        verify(categoryDTOMapper).toEntity(isA(CategoryRequestDTO.class));
        verify(categoryDTOMapper).toResponseDTO(isA(Category.class));
        verify(categoryRepository).findCategoryByName(eq("Name"));
        verify(categoryRepository).save(isA(Category.class));
        assertSame(categoryResponseDTO, actualAddNewCategoryResult);
    }

    /**
     * Method under test: {@link CategoryService#addNewCategory(CategoryRequestDTO)}
     */
    @Test
    void testAddNewCategory3() {
        // Arrange
        Optional<Category> emptyResult = Optional.empty();
        when(categoryRepository.findCategoryByName(Mockito.<String>any())).thenReturn(emptyResult);
        when(categoryDTOMapper.toEntity(Mockito.<CategoryRequestDTO>any()))
                .thenThrow(new EntityNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> categoryService.addNewCategory(new CategoryRequestDTO("Name")));
        verify(categoryDTOMapper).toEntity(isA(CategoryRequestDTO.class));
        verify(categoryRepository).findCategoryByName(eq("Name"));
    }
}
