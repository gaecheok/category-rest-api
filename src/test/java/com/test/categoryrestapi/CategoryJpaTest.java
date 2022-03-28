package com.test.categoryrestapi;

import com.test.categoryrestapi.model.Category;
import com.test.categoryrestapi.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryJpaTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void saveAndSelect() {
        //given
        Category category = new Category(1000, "test10", 0);

        //when
        categoryRepository.save(category);

        //then
        assertThat(categoryRepository.findById(1000).isPresent()).isEqualTo(true);
    }

    @Test
    void delete() {
        //given
        Category category = new Category(1000, "test10", 0);
        categoryRepository.save(category);

        //when
        categoryRepository.deleteById(1000);

        //then
        assertThat(categoryRepository.findById(1000).isPresent()).isEqualTo(false);
    }

}
