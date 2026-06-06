package io.github.lucasrech.productservice.adapters.out.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "CATEGORIA")
@Getter
@Setter
@NoArgsConstructor
public class JpaCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private JpaCategoryEntity parentCategory;

    @Column(name = "DESCRICAO", length = 400)
    private String description;
}
