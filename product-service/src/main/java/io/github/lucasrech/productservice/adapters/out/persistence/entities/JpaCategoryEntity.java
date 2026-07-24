package io.github.lucasrech.productservice.adapters.out.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "CATEGORIA")
@Getter
@Setter
@NoArgsConstructor
public class JpaCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CATEGORIA")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CATEGORIA_PAI")
    private JpaCategoryEntity parentCategory;

    @Column(name = "DESCRICAO", length = 400)
    private String description;

    @Column(name = "DT_INCLUSAO")
    private LocalDateTime inclusionDate;

    @Column(name = "DT_ALTERACAO")
    private LocalDateTime updateDate;

    @Column(name = "FL_ATIVO")
    private boolean isActive;
}
