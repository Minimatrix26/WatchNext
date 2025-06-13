package com.example.demo.categories;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CategoryRepositoryDiffblueTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    /**
     * Method under test: {@link CategoryRepository#findCategoryByName(String)}
     */
    @Test
    void testFindCategoryByName() {

        // Arrange
        String name = "Action";
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);

        // Act
        Optional<Category> actualFindCategoryByNameResult = this.categoryRepository.findCategoryByName(name);

        // Assert
        assertThat(actualFindCategoryByNameResult)
                .isPresent()
                .hasValueSatisfying(c -> assertThat(c.getName()).isEqualTo(name));
    }
}
