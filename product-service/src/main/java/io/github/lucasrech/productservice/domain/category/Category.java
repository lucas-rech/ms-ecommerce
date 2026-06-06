package io.github.lucasrech.productservice.domain.category;

public class Category {
    private Integer id;
    private Category parentCategory;
    private String description;

    public Category(Integer id, Category parentCategory, String description) {
        this.id = id;
        this.parentCategory = parentCategory;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
