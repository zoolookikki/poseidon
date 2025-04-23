package com.nnk.springboot.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.dto.curvePoint.CurvePointCreateRequestDto;
import com.nnk.springboot.dto.curvePoint.CurvePointUpdateRequestDto;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CurvePointDtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("Test de CurvePointCreateRequestDto")
    class CurvePointCreateDtoTests {

        @Test
        @DisplayName("Création valide")
        public void createValid() {
            // given
            CurvePointCreateRequestDto dto = new CurvePointCreateRequestDto();
            dto.setCurveId(10);
            dto.setTerm(1.0);
            dto.setValuePoint(25.0);

            // when
            Set<ConstraintViolation<CurvePointCreateRequestDto>> constraintViolations = validator.validate(dto);
            
            // then
            assertThat(constraintViolations).isEmpty();
        }

        @Test
        @DisplayName("Création invalide (vide)")
        public void createDtoAllFieldsEmptyFailTest() {
            // given when then  
            DtoValidationUtils.verifyEmptyDto(validator, new CurvePointCreateRequestDto(), 3);
        }

        @Test
        @DisplayName("Création invalide (valeurs trop petites)")
        void createInvalidValuesFail() {
            // given
            CurvePointCreateRequestDto dto = new CurvePointCreateRequestDto();
            dto.setCurveId(-1);
            dto.setTerm(0.0);
            dto.setValuePoint(0.0);

            // when then
            DtoValidationUtils.verifyConstraintes(validator, dto, List.of("curveId", "term", "valuePoint"), "Création - valeurs invalides");
        }

        @Test
        @DisplayName("Création invalide (curveId trop grand)")
        void createCurveIdTooBigFail() {
            // given
            CurvePointCreateRequestDto dto = new CurvePointCreateRequestDto();
            dto.setCurveId(999);
            dto.setTerm(1.0);
            dto.setValuePoint(1.0);

            // when then
            DtoValidationUtils.verifyConstraintes(validator, dto, List.of("curveId"), "Création - curveId trop grand");
        }
    }

    @Nested
    @DisplayName("Tests de CurvePointUpdateRequestDto")
    class CurvePointUpdateDtoTests {

        @Test
        @DisplayName("Mise à jour valide")
        public void updateDtoValidTest() {
            // given
            CurvePointUpdateRequestDto dto = new CurvePointUpdateRequestDto();
            dto.setId(1);
            dto.setCurveId(10);
            dto.setTerm(2.0);
            dto.setValuePoint(30.0);

            // when
            Set<ConstraintViolation<CurvePointUpdateRequestDto>> constraintViolations = validator.validate(dto);
            
            // then
            assertThat(constraintViolations).isEmpty();
        }

        @Test
        @DisplayName("Mise à jour invalide (vide)")
        public void updateDtoAllFieldsEmptyFailTest() {
            // given when then
            DtoValidationUtils.verifyEmptyDto(validator, new CurvePointUpdateRequestDto(), 3);
        }

        @Test
        @DisplayName("Mise à jour invalide (valeurs trop petites)")
        void updateInvalidValuesFail() {
            // given
            CurvePointUpdateRequestDto dto = new CurvePointUpdateRequestDto();
            dto.setId(1);
            dto.setCurveId(0);
            dto.setTerm(0.0);
            dto.setValuePoint(0.0);

            // when then
            DtoValidationUtils.verifyConstraintes(validator, dto,  List.of("curveId", "term", "valuePoint"), "Update - valeurs invalides");
        }

        @Test
        @DisplayName("Mise à jour invalide (curveId trop grand)")
        public void updateCurveIdTooBigFail() {
            // given
            CurvePointUpdateRequestDto dto = new CurvePointUpdateRequestDto();
            dto.setId(1);
            dto.setCurveId(999);
            dto.setTerm(1.0);
            dto.setValuePoint(1.0);

            // when then
            DtoValidationUtils.verifyConstraintes(validator, dto, List.of("curveId"), "Update - curveId trop grand");
        }
    }
}
