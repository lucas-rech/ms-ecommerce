package io.github.lucasrech.productservice.domain.manufacturer;

public class Manufacturer {
    private Integer id;
    private String tradeName;
    private String companyName;
    private String cnpj;


    public Manufacturer(Integer id, String tradeName, String companyName, String cnpj) {
        this.id = id;
        this.tradeName = tradeName;
        this.companyName = companyName;
        this.cnpj = cnpj;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
