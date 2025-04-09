package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
// REGLES METIERS DE VALIDATION OU DE TRAITEMENT.
@Table(name = "rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 125)
    // nom de la règle.
    private String name;

    @Column(nullable = false, length = 125)
    // description détaillée de la règle.
    private String description;

    @Column(length = 125)
    // représentation json de la règle.
    private String json;

    @Column(length = 512)
    // modèle utilisé pour afficher ou générer du texte.
    private String template;

    @Column(length = 125)
    // requête SQL représentant la règle.
    private String sqlStr;

    @Column(length = 125)
    // autre partie de la requête.
    private String sqlPart;
}
