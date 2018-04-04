package com.appdora.web.rest;

import com.appdora.AppDoraApp;

import com.appdora.domain.ItensCheckout;
import com.appdora.repository.ItensCheckoutRepository;
import com.appdora.service.ItensCheckoutService;
import com.appdora.repository.search.ItensCheckoutSearchRepository;
import com.appdora.service.dto.ItensCheckoutDTO;
import com.appdora.service.mapper.ItensCheckoutMapper;
import com.appdora.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static com.appdora.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ItensCheckoutResource REST controller.
 *
 * @see ItensCheckoutResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppDoraApp.class)
public class ItensCheckoutResourceIntTest {

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    @Autowired
    private ItensCheckoutRepository itensCheckoutRepository;

    @Autowired
    private ItensCheckoutMapper itensCheckoutMapper;

    @Autowired
    private ItensCheckoutService itensCheckoutService;

    @Autowired
    private ItensCheckoutSearchRepository itensCheckoutSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restItensCheckoutMockMvc;

    private ItensCheckout itensCheckout;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItensCheckoutResource itensCheckoutResource = new ItensCheckoutResource(itensCheckoutService);
        this.restItensCheckoutMockMvc = MockMvcBuilders.standaloneSetup(itensCheckoutResource)
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
    public static ItensCheckout createEntity(EntityManager em) {
        ItensCheckout itensCheckout = new ItensCheckout()
            .quantidade(DEFAULT_QUANTIDADE);
        return itensCheckout;
    }

    @Before
    public void initTest() {
        itensCheckoutSearchRepository.deleteAll();
        itensCheckout = createEntity(em);
    }

    @Test
    @Transactional
    public void createItensCheckout() throws Exception {
        int databaseSizeBeforeCreate = itensCheckoutRepository.findAll().size();

        // Create the ItensCheckout
        ItensCheckoutDTO itensCheckoutDTO = itensCheckoutMapper.toDto(itensCheckout);
        restItensCheckoutMockMvc.perform(post("/api/itens-checkouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itensCheckoutDTO)))
            .andExpect(status().isCreated());

        // Validate the ItensCheckout in the database
        List<ItensCheckout> itensCheckoutList = itensCheckoutRepository.findAll();
        assertThat(itensCheckoutList).hasSize(databaseSizeBeforeCreate + 1);
        ItensCheckout testItensCheckout = itensCheckoutList.get(itensCheckoutList.size() - 1);
        assertThat(testItensCheckout.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);

        // Validate the ItensCheckout in Elasticsearch
        ItensCheckout itensCheckoutEs = itensCheckoutSearchRepository.findOne(testItensCheckout.getId());
        assertThat(itensCheckoutEs).isEqualToIgnoringGivenFields(testItensCheckout);
    }

    @Test
    @Transactional
    public void createItensCheckoutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itensCheckoutRepository.findAll().size();

        // Create the ItensCheckout with an existing ID
        itensCheckout.setId(1L);
        ItensCheckoutDTO itensCheckoutDTO = itensCheckoutMapper.toDto(itensCheckout);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItensCheckoutMockMvc.perform(post("/api/itens-checkouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itensCheckoutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItensCheckout in the database
        List<ItensCheckout> itensCheckoutList = itensCheckoutRepository.findAll();
        assertThat(itensCheckoutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllItensCheckouts() throws Exception {
        // Initialize the database
        itensCheckoutRepository.saveAndFlush(itensCheckout);

        // Get all the itensCheckoutList
        restItensCheckoutMockMvc.perform(get("/api/itens-checkouts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itensCheckout.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)));
    }

    @Test
    @Transactional
    public void getItensCheckout() throws Exception {
        // Initialize the database
        itensCheckoutRepository.saveAndFlush(itensCheckout);

        // Get the itensCheckout
        restItensCheckoutMockMvc.perform(get("/api/itens-checkouts/{id}", itensCheckout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itensCheckout.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE));
    }

    @Test
    @Transactional
    public void getNonExistingItensCheckout() throws Exception {
        // Get the itensCheckout
        restItensCheckoutMockMvc.perform(get("/api/itens-checkouts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItensCheckout() throws Exception {
        // Initialize the database
        itensCheckoutRepository.saveAndFlush(itensCheckout);
        itensCheckoutSearchRepository.save(itensCheckout);
        int databaseSizeBeforeUpdate = itensCheckoutRepository.findAll().size();

        // Update the itensCheckout
        ItensCheckout updatedItensCheckout = itensCheckoutRepository.findOne(itensCheckout.getId());
        // Disconnect from session so that the updates on updatedItensCheckout are not directly saved in db
        em.detach(updatedItensCheckout);
        updatedItensCheckout
            .quantidade(UPDATED_QUANTIDADE);
        ItensCheckoutDTO itensCheckoutDTO = itensCheckoutMapper.toDto(updatedItensCheckout);

        restItensCheckoutMockMvc.perform(put("/api/itens-checkouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itensCheckoutDTO)))
            .andExpect(status().isOk());

        // Validate the ItensCheckout in the database
        List<ItensCheckout> itensCheckoutList = itensCheckoutRepository.findAll();
        assertThat(itensCheckoutList).hasSize(databaseSizeBeforeUpdate);
        ItensCheckout testItensCheckout = itensCheckoutList.get(itensCheckoutList.size() - 1);
        assertThat(testItensCheckout.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);

        // Validate the ItensCheckout in Elasticsearch
        ItensCheckout itensCheckoutEs = itensCheckoutSearchRepository.findOne(testItensCheckout.getId());
        assertThat(itensCheckoutEs).isEqualToIgnoringGivenFields(testItensCheckout);
    }

    @Test
    @Transactional
    public void updateNonExistingItensCheckout() throws Exception {
        int databaseSizeBeforeUpdate = itensCheckoutRepository.findAll().size();

        // Create the ItensCheckout
        ItensCheckoutDTO itensCheckoutDTO = itensCheckoutMapper.toDto(itensCheckout);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restItensCheckoutMockMvc.perform(put("/api/itens-checkouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itensCheckoutDTO)))
            .andExpect(status().isCreated());

        // Validate the ItensCheckout in the database
        List<ItensCheckout> itensCheckoutList = itensCheckoutRepository.findAll();
        assertThat(itensCheckoutList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteItensCheckout() throws Exception {
        // Initialize the database
        itensCheckoutRepository.saveAndFlush(itensCheckout);
        itensCheckoutSearchRepository.save(itensCheckout);
        int databaseSizeBeforeDelete = itensCheckoutRepository.findAll().size();

        // Get the itensCheckout
        restItensCheckoutMockMvc.perform(delete("/api/itens-checkouts/{id}", itensCheckout.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean itensCheckoutExistsInEs = itensCheckoutSearchRepository.exists(itensCheckout.getId());
        assertThat(itensCheckoutExistsInEs).isFalse();

        // Validate the database is empty
        List<ItensCheckout> itensCheckoutList = itensCheckoutRepository.findAll();
        assertThat(itensCheckoutList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchItensCheckout() throws Exception {
        // Initialize the database
        itensCheckoutRepository.saveAndFlush(itensCheckout);
        itensCheckoutSearchRepository.save(itensCheckout);

        // Search the itensCheckout
        restItensCheckoutMockMvc.perform(get("/api/_search/itens-checkouts?query=id:" + itensCheckout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itensCheckout.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItensCheckout.class);
        ItensCheckout itensCheckout1 = new ItensCheckout();
        itensCheckout1.setId(1L);
        ItensCheckout itensCheckout2 = new ItensCheckout();
        itensCheckout2.setId(itensCheckout1.getId());
        assertThat(itensCheckout1).isEqualTo(itensCheckout2);
        itensCheckout2.setId(2L);
        assertThat(itensCheckout1).isNotEqualTo(itensCheckout2);
        itensCheckout1.setId(null);
        assertThat(itensCheckout1).isNotEqualTo(itensCheckout2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItensCheckoutDTO.class);
        ItensCheckoutDTO itensCheckoutDTO1 = new ItensCheckoutDTO();
        itensCheckoutDTO1.setId(1L);
        ItensCheckoutDTO itensCheckoutDTO2 = new ItensCheckoutDTO();
        assertThat(itensCheckoutDTO1).isNotEqualTo(itensCheckoutDTO2);
        itensCheckoutDTO2.setId(itensCheckoutDTO1.getId());
        assertThat(itensCheckoutDTO1).isEqualTo(itensCheckoutDTO2);
        itensCheckoutDTO2.setId(2L);
        assertThat(itensCheckoutDTO1).isNotEqualTo(itensCheckoutDTO2);
        itensCheckoutDTO1.setId(null);
        assertThat(itensCheckoutDTO1).isNotEqualTo(itensCheckoutDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itensCheckoutMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itensCheckoutMapper.fromId(null)).isNull();
    }
}
