package com.dora.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dora.service.RequestItemsService;
import com.dora.web.rest.errors.BadRequestAlertException;
import com.dora.web.rest.util.HeaderUtil;
import com.dora.web.rest.util.PaginationUtil;
import com.dora.service.dto.RequestItemsDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RequestItems.
 */
@RestController
@RequestMapping("/api")
public class RequestItemsResource {

    private final Logger log = LoggerFactory.getLogger(RequestItemsResource.class);

    private static final String ENTITY_NAME = "requestItems";

    private final RequestItemsService requestItemsService;

    public RequestItemsResource(RequestItemsService requestItemsService) {
        this.requestItemsService = requestItemsService;
    }

    /**
     * POST  /request-items : Create a new requestItems.
     *
     * @param requestItemsDTO the requestItemsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requestItemsDTO, or with status 400 (Bad Request) if the requestItems has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/request-items")
    @Timed
    public ResponseEntity<RequestItemsDTO> createRequestItems(@Valid @RequestBody RequestItemsDTO requestItemsDTO) throws URISyntaxException {
        log.debug("REST request to save RequestItems : {}", requestItemsDTO);
        if (requestItemsDTO.getId() != null) {
            throw new BadRequestAlertException("A new requestItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestItemsDTO result = requestItemsService.save(requestItemsDTO);
        return ResponseEntity.created(new URI("/api/request-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /request-items : Updates an existing requestItems.
     *
     * @param requestItemsDTO the requestItemsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requestItemsDTO,
     * or with status 400 (Bad Request) if the requestItemsDTO is not valid,
     * or with status 500 (Internal Server Error) if the requestItemsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/request-items")
    @Timed
    public ResponseEntity<RequestItemsDTO> updateRequestItems(@Valid @RequestBody RequestItemsDTO requestItemsDTO) throws URISyntaxException {
        log.debug("REST request to update RequestItems : {}", requestItemsDTO);
        if (requestItemsDTO.getId() == null) {
            return createRequestItems(requestItemsDTO);
        }
        RequestItemsDTO result = requestItemsService.save(requestItemsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requestItemsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /request-items : get all the requestItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of requestItems in body
     */
    @GetMapping("/request-items")
    @Timed
    public ResponseEntity<List<RequestItemsDTO>> getAllRequestItems(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RequestItems");
        Page<RequestItemsDTO> page = requestItemsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/request-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /request-items/:id : get the "id" requestItems.
     *
     * @param id the id of the requestItemsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requestItemsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/request-items/{id}")
    @Timed
    public ResponseEntity<RequestItemsDTO> getRequestItems(@PathVariable Long id) {
        log.debug("REST request to get RequestItems : {}", id);
        RequestItemsDTO requestItemsDTO = requestItemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(requestItemsDTO));
    }

    /**
     * DELETE  /request-items/:id : delete the "id" requestItems.
     *
     * @param id the id of the requestItemsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/request-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequestItems(@PathVariable Long id) {
        log.debug("REST request to delete RequestItems : {}", id);
        requestItemsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
