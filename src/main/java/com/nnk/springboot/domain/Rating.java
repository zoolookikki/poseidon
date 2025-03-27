package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
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
    private String moodysRating;

    @Column(name = "sandp_rating", length = 125)
    private String sandPRating;

    @Column(length = 125)
    private String fitchRating;

    private Integer orderNumber;
}
