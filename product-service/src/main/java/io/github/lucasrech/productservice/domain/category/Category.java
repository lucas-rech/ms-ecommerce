package io.github.lucasrech.productservice.domain.category;

import java.time.LocalDateTime;

public class Category {
    private Integer id;
    private Category parentCategory;
    private String description;
    private LocalDateTime inclusionDate;
    private LocalDateTime updateDate;

    public Category(Integer id, Category parentCategory, String description, LocalDateTime inclusionDate, LocalDateTime updateDate) {
        this.id = id;
        this.parentCategory = parentCategory;
        this.description = description;
        this.inclusionDate = inclusionDate;
        this.updateDate = updateDate;
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

    public LocalDateTime getInclusionDate() {
        return inclusionDate;
    }

    public void setInclusionDate(LocalDateTime inclusionDate) {
        this.inclusionDate = inclusionDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
