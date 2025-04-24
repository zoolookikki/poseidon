package com.nnk.springboot.domain;

import jakarta.persistence.*;
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
    private String account;

    // Type de transaction (achat, vente etc...)
    @Column(nullable = false, length = 30)
    private String type;

    // Quantité achetée.
    @Column(nullable = false)
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
    
    // Constructeur utilisé pour le test d'intégration du repository.
    public Trade(String account, String type, Double buyQuantity) {
        this(null, account, type, buyQuantity);
    }

    // Constructeur utilisé pour le test d'intégration du service.
    public Trade(Integer id, String account, String type, Double buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity; 
    }
}
