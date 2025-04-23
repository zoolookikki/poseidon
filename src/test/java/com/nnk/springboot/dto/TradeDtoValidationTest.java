package com.nnk.springboot.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.dto.trade.TradeCreateRequestDto;
import com.nnk.springboot.dto.trade.TradeUpdateRequestDto;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TradeDtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("Test de TradeCreateRequestDto")
    class TradeCreateDtoTests {

        @Test
        @DisplayName("Création valide")
        public void createValid() {
            // given
            TradeCreateRequestDto dto = new TradeCreateRequestDto();
            dto.setAccount("Trade Account");
            dto.setType("Type");
            dto.setBuyQuantity(0.01d);

            // when
            Set<ConstraintViolation<TradeCreateRequestDto>> constraintViolations = validator.validate(dto);

            // then
            assertThat(constraintViolations).isEmpty();
        }

        @Test
        @DisplayName("Création invalide (vide)")
        public void createDtoAllFieldsEmptyFailTest() {
            // given when then
            DtoValidationUtils.verifyEmptyDto(validator, new TradeCreateRequestDto(), 3);
        }

        @Test
        @DisplayName("Création invalide (valeur trop petite)")
        void createQuantityTooSmallFail() {
            // given
            TradeCreateRequestDto dto = new TradeCreateRequestDto();
            dto.setAccount("Trade Account");
            dto.setType("Type");
            dto.setBuyQuantity(0.0);

            // when then
            DtoValidationUtils.verifyConstraintes(validator, dto, List.of("buyQuantity"), "Création - buyQuantity trop petit");
        }

        @Test
        @DisplayName("Création invalide (champs trop longs)")
        void createDtoLongFieldsFailTest() {
            // given
            String bigString = "x".repeat(40);
            TradeCreateRequestDto dto = new TradeCreateRequestDto();
            dto.setAccount(bigString);
            dto.setType(bigString);
            dto.setBuyQuantity(1000000.0);

            // when then
            DtoValidationUtils.verifyConstraintes(validator, dto, List.of("account", "type"), "Création - champs trop longs");
        }
    }

    @Nested
    @DisplayName("Tests de TradeUpdateRequestDto")
    class TradeUpdateDtoTests {

        @Test
        @DisplayName("Mise à jour valide")
        public void updateDtoValidTest() {
            // given
            TradeUpdateRequestDto dto = new TradeUpdateRequestDto();
            dto.setId(1);
            dto.setAccount("Updated Trade Account");
            dto.setType("Updated Type");
            dto.setBuyQuantity(3.21);

            // when
            Set<ConstraintViolation<TradeUpdateRequestDto>> constraintViolations = validator.validate(dto);

            // then
            assertThat(constraintViolations).isEmpty();
        }

        @Test
        @DisplayName("Mise à jour invalide (vide)")
        public void updateDtoAllFieldsEmptyFailTest() {
            // given when then
            DtoValidationUtils.verifyEmptyDto(validator, new TradeUpdateRequestDto(), 3);
        }

        @Test
        @DisplayName("Mise à jour invalide (valeur trop petite)")
        void updateQuantityTooSmallFail() {
            // given
            TradeUpdateRequestDto dto = new TradeUpdateRequestDto();
            dto.setId(1);
            dto.setAccount("Trade Account");
            dto.setType("Type");
            dto.setBuyQuantity(0.0);

            // when then
            DtoValidationUtils.verifyConstraintes(validator, dto, List.of("buyQuantity"), "Update - buyQuantity trop petit");
        }

        @Test
        @DisplayName("Mise à jour invalide (champs trop longs)")
        public void updateDtoLongFieldsFailTest() {
            // given
            String bigString = "y".repeat(40);
            TradeUpdateRequestDto dto = new TradeUpdateRequestDto();
            dto.setId(1);
            dto.setAccount(bigString);
            dto.setType(bigString);
            dto.setBuyQuantity(50.0);

            // when then
            DtoValidationUtils.verifyConstraintes(validator, dto, List.of("account", "type"), "Update - champs trop longs");
        }
    }
}
