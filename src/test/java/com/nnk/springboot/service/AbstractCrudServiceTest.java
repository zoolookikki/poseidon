package com.nnk.springboot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.exception.EntityNotFoundException;
import com.nnk.springboot.mapper.IMapper;


/*
Permet à JUnit 5 d’activer le support de Mockito dans ce test.
Pour créer un mock avec @Mock.
Pour injecter automatiquement ces mocks avec @InjectMocks.
Ici Spring Boot ne gère pas les beans comme avec @SpringBootTest et @WebMvcTest => c'est un test unitaire "pur".
*/
@ExtendWith(MockitoExtension.class)
public abstract class AbstractCrudServiceTest<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> {

    // méthodes à implémenter dans les classes filles.
    
    // méthodes de base.
    protected abstract ENTITY getEntity();
    protected abstract List<ENTITY> getEntities();
    protected abstract List<DTO> getDtos();
    // ces 3 fonctions permettent à la classe abstraite de manipuler les bons objets métiers (service, repository, mapper) sans connaître leur type exact à l’avance.
    protected abstract AbstractCrudService<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> getService();
    protected abstract JpaRepository<ENTITY, Integer> getRepository();
    protected abstract IMapper<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> getMapper();
    // ces méthodes sont utilisées pour réaliser les tests : addOkTest et updateOkTest
    protected abstract CREATE_DTO getCreateDto();
    protected abstract DTO getExpectedCreatedDto();
    protected abstract UPDATE_DTO getUpdateDto();
    protected abstract ENTITY getUpdatedEntity();
    protected abstract DTO getExpectedUpdatedDto();    
    
    @Test
    @DisplayName("recherche de la liste de tous les entités avec succès")
    public void findAllOkTest() {
        // given
        List<ENTITY> entities = getEntities();
        List<DTO> dtos = getDtos();

        when(getRepository().findAll()).thenReturn(entities);
        for (int i = 0; i < entities.size(); i++) {
            when(getMapper().toDto(entities.get(i))).thenReturn(dtos.get(i));
        }

        // when
        List<DTO> result = getService().findAll();

        // then
        assertThat(result).hasSize(dtos.size()).containsExactlyElementsOf(dtos);
        verify(getRepository(), times(1)).findAll();
    }

    @Test
    @DisplayName("recherche de la liste de toutes les entités en échec")
    public void findAllFailTest() {
        // given
        when(getRepository().findAll()).thenReturn(List.of());

        // when
        List<DTO> result = getService().findAll();

        // then
        assertThat(result).isEmpty();
        verify(getRepository(), times(1)).findAll();
    }

    @Test
    @DisplayName("recherche d'une entité par son id avec succèse")
    public void findByIdSuccessTest() {
        // given
        ENTITY entity = getEntity();
        when(getRepository().findById(1)).thenReturn(Optional.of(entity));

        // when
        ENTITY result = getService().getById(1);

        // then
        assertThat(result).isEqualTo(entity);
        verify(getRepository(), times(1)).findById(1);
    }
    
    @Test
    @DisplayName("recherche d'une entité par son id en échec")
    public void findByIdFailTest() {
        // given
        when(getRepository().findById(1)).thenReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> getService().getById(1))
                .isInstanceOf(EntityNotFoundException.class);
        verify(getRepository(), times(1)).findById(1);
    }

    @Test
    @DisplayName("suppression d'une entité avec succès")
    public void deleteOkTest() {
        // Given
        when(getRepository().existsById(1)).thenReturn(true);

        // When
        getService().deleteById(1);

        // Then
        verify(getRepository(), times(1)).existsById(1);
        verify(getRepository(), times(1)).deleteById(1);
    }

    @Test
    @DisplayName("supression d'une entité en échec")
    public void deleteFailTest() {
        when(getRepository().existsById(1)).thenReturn(false);

        assertThatThrownBy(() -> getService().deleteById(1))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessageContaining("not found");

        verify(getRepository()).existsById(1);
        verify(getRepository(), never()).deleteById(1);
    }

    @Test
    @DisplayName("ajout d'une entité avec succès")
    public void addOkTest() {
        // given
        CREATE_DTO createDto = getCreateDto();
        ENTITY entity = getEntity();
        DTO expectedDto = getExpectedCreatedDto();

        when(getMapper().fromCreateRequestDto(createDto)).thenReturn(entity);
        when(getRepository().save(entity)).thenReturn(entity);
        when(getMapper().toDto(entity)).thenReturn(expectedDto);

        // when
        DTO result = getService().add(createDto);

        // then
        assertThat(result).isEqualTo(expectedDto);
        verify(getRepository()).save(entity);
    }    

    @Test
    @DisplayName("modification d'une entité avec succès")
    public void updateOkTest() {
        // given
        ENTITY entity = getEntity();
        ENTITY updatedEntity = getUpdatedEntity();
        UPDATE_DTO updateDto = getUpdateDto();
        DTO expectedDto = getExpectedUpdatedDto();

        when(getRepository().findById(1)).thenReturn(Optional.of(entity));
        when(getRepository().save(entity)).thenReturn(updatedEntity);
        when(getMapper().toDto(updatedEntity)).thenReturn(expectedDto);

        // when
        DTO result = getService().update(1, updateDto);

        // then
        assertThat(result).isEqualTo(expectedDto);
        verify(getRepository(), times(1)).findById(1);
        verify(getRepository(), times(1)).save(entity);
    }    
}
