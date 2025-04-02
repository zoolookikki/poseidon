package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // unique = true garanti qu’aucun utilisateur ne peut avoir le même nom.
    @Column(nullable = false, unique = true, length = 125)
    private String username;

    @Column(nullable = false, length = 125)
    private String password;

    @Column(nullable = false, length = 125)
    private String fullname;

    @Column(nullable = false, length = 125)
    private String role;
}
