package com.dora.web.rest;

import com.dora.PoseidonApp;

import com.dora.domain.RequestItems;
import com.dora.domain.Product;
import com.dora.repository.RequestItemsRepository;
import com.dora.service.RequestItemsService;
import com.dora.service.dto.RequestItemsDTO;
import com.dora.service.mapper.RequestItemsMapper;
import com.dora.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.dora.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RequestItemsResource REST controller.
 *
 * @see RequestItemsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PoseidonApp.class)
public class RequestItemsResourceIntTest {

    private static final BigDecimal DEFAULT_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT = new BigDecimal(2);

    @Autowired
    private RequestItemsRepository requestItemsRepository;

    @Autowired
    private RequestItemsMapper requestItemsMapper;

    @Autowired
    private RequestItemsService requestItemsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRequestItemsMockMvc;

    private RequestItems requestItems;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequestItemsResource requestItemsResource = new RequestItemsResource(requestItemsService);
        this.restRequestItemsMockMvc = MockMvcBuilders.standaloneSetup(requestItemsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestItems createEntity(EntityManager em) {
        RequestItems requestItems = new RequestItems()
            .discount(DEFAULT_DISCOUNT);
        // Add required entity
        Product product = ProductResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        requestItems.setProduct(product);
        return requestItems;
    }

    @Before
    public void initTest() {
        requestItems = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequestItems() throws Exception {
        int databaseSizeBeforeCreate = requestItemsRepository.findAll().size();

        // Create the RequestItems
        RequestItemsDTO requestItemsDTO = requestItemsMapper.toDto(requestItems);
        restRequestItemsMockMvc.perform(post("/api/request-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestItemsDTO)))
            .andExpect(status().isCreated());

        // Validate the RequestItems in the database
        List<RequestItems> requestItemsList = requestItemsRepository.findAll();
        assertThat(requestItemsList).hasSize(databaseSizeBeforeCreate + 1);
        RequestItems testRequestItems = requestItemsList.get(requestItemsList.size() - 1);
        assertThat(testRequestItems.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    public void createRequestItemsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestItemsRepository.findAll().size();

        // Create the RequestItems with an existing ID
        requestItems.setId(1L);
        RequestItemsDTO requestItemsDTO = requestItemsMapper.toDto(requestItems);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestItemsMockMvc.perform(post("/api/request-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RequestItems in the database
        List<RequestItems> requestItemsList = requestItemsRepository.findAll();
        assertThat(requestItemsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRequestItems() throws Exception {
        // Initialize the database
        requestItemsRepository.saveAndFlush(requestItems);

        // Get all the requestItemsList
        restRequestItemsMockMvc.perform(get("/api/request-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getRequestItems() throws Exception {
        // Initialize the database
        requestItemsRepository.saveAndFlush(requestItems);

        // Get the requestItems
        restRequestItemsMockMvc.perform(get("/api/request-items/{id}", requestItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requestItems.getId().intValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRequestItems() throws Exception {
        // Get the requestItems
        restRequestItemsMockMvc.perform(get("/api/request-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequestItems() throws Exception {
        // Initialize the database
        requestItemsRepository.saveAndFlush(requestItems);
        int databaseSizeBeforeUpdate = requestItemsRepository.findAll().size();

        // Update the requestItems
        RequestItems updatedRequestItems = requestItemsRepository.findOne(requestItems.getId());
        updatedRequestItems
            .discount(UPDATED_DISCOUNT);
        RequestItemsDTO requestItemsDTO = requestItemsMapper.toDto(updatedRequestItems);

        restRequestItemsMockMvc.perform(put("/api/request-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestItemsDTO)))
            .andExpect(status().isOk());

        // Validate the RequestItems in the database
        List<RequestItems> requestItemsList = requestItemsRepository.findAll();
        assertThat(requestItemsList).hasSize(databaseSizeBeforeUpdate);
        RequestItems testRequestItems = requestItemsList.get(requestItemsList.size() - 1);
        assertThat(testRequestItems.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingRequestItems() throws Exception {
        int databaseSizeBeforeUpdate = requestItemsRepository.findAll().size();

        // Create the RequestItems
        RequestItemsDTO requestItemsDTO = requestItemsMapper.toDto(requestItems);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRequestItemsMockMvc.perform(put("/api/request-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestItemsDTO)))
            .andExpect(status().isCreated());

        // Validate the RequestItems in the database
        List<RequestItems> requestItemsList = requestItemsRepository.findAll();
        assertThat(requestItemsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRequestItems() throws Exception {
        // Initialize the database
        requestItemsRepository.saveAndFlush(requestItems);
        int databaseSizeBeforeDelete = requestItemsRepository.findAll().size();

        // Get the requestItems
        restRequestItemsMockMvc.perform(delete("/api/request-items/{id}", requestItems.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RequestItems> requestItemsList = requestItemsRepository.findAll();
        assertThat(requestItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestItems.class);
        RequestItems requestItems1 = new RequestItems();
        requestItems1.setId(1L);
        RequestItems requestItems2 = new RequestItems();
        requestItems2.setId(requestItems1.getId());
        assertThat(requestItems1).isEqualTo(requestItems2);
        requestItems2.setId(2L);
        assertThat(requestItems1).isNotEqualTo(requestItems2);
        requestItems1.setId(null);
        assertThat(requestItems1).isNotEqualTo(requestItems2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestItemsDTO.class);
        RequestItemsDTO requestItemsDTO1 = new RequestItemsDTO();
        requestItemsDTO1.setId(1L);
        RequestItemsDTO requestItemsDTO2 = new RequestItemsDTO();
        assertThat(requestItemsDTO1).isNotEqualTo(requestItemsDTO2);
        requestItemsDTO2.setId(requestItemsDTO1.getId());
        assertThat(requestItemsDTO1).isEqualTo(requestItemsDTO2);
        requestItemsDTO2.setId(2L);
        assertThat(requestItemsDTO1).isNotEqualTo(requestItemsDTO2);
        requestItemsDTO1.setId(null);
        assertThat(requestItemsDTO1).isNotEqualTo(requestItemsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(requestItemsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(requestItemsMapper.fromId(null)).isNull();
    }
}
