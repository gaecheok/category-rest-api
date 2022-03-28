package com.test.categoryrestapi.repository;

import com.test.categoryrestapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByReferenceIdEquals(Integer referenceId);
}
