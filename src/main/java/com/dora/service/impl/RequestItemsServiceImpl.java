package com.dora.service.impl;

import com.dora.service.RequestItemsService;
import com.dora.domain.RequestItems;
import com.dora.repository.RequestItemsRepository;
import com.dora.service.dto.RequestItemsDTO;
import com.dora.service.mapper.RequestItemsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing RequestItems.
 */
@Service
@Transactional
public class RequestItemsServiceImpl implements RequestItemsService{

    private final Logger log = LoggerFactory.getLogger(RequestItemsServiceImpl.class);

    private final RequestItemsRepository requestItemsRepository;

    private final RequestItemsMapper requestItemsMapper;

    public RequestItemsServiceImpl(RequestItemsRepository requestItemsRepository, RequestItemsMapper requestItemsMapper) {
        this.requestItemsRepository = requestItemsRepository;
        this.requestItemsMapper = requestItemsMapper;
    }

    /**
     * Save a requestItems.
     *
     * @param requestItemsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RequestItemsDTO save(RequestItemsDTO requestItemsDTO) {
        log.debug("Request to save RequestItems : {}", requestItemsDTO);
        RequestItems requestItems = requestItemsMapper.toEntity(requestItemsDTO);
        requestItems = requestItemsRepository.save(requestItems);
        return requestItemsMapper.toDto(requestItems);
    }

    /**
     *  Get all the requestItems.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RequestItemsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequestItems");
        return requestItemsRepository.findAll(pageable)
            .map(requestItemsMapper::toDto);
    }

    /**
     *  Get one requestItems by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RequestItemsDTO findOne(Long id) {
        log.debug("Request to get RequestItems : {}", id);
        RequestItems requestItems = requestItemsRepository.findOne(id);
        return requestItemsMapper.toDto(requestItems);
    }

    /**
     *  Delete the  requestItems by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequestItems : {}", id);
        requestItemsRepository.delete(id);
    }
}
