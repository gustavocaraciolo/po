package com.dora.service;

import com.dora.service.dto.RequestItemsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RequestItems.
 */
public interface RequestItemsService {

    /**
     * Save a requestItems.
     *
     * @param requestItemsDTO the entity to save
     * @return the persisted entity
     */
    RequestItemsDTO save(RequestItemsDTO requestItemsDTO);

    /**
     *  Get all the requestItems.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RequestItemsDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" requestItems.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RequestItemsDTO findOne(Long id);

    /**
     *  Delete the "id" requestItems.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
