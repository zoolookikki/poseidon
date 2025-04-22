package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

@Entity
// Point sur une courbe de taux d’intérêt.
@Table(name = "curve_points")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Référence de la courbe à laquelle appartient ce point (1, 2, 3, etc...) => de 0 à 255 (1 à 255 réellement)
    @Column(nullable = false)
    private Integer curveId;

    // Date de validité du point.
    private Instant asOfDate;

    // Durée en année (1.00 = 1 an, 0.50 = 6 mois)
    @Column(nullable = false)
    private Double term;

    // La valeur au terme en % ou en valeur.
    @Column(nullable = false)
    private Double valuePoint;

    // Spécificité Hibernate : remplis automatiquement le champ avec la date/heure au moment de l’insertion uniquement.
    @CreationTimestamp
    private Instant creationDate;
    
    // Constructeur utilisé pour le test d'intégration du repository
    public CurvePoint(Integer curveId, Double term, Double valuePoint) {
        this(null, curveId, term, valuePoint);
    }    

    // Constructeur utilisé pour le test d'intégration du service.
    public CurvePoint(Integer id, Integer curveId, Double term, Double valuePoint) {
        this.id = id;
        this.curveId = curveId;
        this.term = term;
        this.valuePoint = valuePoint;
    }    
}
