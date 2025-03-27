package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rule_names")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 125)
    private String name;

    @Column(length = 125)
    private String description;

    @Column(length = 125)
    private String json;

    @Column(length = 512)
    private String template;

    @Column(length = 125)
    private String sqlStr;

    @Column(length = 125)
    private String sqlPart;
}
