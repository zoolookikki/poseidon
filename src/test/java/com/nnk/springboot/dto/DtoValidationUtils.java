package com.nnk.springboot.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DtoValidationUtils {

    /*
    DTO est un type générique qui ici peut être soit (Entity)CreateRequestDto ou (Entity)UpdateRequestDto.
    Il faut le déclarer avant le retour de fonction (ici void) pour indiquer que la méthode va utiliser ce type générique.
    */
    public static <DTO> void verifyEmptyDto(Validator validator, DTO dto, int expectedSize) {
        // when
        Set<ConstraintViolation<DTO>> constraintViolations = validator.validate(dto);

        // then
        assertEquals(expectedSize, constraintViolations.size());
    }

    public static <DTO> void verifyConstraintes(Validator validator, DTO dto, List<String> expectedFields, String title) {
        // when
        Set<ConstraintViolation<DTO>> constraintViolations = validator.validate(dto);

        // then
//        assertThat(constraintViolations).isNotEmpty();
        assertEquals(expectedFields.size(), constraintViolations.size());


        // on regarde si les contrôles ont bien été fait.
        for (ConstraintViolation<DTO> constraint : constraintViolations) {
            // on récupère le nom du champ sur lequel la contrainte a échoué.
            String field = constraint.getPropertyPath().toString();
            // on récupère le message définit dans l'annotation de validation.
            String message = constraint.getMessage();

            // il faut que le nom du champ correspondant à un de ceux là.
            assertThat(field).isIn(expectedFields);
            // une contrainte sans message, pas bien...
            assertThat(message).isNotBlank();

            // constraint.getInvalidValue() = valeur du champ concerné.                
            System.out.printf("%s : %s = '%s' : %s%n", title, field, constraint.getInvalidValue(), message);
        }
    }
}
