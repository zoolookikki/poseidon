package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rule;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RuleRepository extends JpaRepository<Rule, Integer> {
    Optional<Rule> findByName(String username);
}
