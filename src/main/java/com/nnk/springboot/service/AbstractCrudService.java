package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.mapper.IMapper;
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
        // exception car si l'id n'est pas trouvé, c'est une erreur grave.
        return repository.findById(id)
                         .orElseThrow(() -> new EntityNotFoundException("findById error : id " + id + " not found"));
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id identifier of the entity to be deleted
     * @throws EntityNotFoundException if the identifier does not exist in the database
     */
    public void deleteById(Integer id) {
        if (!repository.existsById(id)) {
            // exception car si l'id n'existe pas, c'est une erreur grave.
            throw new EntityNotFoundException("deleteById error : id " + id + " not found");
        }
        repository.deleteById(id);
    }


    /**
     * Creates a new entity from a creation DTO.
     *
     * @param dto the data necessary for the creation of the entity
     * @return the DTO representing the created entity
     */
    public DTO add(CREATE_DTO dto) {
        ENTITY entity = mapper.fromCreateRequestDto(dto);
        return mapper.toDto(repository.save(entity));
    }

    /**
     * Updates an existing entity from an update DTO.
     *
     * @param id identifier of the entity to be updated
     * @param dto DTO containing the new values
     * @return the DTO representing the updated entity
     */
    public DTO update(Integer id, UPDATE_DTO dto) {
        ENTITY entity = getById(id);
        
        mapper.updateEntityFromDto(entity, dto);
        
        return mapper.toDto(repository.save(entity));
    }
}
