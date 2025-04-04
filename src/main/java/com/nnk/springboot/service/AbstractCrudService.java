package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.EntityNotFoundException;

public abstract class AbstractCrudService<ENTITY, ENTITYDTO> {

    protected final JpaRepository<ENTITY, Integer> repository;

    protected AbstractCrudService(JpaRepository<ENTITY, Integer> repository) {
        this.repository = repository;
    }

    // fonction à implémenter dans la classe fille.
    protected abstract ENTITYDTO toDto(ENTITY entity);
    
    public List<ENTITYDTO> findAll() {
        List<ENTITY> entitys = repository.findAll();
        List<ENTITYDTO> entityDtos = new ArrayList<>();

        for (ENTITY entity : entitys) {
            entityDtos.add(toDto(entity));
        }

        return entityDtos;
    }

    public ENTITY getById(Integer id) {
        // exception car si l'id n'est pas trouvé, c'est une erreur grave.
        return repository.findById(id)
                         .orElseThrow(() -> new EntityNotFoundException("findById error : id " + id + " not found"));
    }
   
    public void deleteById(Integer id) {
        if (!repository.existsById(id)) {
            // exception car si l'id n'existe pas, c'est une erreur grave.
            throw new EntityNotFoundException("deleteById error : id " + id + " not found");
        }
        repository.deleteById(id);
    }

}
