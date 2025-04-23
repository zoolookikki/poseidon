package com.nnk.springboot.controllers;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nnk.springboot.exception.EntityNotFoundException;

import lombok.extern.log4j.Log4j2;

/**
 * Generic abstract controller for handling CRUD operations with Spring MVC.
 *
 * <p>
 * This class centralizes standard operations (list display, add, update, delete form) for the different entities.
 * It is designed to be extended by entity-specific classes.
 * </p>
 *
 * <p>
 * Main features:
 * <ul>
 *     <li>Centralized view management for listing, adding, updating, deleting</li>
 *     <li>Factorization of current operations</li>
 *     <li>Validation with BindingResult</li>
 * </ul>
 * </p>
 *
 * @param <ENTITY>       the type of the persisted entity (eg: Bid, Rule)
 * @param <DTO>          the type of DTO displayed in the views (eg: BidDto)
 * @param <CREATE_DTO>   the type of DTO used for creation
 * @param <UPDATE_DTO>   the type of DTO used for the update
 */
@Log4j2
public abstract class AbstractCrudController<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> {

    
    // méthodes à implémenter dans la classe fille.
    
    /**
     * Entity name (used to construct view names and model attributes).
     *
     * @return the name of the entity in String format (eg: "bid", "rule")
     */
    protected abstract String getEntityName();
    /**
     * Retrieves the full list of entities converted to DTOs.
     *
     * @return list of DTOs
     */
    protected abstract List<DTO> findAll();
    /**
     * Returns an empty object used to pre-populate the add form.
     *
     * @return an empty DTO for creation
     */
    protected abstract CREATE_DTO emptyCreateDto();
    /**
     * Retrieves an existing entity by its ID.
     *
     * @param id entity identifier
     * @return the corresponding entity
     */
    protected abstract ENTITY getById(Integer id);
    /**
     * Converts an entity to a DTO for display in the update form.
     *
     * @param entity the entity to be converted
     * @return a pre-filled DTO for the update
     */
    protected abstract UPDATE_DTO getUpdateDto(ENTITY entity);
    /**
     * Deletes an entity based on its ID.
     *
     * @param id identifier of the entity to be deleted
     */
    protected abstract void deleteById(Integer id);
    /**
     * Adds a new entity from the creation DTO.
     *
     * @param dto DTO containing the data to be saved
     * @return the resulting DTO after registration
     */
    protected abstract DTO addEntity(CREATE_DTO dto);
    /**
     * Updates an existing entity with new data.
     *
     * @param id identifier of the entity to be updated
     * @param dto updated data
     * @return the updated DTO
     */
    protected abstract DTO updateEntity(Integer id, UPDATE_DTO dto);
    
    // méthodes internes.
    
    /**
     * Constructs the view path based on the entity name and suffix.
     *
     * @param suffix view name (eg: "list", "add", "update")
     * @return full path to view
     */
    private String view(String suffix) {
        return getEntityName() + "/" + suffix;
    }
    
    // méthodes communes.
    
    /**
     * Displays the list of entities.
     *
     * @param model model used to pass data to the view
     * @return the name of the view
     */
    @GetMapping("/list")
    public String list(Model model) {
        log.debug("GetMapping/list");
        
        model.addAttribute(getEntityName() + "s", findAll());
        
        return view("list");
    }
    
    /**
     * Displays the form for adding an entity.
     *
     * @param model model used to pass data to the view
     * @return the name of the view
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        log.debug("GetMapping/showAddForm");

        model.addAttribute(getEntityName(), emptyCreateDto());
        
        return view("add");
    }
    
    /**
     * Displays the update form for a given entity.
     *
     * @param id identifier of the entity to be updated
     * @param model model used to pass data to the view
     * @return the name of the view
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        log.debug("GetMapping/showUpdateForm,id="+id);
        
        ENTITY entity = getById(id);
        
        model.addAttribute(getEntityName(), getUpdateDto(entity));
        
        return view("update");
    }
    
    /**
     * Deletes an entity by its id and redirects to the list.
     *
     * @param id identifier of the entity to be deleted
     * @return redirect to the "list" view
     */
    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable Integer id) {
    public String delete(@PathVariable Integer id, Model model) {
    
        log.debug("GetMapping/delete,id="+id);
        
//        deleteById(id);
//        return "redirect:/" + getEntityName() + "/list";
        
        try {
            deleteById(id);
            return "redirect:/" + getEntityName() + "/list";
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
        
    }
    
    // méthodes de base appelées par toutes les classes filles.
    
    /**
     * Processes the creation of an entity after form submission.
     *
     * @param dto DTO containing the data to be created
     * @param result validation result
     * @param model model used to pass data to the view
     * @return redirect to the "list" view or return to form in case of error
     */
    protected String create(CREATE_DTO dto, BindingResult result, Model model) {
        log.debug("create,result="+result);
        
        if (result.hasErrors()) {
            model.addAttribute(getEntityName(), dto);
            return view("add");
        }
        
        addEntity(dto);
        
        return "redirect:/" + getEntityName() + "/list";
    }
    
    /**
     * Processes updating an entity after form submission.
     *
     * @param id identifier of the entity to be updated
     * @param dto DTO containing the updated data
     * @param result validation result
     * @param model model used to pass data to the view
     * @return redirect to the "list" view or return to form in case of error
     */
    protected String update(Integer id, UPDATE_DTO dto, BindingResult result, Model model) {
        log.debug("update,result="+result);
        
        if (result.hasErrors()) {
            model.addAttribute(getEntityName(), dto);
            return view("update");
        }
        
//        updateEntity(id, dto);
//        return "redirect:/" + getEntityName() + "/list";
        
        try {
            updateEntity(id, dto);
            return "redirect:/" + getEntityName() + "/list";
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
    }
}
