package io.github.lucasrech.productservice.adapters.out.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "FABRICANTE")
@Getter
@Setter
@NoArgsConstructor
public class JpaManufacturerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FABRICANTE")
    private Short id;

    @Column(name = "NOME_FANTASIA", length = 100)
    private String tradeName;

    @Column(name = "RAZAO_SOCIAL", length = 150)
    private String companyName;

    @Column(name = "CNPJ", length = 14)
    private String cnpj;

    @Column(name = "DT_INCLUSAO")
    private LocalDateTime inclusionDate;

    @Column(name = "DT_ALTERACAO")
    private LocalDateTime updateDate;
}
