package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Entity
// TRANSACTION FINANCIERE
@Table(name = "trades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Identifiant du compte concerné 
    @Column(nullable = false, length = 30)
    @NotBlank(message = "Account is mandatory")
    private String account;

    // Type de transaction (achat, vente etc...)
    @Column(nullable = false, length = 30)
    @NotBlank(message = "Type is mandatory")
    private String type;

    // Quantité achetée.
    private Double buyQuantity;

    private Double sellQuantity;

    private Double buyPrice;

    private Double sellPrice;

    private Instant tradeDate;

    @Column(length = 125)
    private String security;

    @Column(length = 10)
    private String status;

    @Column(length = 125)
    private String trader;

    @Column(length = 125)
    private String benchmark;

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
