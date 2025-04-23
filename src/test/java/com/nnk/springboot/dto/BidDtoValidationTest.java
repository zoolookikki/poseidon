package com.nnk.springboot.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.dto.bid.BidCreateRequestDto;
import com.nnk.springboot.dto.bid.BidUpdateRequestDto;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BidDtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("Test de BidCreateRequestDto")
    class BidCreateDtoTests {

        @Test
        @DisplayName("Création valide")
        public void createValid() {
            // given
            BidCreateRequestDto dto = new BidCreateRequestDto();
            dto.setAccount("Test Account");
            dto.setType("Test Type");
            dto.setBidQuantity(10.0);

            // when
            Set<ConstraintViolation<BidCreateRequestDto>> constraintViolations = validator.validate(dto);
            
            // then
            assertThat(constraintViolations).isEmpty();
        }

        @Test
        @DisplayName("Création invalide (vide)")
        public void createDtoAllFieldsEmptyFailTest() {
            // given when then  
            DtoValidationUtils.verifyEmptyDto(validator, new BidCreateRequestDto(), 3);
        }

        @Test
        @DisplayName("Création invalide (valeur trop petite)")
        void createQuantityTooSmallFail() {
            // given
            BidCreateRequestDto dto = new BidCreateRequestDto();
            dto.setAccount("Test Account");
            dto.setType("Test Type");
            dto.setBidQuantity(0.0);

            // when then
            DtoValidationUtils.verifyConstraintes(validator, dto, List.of("bidQuantity"), "Création - bidQuantity trop petit");
        }

        @Test
        @DisplayName("Création invalide (champs trop longs)")
        void createDtoLongFieldsFailTest() {
            // given
            String bigString = "x".repeat(40);
            BidCreateRequestDto dto = new BidCreateRequestDto();
            dto.setAccount(bigString);
            dto.setType(bigString);
            dto.setBidQuantity(10.0);

            // when then
            DtoValidationUtils.verifyConstraintes(validator, dto, List.of("account", "type"), "Création - champs trop longs");
        }
    }

    @Nested
    @DisplayName("Tests de BidUpdateRequestDto")
    class BidUpdateDtoTests {

        @Test
        @DisplayName("Mise à jour valide")
        public void updateDtoValidTest() {
            // given
            BidUpdateRequestDto dto = new BidUpdateRequestDto();
            dto.setId(1);
            dto.setAccount("Updated Account");
            dto.setType("Updated Type");
            dto.setBidQuantity(20.0);

            // when
            Set<ConstraintViolation<BidUpdateRequestDto>> constraintViolations = validator.validate(dto);
            
            // then
            assertThat(constraintViolations).isEmpty();
        }

        @Test
        @DisplayName("Mise à jour invalide (vide)")
        public void updateDtoAllFieldsEmptyFailTest() {
            // given when then
            DtoValidationUtils.verifyEmptyDto(validator, new BidUpdateRequestDto(), 3);
        }

        @Test
        @DisplayName("Mise à jour invalide (Valeur trop petite)")
        void updateQuantityTooSmallFail() {
            // given
            BidUpdateRequestDto dto = new BidUpdateRequestDto();
            dto.setId(1);
            dto.setAccount("Updated Account");
            dto.setType("Updated Type");
            dto.setBidQuantity(0.0);

            // when then
            DtoValidationUtils.verifyConstraintes(validator, dto, List.of("bidQuantity"), "Update - bidQuantity trop petit");
        }

        @Test
        @DisplayName("Mise à jour invalide (champs trop longs)")
        public void updateDtoLongFieldsFailTest() {
            // given
            String bigString = "y".repeat(40);
            BidUpdateRequestDto dto = new BidUpdateRequestDto();
            dto.setId(1);
            dto.setAccount(bigString);
            dto.setType(bigString);
            dto.setBidQuantity(50.0);

            // when then
            DtoValidationUtils.verifyConstraintes(validator, dto, List.of("account", "type"), "Update - champs trop longs");
        }
    }
}
