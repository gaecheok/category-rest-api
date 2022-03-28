package com.test.categoryrestapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Category {

    @Id
    @Min(value=1, message="id not 0")
    private Integer categoryId;

    @NotBlank(message="description required")
    private String description;

    private Integer referenceId;

    @Transient
    private List<Category> referencedList;

    public Category(){}
    public Category(Integer categoryId, String description, Integer referenceId) {
        this.categoryId = categoryId;
        this.description = description;
        this.referenceId = referenceId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public List<Category> getReferencedList() {
        return referencedList;
    }

    public void setReferencedList(List<Category> referencedList) {
        this.referencedList = referencedList;
    }
}
