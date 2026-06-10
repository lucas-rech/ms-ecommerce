package io.github.lucasrech.productservice.domain.product;

import java.time.LocalDateTime;

public class ProductImage {
    private Long id;
    private String s3Code;
    private boolean isFeatured;
    private LocalDateTime inclusionDate;
    private LocalDateTime updateDate;

    public ProductImage(Long id, String s3Code, boolean isFeatured, LocalDateTime inclusionDate, LocalDateTime updateDate) {
        this.id = id;
        this.s3Code = s3Code;
        this.isFeatured = isFeatured;
        this.inclusionDate = inclusionDate;
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getS3Code() {
        return s3Code;
    }

    public void setS3Code(String s3Code) {
        this.s3Code = s3Code;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
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
