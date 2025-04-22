package com.nnk.springboot.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.rule.RuleCreateRequestDto;
import com.nnk.springboot.dto.rule.RuleDto;
import com.nnk.springboot.dto.rule.RuleUpdateRequestDto;
import com.nnk.springboot.mapper.RuleMapper;
import com.nnk.springboot.mapper.IMapper;
import com.nnk.springboot.repositories.RuleRepository;
import com.nnk.springboot.service.rule.RuleServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RuleServiceImplTest extends AbstractCrudServiceTest<Rule, RuleDto, RuleCreateRequestDto, RuleUpdateRequestDto> {

    @InjectMocks
    private RuleServiceImpl ruleService;

    @Mock
    private RuleRepository ruleRepository;

    @Mock
    private RuleMapper ruleMapper;

    private Rule rule1, rule2;
    private RuleDto ruleDto1, ruleDto2;
    private RuleCreateRequestDto ruleCreateDto1;
    private RuleUpdateRequestDto ruleUpdateDto1;
    private Rule ruleUpdated1;
    private RuleDto ruleDtoUpdated1;

    @BeforeEach
    void setUp() {
        rule1 = new Rule(1,"Rule Name 1", "Description 1", "Json 1", "Template 1", "SQL 1", "SQL Part 1");
        rule2 = new Rule(2, "Rule Name 2", "Description 2", "Json 2", "Template 2", "SQL 2", "SQL Part 2");
        ruleDto1 = new RuleDto(1,"Rule Name 1", "Description 1", "Json 1", "Template 1", "SQL 1", "SQL Part 1");
        ruleDto2 = new RuleDto(2, "Rule Name 2", "Description 2", "Json 2", "Template 2", "SQL 2", "SQL Part 2");
        ruleCreateDto1 = new RuleCreateRequestDto();
        ruleCreateDto1.setName(rule1.getName());
        ruleCreateDto1.setDescription(rule1.getDescription());
        ruleCreateDto1.setTemplate(rule1.getTemplate());
        ruleCreateDto1.setJson(rule1.getJson());
        ruleCreateDto1.setSqlStr(rule1.getSqlStr());
        ruleCreateDto1.setSqlPart(rule1.getSqlPart());
        ruleUpdateDto1 = new RuleUpdateRequestDto();
        ruleUpdateDto1.setName(rule1.getName() + " updated");
        ruleUpdateDto1.setDescription(rule1.getDescription() + " updated");
        ruleUpdateDto1.setTemplate(rule1.getTemplate() + " updated");
        ruleUpdateDto1.setJson(rule1.getJson() + " updated");
        ruleUpdateDto1.setSqlStr(rule1.getSqlStr() + " updated");
        ruleUpdateDto1.setSqlPart(rule1.getSqlPart() + " updated");
        ruleUpdated1 = new Rule(1,"Rule Name 1 updated", "Description 1 updated", "Json 1 updated", "Template 1 updated", "SQL 1 updated", "SQL Part 1 updated"); 
        ruleDtoUpdated1 = new RuleDto(1, "Rule Name 1 updated", "Description 1 updated", "Json 1 updated", "Template 1 updated", "SQL 1 updated", "SQL Part 1 updated"); 
    }

    @Override
    protected Rule getEntity() {
        return rule1;
    }
    
    @Override
    protected List<Rule> getEntities() {
        return List.of(rule1, rule2);
    }

    @Override
    protected List<RuleDto> getDtos() {
        return List.of(ruleDto1, ruleDto2);
    }

    @Override
    protected AbstractCrudService<Rule, RuleDto, RuleCreateRequestDto, RuleUpdateRequestDto> getService() {
        return ruleService;
    }

    @Override
    protected JpaRepository<Rule, Integer> getRepository() {
        return ruleRepository;
    }

    @Override
    protected IMapper<Rule, RuleDto, RuleCreateRequestDto, RuleUpdateRequestDto> getMapper() {
        return ruleMapper;
    }

    @Override
    protected RuleCreateRequestDto getCreateDto() {
        return ruleCreateDto1;
    }
    
    @Override
    protected RuleDto getExpectedCreatedDto() {
        return ruleDto1;
    }
    
    @Override
    protected RuleUpdateRequestDto getUpdateDto() {
        return ruleUpdateDto1;
    }
    @Override
    protected Rule getUpdatedEntity() {
        return ruleUpdated1;
    }

    @Override
    protected RuleDto getExpectedUpdatedDto() {
        return ruleDtoUpdated1;
    }
}
