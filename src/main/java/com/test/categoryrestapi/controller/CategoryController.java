package com.test.categoryrestapi.controller;

import com.test.categoryrestapi.model.Category;
import com.test.categoryrestapi.service.CategoryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class CategoryController {

    final CategoryService categoryService;

    public CategoryController(CategoryService categoryservice) {
        this.categoryService = categoryservice;
    }

    /**
     * 카테고리 전체 조회
     * @return 전체 카테고리 목록
     */
    @GetMapping(value="/category")
    public ResponseEntity<?> findAllCategory() {
        List<Category> list = categoryService.getAllCategoryWithReference();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(list, headers, HttpStatus.OK);
    }

    /**
     * 카테고리 단건 조회
     * @param categoryId
     * @return
     */
    @GetMapping(value="/category/{categoryId}")
    public ResponseEntity<?> findByIdCategory(@PathVariable(value = "categoryId") Integer categoryId) {

        Category category = categoryService.getCategoryWithReference(categoryId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(category, headers, HttpStatus.OK);
    }

    /**
     * 카테고리 등록
     * @param category
     * @return
     */
    @PostMapping(value="/category")
    public ResponseEntity<?> insertCategory(@Valid @RequestBody Category category) {
        categoryService.save(category);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>("INSERT OK", headers, HttpStatus.CREATED);
    }

    /**
     * 카테고리 수정
     * @param categoryId
     * @param category
     * @return
     */
    @PutMapping(value="/category/{categoryId}")
    public ResponseEntity<?> updateCategory(
            @PathVariable(value = "categoryId") Integer categoryId,
            @Valid @RequestBody Category category) {
        category.setCategoryId(categoryId);
        categoryService.save(category);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>("UPDATE OK", headers, HttpStatus.OK);
    }

    /**
     * 카테고리 삭제
     * @param categoryId
     * @return
     */
    @DeleteMapping(value="/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "categoryId") Integer categoryId) {
        categoryService.delete(categoryId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>("DELETE OK", headers, HttpStatus.OK);
    }


}
