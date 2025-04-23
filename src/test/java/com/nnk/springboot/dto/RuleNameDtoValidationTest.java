package com.nnk.springboot.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.dto.rule.RuleCreateRequestDto;
import com.nnk.springboot.dto.rule.RuleUpdateRequestDto;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RuleNameDtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("Test de RuleNameCreateRequestDto")
    class RuleNameCreateDtoTests {

        @Test
        @DisplayName("Création valide")
        public void createValid() {
            // given
            RuleCreateRequestDto dto = new RuleCreateRequestDto();
            dto.setName("Rule name");
            dto.setDescription("Description");
            dto.setJson("Json");
            dto.setTemplate("Template");
            dto.setSqlStr("SQL");
            dto.setSqlPart("SQL Part");

            // when
            Set<ConstraintViolation<RuleCreateRequestDto>> constraintViolations = validator.validate(dto);

            // then
            assertThat(constraintViolations).isEmpty();
        }

        @Test
        @DisplayName("Création invalide (vide)")
        public void createDtoAllFieldsEmptyFailTest() {
            // given when then
            // uniquement 2 sont obligatoires.
            DtoValidationUtils.verifyEmptyDto(validator, new RuleCreateRequestDto(), 2); 
        }

        @Test
        @DisplayName("Création invalide (champs trop longs)")
        void createDtoLongFieldsFailTest() {
            // given
            String bigString125 = "x".repeat(130);
            String bigString512 = "x".repeat(520);

            RuleCreateRequestDto dto = new RuleCreateRequestDto();
            dto.setName(bigString125);
            dto.setDescription(bigString125);
            dto.setJson(bigString125);
            dto.setTemplate(bigString512);
            dto.setSqlStr(bigString125);
            dto.setSqlPart(bigString125);

            // when then
            DtoValidationUtils.verifyConstraintes(validator,
                    dto,
                    List.of("name", "description", "json", "template", "sqlStr", "sqlPart"),
                    "Création - champs trop longs");
        }
    }
    
    @Nested
    @DisplayName("Tests de RuleNameUpdateRequestDto")
    class RuleNameUpdateDtoTests {

        @Test
        @DisplayName("Mise à jour valide")
        public void updateDtoValidTest() {
            // given
            RuleUpdateRequestDto dto = new RuleUpdateRequestDto();
            dto.setId(1);
            dto.setName("Updated Rule name");
            dto.setDescription("Updated Description");
            dto.setJson("Updated Json");
            dto.setTemplate("Updated Template");
            dto.setSqlStr("Updated SQL");
            dto.setSqlPart("Updated SQL Part");

            // when
            Set<ConstraintViolation<RuleUpdateRequestDto>> constraintViolations = validator.validate(dto);

            // then
            assertThat(constraintViolations).isEmpty();
        }

        @Test
        @DisplayName("Mise à jour invalide (vide)")
        public void updateDtoAllFieldsEmptyFailTest() {
            // given when then
            DtoValidationUtils.verifyEmptyDto(validator, new RuleUpdateRequestDto(), 2);
        }

        @Test
        @DisplayName("Mise à jour invalide (champs trop longs)")
        public void updateDtoLongFieldsFailTest() {
            // given
            String bigString125 = "y".repeat(130);
            String bigString512 = "y".repeat(520);

            RuleUpdateRequestDto dto = new RuleUpdateRequestDto();
            dto.setId(1);
            dto.setName(bigString125);
            dto.setDescription(bigString125);
            dto.setJson(bigString125);
            dto.setTemplate(bigString512);
            dto.setSqlStr(bigString125);
            dto.setSqlPart(bigString125);

            // when then
            DtoValidationUtils.verifyConstraintes(validator, 
                    dto,
                    List.of("name", "description", "json", "template", "sqlStr", "sqlPart"),
                    "Update - champs trop longs");
        }
    }
}
