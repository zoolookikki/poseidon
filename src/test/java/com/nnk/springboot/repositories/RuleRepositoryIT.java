package com.nnk.springboot.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nnk.springboot.domain.Rule;

public class RuleRepositoryIT extends AbstractRepositoryIT<Rule, Integer> {

    @Autowired
    private RuleRepository ruleRepository;

    @Override
    protected Rule createEntity() {
        return new Rule(null, "Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
    }
    
    @Override
    protected JpaRepository<Rule, Integer> getRepository() {
        return ruleRepository;
    }

    @Override
    protected Integer getId(Rule rule) {
        return rule.getId();
    }

    @Override
    protected void verifyCreate(Rule rule) {
        assertThat(rule.getName()).isEqualTo("Rule Name");
        assertThat(rule.getDescription()).isEqualTo("Description");
        assertThat(rule.getJson()).isEqualTo("Json");
        assertThat(rule.getTemplate()).isEqualTo("Template");
        assertThat(rule.getSqlStr()).isEqualTo("SQL");
        assertThat(rule.getSqlPart()).isEqualTo("SQL Part");
    }

    @Override
    protected void updateEntity(Rule rule) {
        rule.setName("Rule Name updated");
        rule.setDescription("Description updated");
        rule.setJson("Json updated");
        rule.setTemplate("Template updated");
        rule.setSqlStr("SQL updated");
        rule.setSqlPart("SQL Part updated");
    }

    @Override
    protected void verifyUpdate(Rule rule) {
        assertThat(rule.getName()).isEqualTo("Rule Name updated");
        assertThat(rule.getDescription()).isEqualTo("Description updated");
        assertThat(rule.getJson()).isEqualTo("Json updated");
        assertThat(rule.getTemplate()).isEqualTo("Template updated");
        assertThat(rule.getSqlStr()).isEqualTo("SQL updated");
        assertThat(rule.getSqlPart()).isEqualTo("SQL Part updated");
    }
}
