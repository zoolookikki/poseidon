package com.nnk.springboot.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.rule.RuleCreateRequestDto;
import com.nnk.springboot.dto.rule.RuleDto;
import com.nnk.springboot.dto.rule.RuleUpdateRequestDto;
import com.nnk.springboot.mapper.RuleMapper;
import com.nnk.springboot.service.ICrudService;
import com.nnk.springboot.service.rule.IRuleService;

@WebMvcTest(RuleController.class)
public class RuleControllerTest extends AbstractCrudControllerTest<Rule, RuleDto, RuleCreateRequestDto, RuleUpdateRequestDto> {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private IRuleService ruleService;
    @MockitoBean private RuleMapper ruleMapper;

    private RuleDto ruleDto;

    @BeforeEach
    void setup() {
        ruleDto = new RuleDto(1,"Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
    }

    @Override protected String getEntityName() { return "rule"; }
    @Override protected ICrudService<Rule, RuleDto, RuleCreateRequestDto, RuleUpdateRequestDto> getService() { return ruleService; }
    @Override protected RuleDto getDto() { return ruleDto; }
    @Override protected MockMvc getMockMvc() { return mockMvc; }

    @Override
    protected ResultActions perfomCreate(String path) throws Exception {
        return mockMvc.perform(post(path)
                .with(csrf())
                .param("name", "Rule Name")
                .param("description", "Description")
                .param("json", "Json")
                .param("template", "Template")
                .param("sqlStr", "SQL")
                .param("sqlPart", "SQL Part"));
    }

    @Override
    protected ResultActions perfomUpdate(String path) throws Exception {
        return mockMvc.perform(post(path)
                .with(csrf())
                .param("name", "Rule Name updated")
                .param("description", "Description updated")
                .param("json", "Json updated")
                .param("template", "Template updated")
                .param("sqlStr", "SQL updated")
                .param("sqlPart", "SQL Part updated"));
    }
}
