package com.nnk.springboot.dto;

import com.nnk.springboot.dto.user.UserCreateRequestDto;
import com.nnk.springboot.dto.user.UserUpdateRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

// référence : https://www.jmdoudoux.fr/java/dej/chap-validation_donnees.htm
public class UserDtoValidationTest {

    // validateur de contraintes
    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        // pour récupérer une instance du validateur de contraintes.
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    private void verifyEmptyDto(Object dto, int size) {
        // when
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(dto);

        // then
        assertEquals(size, constraintViolations.size()); 
    }
    
    private <DTO> void verifyOthersConstraintes(DTO dto, String title) {
        // when
        Set<ConstraintViolation<DTO>> constraintViolations = validator.validate(dto);

        // then
        assertThat(constraintViolations).isNotEmpty();
    
        // on regarde si les contrôles ont bien été fait.
        for (ConstraintViolation<DTO> constraint : constraintViolations) {
        
            // on récupère le nom du champ sur lequel la contrainte a échoué.
            String field = constraint.getPropertyPath().toString();
            // on récupère le message définit dans l'annotation de validation.
            String message = constraint.getMessage();
    
            // il faut que le nom du champ correspondant à un de ceux là.
            assertThat(field).isIn("username", "fullname", "role", "password");
            // une contrainte sans message...
            assertThat(message).isNotBlank();
    
            // constraint.getInvalidValue() = valeur du champ concerné.                
            System.out.printf("%s : %s = '%s' : %s%n", title, field, constraint.getInvalidValue(), message);
        }
    }
    
    private <DTO> void verifLongFields(DTO dto, String title) {
        // when
        Set<ConstraintViolation<DTO>> constraintViolations = validator.validate(dto);
    
        // then
        assertThat(constraintViolations).isNotEmpty();

        for (ConstraintViolation<DTO> constraint : constraintViolations) {
        
            String field = constraint.getPropertyPath().toString();
            String message = constraint.getMessage();
    
            assertThat(field).isIn("username", "fullname", "password");
            if (field.equals("username") || field.equals("fullname") || field.equals("password")) {
                assertThat(message).contains("max.length");
            }
    
            System.out.printf("%s : %s = '%s' : %s%n", title, field,  constraint.getInvalidValue(), message);
        }
    }
    
    @Nested
    @DisplayName("Tests de UserCreateRequestDto")
    class UserCreateRequestDtoTests {

        @Test
        @DisplayName("Création valide")
        public void createDtoValidTest() {
            // given
            UserCreateRequestDto dto = new UserCreateRequestDto();
            dto.setUsername("John");
            dto.setFullname("Full John");
            dto.setPassword("John@678");
            dto.setRole("USER");

            // when
            // retourne une collection de violations de règles.
            Set<ConstraintViolation<UserCreateRequestDto>> constraintViolations = validator.validate(dto);
            
            // then
            assertThat(constraintViolations).isEmpty();
        }

        @Test
        @DisplayName("Création invalide (vide)")
        public void createDtoAllFieldsEmptyFailTest() {
            // given when then  
            // les 4 obligatoires sont : username,  fullname, password, role.
            verifyEmptyDto(new UserCreateRequestDto(),4);
        }

        @Test
        @DisplayName("Création invalide (autres contraintes)")
        public void createDtoOthersConstraintesFailTest() {
            // given
            UserCreateRequestDto dto = new UserCreateRequestDto();
            // @ interdit
            dto.setUsername("John@");
            // + interdit
            dto.setFullname("Full John+");
            // trop faible.
            dto.setPassword("John");
            // ADMIN ou USER
            dto.setRole("XXX");

            // when then
            verifyOthersConstraintes(dto, "Création invalide (autres contraintes)");
        }

        @Test
        @DisplayName("Création invalide (champs trop longs)")
        void createDtoLongFieldsFailTest() {
            // given
            String bigString = "x".repeat(126);
            UserCreateRequestDto dto = new UserCreateRequestDto();
            dto.setUsername(bigString);
            dto.setFullname(bigString);
            // pour ne pas tomber sur la contrainte du mot de passe trop faible.
            dto.setPassword(bigString+"X@1");
            dto.setRole("ADMIN");
        
            // when then
            verifLongFields(dto, "Création invalide (champs trop longs)");
        }
        
    }

    @Nested
    @DisplayName("Tests de UserUpdateRequestDto")
    class UserUpdateRequestDtoTests {

        @Test
        @DisplayName("Mise à jour valide")
        public void updateDtoValidTest() {
            // given
            UserUpdateRequestDto dto = new UserUpdateRequestDto();
            dto.setId(1);
            dto.setUsername("Johnny");
            dto.setFullname("Full Johnny");
            dto.setPassword("Johnny@8");
            dto.setRole("ADMIN");
        
            // when
            Set<ConstraintViolation<UserUpdateRequestDto>> constraintViolations = validator.validate(dto);
            
            // then
            assertThat(constraintViolations).isEmpty();
        }

       @Test
        @DisplayName("Mie à jour invalide (vide)")
        public void updateDtoAllFieldsEmptyFailTest() {
            // given when then
            // les 3 obligatoires sont : username,  fullname, role.
            verifyEmptyDto(new UserUpdateRequestDto(),3);
        }

        @Test
        @DisplayName("Mise à jour invalide (autres contraintes)")
        public void updateDtoOthersConstraintesFailTest() {
            // given
            UserUpdateRequestDto dto = new UserUpdateRequestDto();
            dto.setId(1);
            dto.setUsername("Johnny@");
            dto.setFullname("Full Johnny+");
            dto.setPassword("Johnny");
            dto.setRole("XXX");
            
            // when then
            verifyOthersConstraintes(dto, "Mise à jour invalide (autres contraintes)");
        }
        
        @Test
        @DisplayName("Mise à jour valide (mot de passe vide)")
        public void updateDtoEmptyPasswordValidTest() {
            // given
            UserUpdateRequestDto dto = new UserUpdateRequestDto();
            dto.setId(1);
            dto.setUsername("Johnny");
            dto.setFullname("Full Johnny");
            // vide => ok ici
            dto.setPassword("");
            dto.setRole("ADMIN");
        
            Set<ConstraintViolation<UserUpdateRequestDto>> constraintViolations = validator.validate(dto);
            assertThat(constraintViolations).isEmpty();
        }

        @Test
        @DisplayName("Mise à jour invalide (champs trop longs)")
        public void updateDtoLongFieldsFailTest() {
            // given
            String bigString = "y".repeat(126);
            UserUpdateRequestDto dto = new UserUpdateRequestDto();
            dto.setId(10);
            dto.setUsername(bigString);
            dto.setFullname(bigString);
            dto.setPassword(bigString+"X@1");
            dto.setRole("ADMIN"); 
        
            // when then
            verifLongFields(dto, "Mise à jour invalide (champs trop longs)");
        }
        
    }
    
}
