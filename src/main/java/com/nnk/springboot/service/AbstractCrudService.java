package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.mapper.IMapper;

import lombok.extern.log4j.Log4j2;

import com.nnk.springboot.exception.EntityNotFoundException;

/**
 * Generic abstract service centralizing CRUD operations on an entity.
 *
 * <p>
 * This class provides a common implementation for:
 * <ul>
 *     <li>Retrieving all records</li>
 *     <li>Search by ID</li>
 *     <li>Deletion by identifier</li>
 *     <li>Adding and updating from DTOs</li>
 * </ul>
 * </p>
 *
 * <p>
 * The service is based on two components :
 * <ul>
 *     <li>JpaRepository : for persistence operations</li>
 *     <li>IMapper : to convert entities to/from DTOs</li>
 * </ul>
 * </p>
 *
 * @param <ENTITY>       the type of the persisted entity
 * @param <DTO>          the type of DTO exposed to the presentation layer
 * @param <CREATE_DTO>   the type of DTO used during creation
 * @param <UPDATE_DTO>   the type of DTO used during the update
 */
@Log4j2
public abstract class AbstractCrudService<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> {

    protected final JpaRepository<ENTITY, Integer> repository;
    protected final IMapper<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> mapper;

    protected AbstractCrudService(
            JpaRepository<ENTITY, Integer> repository, 
            IMapper<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Retrieves and returns all records as DTOs.
     *
     * @return list of DTOs representing entities
     */
    public List<DTO> findAll() {
        List<ENTITY> entitys = repository.findAll();
        log.debug("findAll,entitys.size={}", entitys.size());
        List<DTO> dtos = new ArrayList<>();

        for (ENTITY entity : entitys) {
            dtos.add(mapper.toDto(entity));
        }

        return dtos;
    }

    /**
     * Search for an entity by its ID.
     *
     * @param id identifier of the entity sought
     * @return the corresponding entity
     * @throws EntityNotFoundException if no entity is found with this identifier
     */
    public ENTITY getById(Integer id) {
        log.debug("getById,id={}", id);
        // exception car si l'id n'est pas trouvé, c'est une erreur grave.
        return repository.findById(id)
                         .orElseThrow(() -> {
                             log.debug("getById failed : id {} not found", id);
                             return new EntityNotFoundException("findById error : id " + id + " not found");                             
                         });
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id identifier of the entity to be deleted
     * @throws EntityNotFoundException if the identifier does not exist in the database
     */
    public void deleteById(Integer id) {
        if (!repository.existsById(id)) {
            log.debug("deleteById failed : id {} not found", id);
            // exception car si l'id n'existe pas, c'est une erreur grave.
            throw new EntityNotFoundException("deleteById error : id " + id + " not found");
        }
        
        repository.deleteById(id);
        log.info("Entity with id {} deleted successfully", id);
    }


    /**
     * Creates a new entity from a creation DTO.
     *
     * @param dto the data necessary for the creation of the entity
     * @return the DTO representing the created entity
     */
    public DTO add(CREATE_DTO createDto) {
        log.debug("add,createDTO={}", createDto);
        
        ENTITY entity = mapper.fromCreateRequestDto(createDto);
        DTO dto = mapper.toDto(repository.save(entity));
        
        log.info("Entity created successfully={}", dto);
        return dto;
    }

    /**
     * Updates an existing entity from an update DTO.
     *
     * @param id identifier of the entity to be updated
     * @param dto DTO containing the new values
     * @return the DTO representing the updated entity
     */
    public DTO update(Integer id, UPDATE_DTO updateDto) {
        log.debug("update,id={},updateDTO={}", id, updateDto);
        
        ENTITY entity = getById(id);
        
        // mise à jour de l'entité à partir de la dto.
        mapper.updateEntityFromDto(entity, updateDto);
        ENTITY updated = repository.save(entity);
        DTO dto = mapper.toDto(updated);
        
        log.info("Entity updated successfully={}", dto);
        return dto; 
    }
}
