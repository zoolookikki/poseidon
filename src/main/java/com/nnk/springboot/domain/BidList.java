package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "bid_lists")
/*
Non utilisation de @Data car génère equals() et hashCode(), ce qui peut être problématique pour les entités JPA (vu sur plusieurs sites) => à la place :
@Getter + @Setter + @NoArgsConstructor + @AllArgsConstructor
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//important pour afficher le contenu des objets simplement avec log4j2.
@ToString
public class BidList {

    // Indique que id est la clé primaire.
    @Id
    // Indique que la valeur de la clé primaire est générée automatiquement par la base de données, en utilisant une auto-incrémentation.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "Account is mandatory")
    private String account;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "Type is mandatory")
    private String type;

    private Double bidQuantity;

    private Double askQuantity;

    private Double bid;

    private Double ask;

    @Column(length = 125)
    private String benchmark;

    private Instant bidListDate;

    @Column(length = 125)
    private String commentary;

    @Column(length = 125)
    private String security;

    @Column(length = 10)
    private String status;

    @Column(length = 125)
    private String trader;

    @Column(length = 125)
    private String book;

    @Column(length = 125)
    private String creationName;

    private Instant creationDate;

    @Column(length = 125)
    private String revisionName;

    private Instant revisionDate;

    @Column(length = 125)
    private String dealName;

    @Column(length = 125)
    private String dealType;

    @Column(length = 125)
    private String sourceListId;

    @Column(length = 125)
    private String side;
}
