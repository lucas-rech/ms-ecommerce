package io.github.lucasrech.productservice.adapters.out.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUTO_IMAGEM")
@Getter
@Setter
@NoArgsConstructor
public class JpaProductImageEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUTO_IMAGEM")
    private Long id;

    @Column(name = "S3_CODE", length = 1000)
    private String s3Code;

    @Column(name = "FL_DESTAQUE")
    private boolean isFeatured;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUTO")
    private JpaProductEntity product;

    @Column(name = "DT_INCLUSAO")
    private LocalDateTime inclusionDate;

    @Column(name = "DT_ALTERACAO")
    private LocalDateTime updateDate;
}
