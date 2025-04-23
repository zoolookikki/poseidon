package com.nnk.springboot.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.dto.rating.RatingCreateRequestDto;
import com.nnk.springboot.dto.rating.RatingUpdateRequestDto;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RatingDtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("Test de RatingCreateRequestDto")
    class RatingCreateDtoTests {

        @Test
        @DisplayName("Création valide")
        public void createValid() {
            // given
            RatingCreateRequestDto dto = new RatingCreateRequestDto();
            dto.setMoodysRating("Moodys Rating");
            dto.setSandPRating("Sand PRating");
            dto.setFitchRating("Fitch Rating");
            dto.setOrderNumber(10);

            // when
            Set<ConstraintViolation<RatingCreateRequestDto>> constraintViolations = validator.validate(dto);

            // then
            assertThat(constraintViolations).isEmpty();
        }

        @Test
        @DisplayName("Création invalide (vide)")
        public void createDtoAllFieldsEmptyFailTest() {
            // given when then
            DtoValidationUtils.verifyEmptyDto(validator, new RatingCreateRequestDto(), 4);
        }

        @Test
        @DisplayName("Création invalide (valeurs incorrectes)")
        void createInvalidValuesFail() {
            // given
            RatingCreateRequestDto dto = new RatingCreateRequestDto();
            dto.setMoodysRating(""); // NotBlank
            dto.setSandPRating("");  // NotBlank
            dto.setFitchRating("");  // NotBlank
            dto.setOrderNumber(0);   // Positive

            // when then
            DtoValidationUtils.verifyConstraintes(validator, 
                    dto, 
                    List.of("moodysRating", "sandPRating", "fitchRating", "orderNumber"),
                    "Création - valeurs invalides");
        }

        @Test
        @DisplayName("Création invalide (champs trop longs)")
        void createDtoLongFieldsFailTest() {
            // given
            String bigString = "x".repeat(130);
            RatingCreateRequestDto dto = new RatingCreateRequestDto();
            dto.setMoodysRating(bigString);
            dto.setSandPRating(bigString);
            dto.setFitchRating(bigString);
            dto.setOrderNumber(50);

            // when then
            DtoValidationUtils.verifyConstraintes(validator,
                    dto, 
                    List.of("moodysRating", "sandPRating", "fitchRating"),
                    "Création - champs trop longs");
        }
    }

    @Nested
    @DisplayName("Tests de RatingUpdateRequestDto")
    class RatingUpdateDtoTests {

        @Test
        @DisplayName("Mise à jour valide")
        public void updateDtoValidTest() {
            // given
            RatingUpdateRequestDto dto = new RatingUpdateRequestDto();
            dto.setId(1);
            dto.setMoodysRating("Moodys Rating updated");
            dto.setSandPRating("Sand PRating updated");
            dto.setFitchRating("Fitch Rating updated");
            dto.setOrderNumber(1);

            // when
            Set<ConstraintViolation<RatingUpdateRequestDto>> constraintViolations = validator.validate(dto);

            // then
            assertThat(constraintViolations).isEmpty();
        }

        @Test
        @DisplayName("Mise à jour invalide (vide)")
        public void updateDtoAllFieldsEmptyFailTest() {
            // given when then
            DtoValidationUtils.verifyEmptyDto(validator, new RatingUpdateRequestDto(), 4);
        }

        @Test
        @DisplayName("Mise à jour invalide (valeurs incorrectes)")
        void updateInvalidValuesFail() {
            // given
            RatingUpdateRequestDto dto = new RatingUpdateRequestDto();
            dto.setId(1);
            dto.setMoodysRating("");
            dto.setSandPRating("");
            dto.setFitchRating("");
            dto.setOrderNumber(0);

            // when then
            DtoValidationUtils.verifyConstraintes(validator, 
                    dto,
                    List.of("moodysRating", "sandPRating", "fitchRating", "orderNumber"),
                    "Update - valeurs invalides");
        }

        @Test
        @DisplayName("Mise à jour invalide (champs trop longs)")
        public void updateDtoLongFieldsFailTest() {
            // given
            String bigString = "y".repeat(130);
            RatingUpdateRequestDto dto = new RatingUpdateRequestDto();
            dto.setId(1);
            dto.setMoodysRating(bigString);
            dto.setSandPRating(bigString);
            dto.setFitchRating(bigString);
            dto.setOrderNumber(50);

            // when then
            DtoValidationUtils.verifyConstraintes(validator, 
                    dto,
                    List.of("moodysRating", "sandPRating", "fitchRating"),
                    "Update - champs trop longs");
        }
    }
}
