package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
    Optional<RuleName> findByName(String username);
}
