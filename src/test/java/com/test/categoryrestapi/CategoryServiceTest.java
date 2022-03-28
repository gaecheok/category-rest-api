package com.test.categoryrestapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

import com.test.categoryrestapi.model.Category;
import com.test.categoryrestapi.repository.CategoryRepository;
import com.test.categoryrestapi.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepo;

    @Test
    @DisplayName("insert category")
    void test_save() {
        //given
        Category category = new Category(100, "test", 0);
        given(categoryRepo.save(any()))
                .willReturn(category);
        given(categoryRepo.findById(100))
                .willReturn(Optional.ofNullable(category));

        //when
        Integer insertedCategoryId = categoryService.save(category).getCategoryId();

        //then
        Category findCategory = categoryRepo.findById(insertedCategoryId).get();

        assertEquals(category.getCategoryId(), findCategory.getCategoryId());
        assertEquals(category.getDescription(), findCategory.getDescription());
        assertEquals(category.getReferenceId(), findCategory.getReferenceId());
    }

    @Test
    @DisplayName("select all category")
    void test_getAllCategoryWithReference() {
        //given
        given(categoryRepo.findAllByReferenceIdEquals(0))
                .willReturn(categoryList());

        given(categoryRepo.findById(100))
                .willReturn(Optional.ofNullable(new Category(100,"test1",0)));
        given(categoryRepo.findById(200))
                .willReturn(Optional.ofNullable(new Category(200,"test2",0)));
        given(categoryRepo.findById(300))
                .willReturn(Optional.ofNullable(new Category(300,"test3",0)));

        //when
        List<Category> resultList = categoryService.getAllCategoryWithReference();

        //then
        assertEquals(resultList.size(), categoryList().size());
    }

    private List<Category> categoryList() {
        final List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(100,"test1",0));
        categoryList.add(new Category(200,"test2",0));
        categoryList.add(new Category(300,"test2",0));
        return categoryList;
    }

    @Test
    @DisplayName("select one category")
    void test_getCategoryWithReference() {
        //given
        given(categoryRepo.findById(100))
                .willReturn(Optional.ofNullable(new Category(100,"test1",0)));

        //when
        Category resultCategory = categoryService.getCategoryWithReference(100);

        //then
        assertEquals(resultCategory.getCategoryId(), 100);
    }




}
