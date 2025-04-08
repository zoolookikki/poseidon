package com.nnk.springboot.service;

import java.util.List;

public interface ICrudService<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> {

    List<DTO> findAll();
    
    // getById plus clair que FindById car cette méthode renvoit toujours un id sinon il y a exception.
    ENTITY getById(Integer id);
    
    // add et update plus clair que save qui peut faire les 2.
    DTO add(CREATE_DTO dto);
    DTO update(Integer id, UPDATE_DTO dto);
    
    void deleteById(Integer id);

}
