package io.github.lucasrech.productservice.domain.product;

import io.github.lucasrech.productservice.domain.category.Category;
import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Product {
    private Long id;
    private String skuCode;
    private String eanCode;
    private String name;
    private String description;
    private Manufacturer manufacturer;
    private BigDecimal price;
    private List<Category> categories;
    private List<ProductImage> images;
    private LocalDateTime inclusionDate;
    private LocalDateTime updateDate;
    private boolean isActive;

    public Product(Long id, String skuCode, String eanCode, String name, String description, Manufacturer manufacturer, BigDecimal price, List<Category> categories, List<ProductImage> images, LocalDateTime inclusionDate, LocalDateTime updateDate, boolean isActive) {
        this.id = id;
        this.skuCode = skuCode;
        this.eanCode = eanCode;
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.price = price;
        this.categories = categories;
        this.images = images;
        this.inclusionDate = inclusionDate;
        this.updateDate = updateDate;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getEanCode() {
        return eanCode;
    }

    public void setEanCode(String eanCode) {
        this.eanCode = eanCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
