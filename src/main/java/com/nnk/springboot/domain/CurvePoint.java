package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
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

    private Integer curveId;

    private Instant asOfDate;

    private Double term;

    private Double value;

    private Instant creationDate;
}
