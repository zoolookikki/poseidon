package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.mapper.IMapper;
import com.nnk.springboot.exception.EntityNotFoundException;

public abstract class AbstractCrudService<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> {

    protected final JpaRepository<ENTITY, Integer> repository;
    protected final IMapper<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> mapper;

    protected AbstractCrudService(
            JpaRepository<ENTITY, Integer> repository, 
            IMapper<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<DTO> findAll() {
        List<ENTITY> entitys = repository.findAll();
        List<DTO> dtos = new ArrayList<>();

        for (ENTITY entity : entitys) {
            dtos.add(mapper.toDto(entity));
        }

        return dtos;
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


    public DTO add(CREATE_DTO dto) {
        ENTITY entity = mapper.fromCreateRequestDto(dto);
        return mapper.toDto(repository.save(entity));
    }

    public DTO update(Integer id, UPDATE_DTO dto) {
        ENTITY entity = getById(id);
        
        mapper.updateEntityFromDto(entity, dto);
        
        return mapper.toDto(repository.save(entity));
    }
    
}
