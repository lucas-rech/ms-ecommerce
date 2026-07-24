package io.github.lucasrech.productservice.adapters.out.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PRODUTO")
@Getter
@Setter
@NoArgsConstructor
public class JpaProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUTO")
    private Long id;

    @Column(name = "CD_SKU", length = 50)
    private String cdSku;

    @Column(name = "CD_EAN", length = 14)
    private String cdEan;

    @Column(name = "NM_PRODUTO", length = 250)
    private String name;

    @Column(name = "DESCRICAO", length = 1000)
    private String description;

    @Column(name = "PRECO", precision = 15, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FABRICANTE")
    private JpaManufacturerEntity manufacturer;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaProductImageEntity> images;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "CATEGORIA_PRODUTO",
            joinColumns = @JoinColumn(name = "ID_PRODUTO"),
            inverseJoinColumns = @JoinColumn(name = "ID_CATEGORIA")
    )
    private List<JpaCategoryEntity> categories;

    @Column(name = "DT_INCLUSAO")
    private LocalDateTime inclusionDate;

    @Column(name = "DT_ALTERACAO")
    private LocalDateTime updateDate;

    @Column(name = "FL_ATIVO")
    private boolean isActive;
}
