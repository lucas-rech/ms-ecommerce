package io.github.lucasrech.productservice.domain.manufacturer;

import java.time.LocalDateTime;

public class Manufacturer {
    private Short id;
    private String tradeName;
    private String companyName;
    private String cnpj;
    private LocalDateTime inclusionDate;
    private LocalDateTime updateDate;
    private boolean isActive;


    public Manufacturer(Short id, String tradeName, String companyName, String cnpj, LocalDateTime inclusionDate, LocalDateTime updateDate) {
        this.id = id;
        this.tradeName = tradeName;
        this.companyName = companyName;
        this.cnpj = cnpj;
        this.inclusionDate = inclusionDate;
        this.updateDate = updateDate;
        this.isActive = true;
    }

    public Manufacturer() {
        this.inclusionDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
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
