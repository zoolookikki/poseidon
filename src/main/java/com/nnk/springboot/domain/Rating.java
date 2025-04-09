package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
// NOTATION FINANCIERES : attribuées à des titres, obligations ou entités (entreprises, banques, etc.)
@Table(name = "ratings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 125)
    // notation attribuée par l’agence Moody’s
    private String moodysRating;

    @Column(name = "sandp_rating", length = 125)
    // une autre notation.
    private String sandPRating;

    @Column(length = 125)
    // Notation attribuée par Fitch.
    private String fitchRating;

    // indice d'ordre lié à la qualité du crédit => de 0 à 255 (1 à 255 réellement)
    private Integer orderNumber;
}
