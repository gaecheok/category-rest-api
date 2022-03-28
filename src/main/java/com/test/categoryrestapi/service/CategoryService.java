package com.test.categoryrestapi.service;

import com.test.categoryrestapi.model.Category;
import com.test.categoryrestapi.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category save(Category category) {

        //변경하려는 참조아이디가 디비에 존재하는지 체크
        if (category.getReferenceId() > 0) {
            if (!categoryRepository.findById(category.getReferenceId()).isPresent()) {
                throw new NoSuchElementException("ReferenceId Not Exist");
            }
        }

        return categoryRepository.save(category);
    }

    public void delete(Integer categoryId) {
        List<Category> referencedList = getReferencedList(categoryId);
        deleteReference(referencedList);
        categoryRepository.deleteById(categoryId);
    }

    private void deleteReference(List<Category> referencedList) {
        for(Category category : referencedList) {
            if (category.getReferencedList().size() > 0) {
                deleteReference(category.getReferencedList());
            }
            categoryRepository.deleteById(category.getCategoryId());
        }
    }

    //카테고리 전체 조회
    public List<Category> getAllCategoryWithReference() {
        List<Category> list = categoryRepository.findAllByReferenceIdEquals(0);
        for(Category category : list) {
            category.setReferencedList(
                    getCategoryWithReference(category.getCategoryId()).getReferencedList());
        }
        return list;
    }

    //카테고리 단건 조회
    public Category getCategoryWithReference(Integer categoryId) throws NoSuchElementException{
        Category category = categoryRepository.findById(categoryId).get();
        List<Category> referencedList = getReferencedList(categoryId);
        category.setReferencedList(referencedList);
        return category;
    }

    //하위 카테고리 조회
    private List<Category> getReferencedList(Integer categoryId) {
        List<Category> referencedList = categoryRepository.findAllByReferenceIdEquals(categoryId);
        if (referencedList.size() > 0) {
            for(Category category : referencedList) {
                List<Category> tmpList = getReferencedList(category.getCategoryId());
                category.setReferencedList(tmpList);
            }
        }
        return referencedList;
    }

}
