package io.github.lucasrech.productservice.domain.product;

public class ProductImage {
    private Long id;
    private String s3Code;
    private boolean isFeatured;

    public ProductImage(Long id, String s3Code, boolean isFeatured) {
        this.id = id;
        this.s3Code = s3Code;
        this.isFeatured = isFeatured;
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
}
