package com.nnk.springboot.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;


@SpringBootTest
/* 
Base de données en mémoire pendant les tests (H2)
Spring Boot génère automatiquement le schéma à partir de ce qui est déclarés dans les entités, en utilisant Hibernate.
*/
@AutoConfigureTestDatabase
public abstract class AbstractRepositoryIT<ENTITY, ID> {

    protected ENTITY initializedEntity;

    protected abstract ENTITY createEntity();
    protected abstract JpaRepository<ENTITY, ID> getRepository();
    protected abstract ID getId(ENTITY entity);
    protected abstract void verifyCreate(ENTITY entity);
    protected abstract void updateEntity(ENTITY entity);
    protected abstract void verifyUpdate(ENTITY entity);

    /**
     * Pour que la classe fille selon les cas fournisse d'autres entités à enregistrer pour enrichir le test findAll.
     * Par défaut, retourne une liste vide.
     */
    protected List<ENTITY> getMoreEntities() {
        return List.of(); 
    }

    
    @BeforeEach
    void setup() {
        // pour nettoyer la base à chaque fois.
        getRepository().deleteAll();
        ENTITY entity = createEntity();
        initializedEntity = getRepository().save(entity);
    }
    
    @Test
    void createTest() {
        // then
        assertThat(getId(initializedEntity)).isNotNull();
        verifyCreate(initializedEntity);
    }

    @Test
    void readTest() {
        // given when
        Optional<ENTITY> entity = getRepository().findById(getId(initializedEntity));
        
        // then
        assertThat(entity).isPresent();
        verifyCreate(entity.get());
    }

    @Test
    void updateTest() {
        // given
        /*
        findById(...) retourne un Optional<Bid>.
        Si la valeur est présente, elle est récupérée.
        Si l’Optional est vide (l’entité n’existe pas), une AssertionError est levée => exception standard de Java utilisé pour les tests.         
        */
        ENTITY entity = getRepository().findById(getId(initializedEntity))
                .orElseThrow(() -> new AssertionError("Not found"));
        updateEntity(entity);
        
        // when
        ENTITY updatedEntity = getRepository().save(entity);
        
        // then
        verifyUpdate(updatedEntity);
    }

    @Test
    void findAllTest() {
        // given
        // ajout d'entités supplémentaires si founies.
        List<ENTITY> addedEntities = getMoreEntities();
        for (ENTITY entity : addedEntities) {
            getRepository().save(entity);
        }
        
        // when
        List<ENTITY> entities = getRepository().findAll();
        System.out.println("Nombre d'entités dans findAllTest : " + entities.size());
        
        // then
        assertThat(entities).hasSize(addedEntities.size() + 1);
        boolean found = false;
        for (ENTITY entity : entities) {
            if (getId(entity).equals(getId(initializedEntity))) {
                found = true;
                break;
            }
        }
        assertThat(found).isTrue();
    }

    @Test
    void deleteTest() {
        // given
        ENTITY entity = getRepository().findById(getId(initializedEntity))
                .orElseThrow(() -> new AssertionError("Not found"));

        // when
        getRepository().delete(entity);
        
        // then
        assertThat(getRepository().findById(getId(initializedEntity))).isNotPresent();
    }
}
