package io.github.lucasrech.productservice.adapters.out.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PRODUTO_IMAGEM")
@Getter
@Setter
@NoArgsConstructor
public class JpaProductImageEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "S3_CODE", length = 1000)
    private String s3Code;

    @Column(name = "FL_DESTAQUE")
    private boolean isFeatured;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUTO")
    private JpaProductEntity product;
}
