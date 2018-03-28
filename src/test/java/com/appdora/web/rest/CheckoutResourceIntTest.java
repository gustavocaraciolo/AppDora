package com.appdora.web.rest;

import com.appdora.AppDoraApp;

import com.appdora.domain.Checkout;
import com.appdora.repository.CheckoutRepository;
import com.appdora.service.CheckoutService;
import com.appdora.repository.search.CheckoutSearchRepository;
import com.appdora.service.dto.CheckoutDTO;
import com.appdora.service.mapper.CheckoutMapper;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.appdora.web.rest.TestUtil.sameInstant;
import static com.appdora.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CheckoutResource REST controller.
 *
 * @see CheckoutResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppDoraApp.class)
public class CheckoutResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATA_HORA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_HORA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    private static final BigDecimal DEFAULT_DESCONTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_DESCONTO = new BigDecimal(2);

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private CheckoutMapper checkoutMapper;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private CheckoutSearchRepository checkoutSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCheckoutMockMvc;

    private Checkout checkout;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CheckoutResource checkoutResource = new CheckoutResource(checkoutService);
        this.restCheckoutMockMvc = MockMvcBuilders.standaloneSetup(checkoutResource)
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
    public static Checkout createEntity(EntityManager em) {
        Checkout checkout = new Checkout()
            .dataHora(DEFAULT_DATA_HORA)
            .quantidade(DEFAULT_QUANTIDADE)
            .desconto(DEFAULT_DESCONTO);
        return checkout;
    }

    @Before
    public void initTest() {
        checkoutSearchRepository.deleteAll();
        checkout = createEntity(em);
    }

    @Test
    @Transactional
    public void createCheckout() throws Exception {
        int databaseSizeBeforeCreate = checkoutRepository.findAll().size();

        // Create the Checkout
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(checkout);
        restCheckoutMockMvc.perform(post("/api/checkouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkoutDTO)))
            .andExpect(status().isCreated());

        // Validate the Checkout in the database
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeCreate + 1);
        Checkout testCheckout = checkoutList.get(checkoutList.size() - 1);
        assertThat(testCheckout.getDataHora()).isEqualTo(DEFAULT_DATA_HORA);
        assertThat(testCheckout.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testCheckout.getDesconto()).isEqualTo(DEFAULT_DESCONTO);

        // Validate the Checkout in Elasticsearch
        Checkout checkoutEs = checkoutSearchRepository.findOne(testCheckout.getId());
        assertThat(testCheckout.getDataHora()).isEqualTo(testCheckout.getDataHora());
        assertThat(checkoutEs).isEqualToIgnoringGivenFields(testCheckout, "dataHora");
    }

    @Test
    @Transactional
    public void createCheckoutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checkoutRepository.findAll().size();

        // Create the Checkout with an existing ID
        checkout.setId(1L);
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(checkout);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckoutMockMvc.perform(post("/api/checkouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkoutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Checkout in the database
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDataHoraIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkoutRepository.findAll().size();
        // set the field null
        checkout.setDataHora(null);

        // Create the Checkout, which fails.
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(checkout);

        restCheckoutMockMvc.perform(post("/api/checkouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkoutDTO)))
            .andExpect(status().isBadRequest());

        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCheckouts() throws Exception {
        // Initialize the database
        checkoutRepository.saveAndFlush(checkout);

        // Get all the checkoutList
        restCheckoutMockMvc.perform(get("/api/checkouts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkout.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHora").value(hasItem(sameInstant(DEFAULT_DATA_HORA))))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.intValue())));
    }

    @Test
    @Transactional
    public void getCheckout() throws Exception {
        // Initialize the database
        checkoutRepository.saveAndFlush(checkout);

        // Get the checkout
        restCheckoutMockMvc.perform(get("/api/checkouts/{id}", checkout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(checkout.getId().intValue()))
            .andExpect(jsonPath("$.dataHora").value(sameInstant(DEFAULT_DATA_HORA)))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.desconto").value(DEFAULT_DESCONTO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCheckout() throws Exception {
        // Get the checkout
        restCheckoutMockMvc.perform(get("/api/checkouts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCheckout() throws Exception {
        // Initialize the database
        checkoutRepository.saveAndFlush(checkout);
        checkoutSearchRepository.save(checkout);
        int databaseSizeBeforeUpdate = checkoutRepository.findAll().size();

        // Update the checkout
        Checkout updatedCheckout = checkoutRepository.findOne(checkout.getId());
        // Disconnect from session so that the updates on updatedCheckout are not directly saved in db
        em.detach(updatedCheckout);
        updatedCheckout
            .dataHora(UPDATED_DATA_HORA)
            .quantidade(UPDATED_QUANTIDADE)
            .desconto(UPDATED_DESCONTO);
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(updatedCheckout);

        restCheckoutMockMvc.perform(put("/api/checkouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkoutDTO)))
            .andExpect(status().isOk());

        // Validate the Checkout in the database
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeUpdate);
        Checkout testCheckout = checkoutList.get(checkoutList.size() - 1);
        assertThat(testCheckout.getDataHora()).isEqualTo(UPDATED_DATA_HORA);
        assertThat(testCheckout.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testCheckout.getDesconto()).isEqualTo(UPDATED_DESCONTO);

        // Validate the Checkout in Elasticsearch
        Checkout checkoutEs = checkoutSearchRepository.findOne(testCheckout.getId());
        assertThat(testCheckout.getDataHora()).isEqualTo(testCheckout.getDataHora());
        assertThat(checkoutEs).isEqualToIgnoringGivenFields(testCheckout, "dataHora");
    }

    @Test
    @Transactional
    public void updateNonExistingCheckout() throws Exception {
        int databaseSizeBeforeUpdate = checkoutRepository.findAll().size();

        // Create the Checkout
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(checkout);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCheckoutMockMvc.perform(put("/api/checkouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkoutDTO)))
            .andExpect(status().isCreated());

        // Validate the Checkout in the database
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCheckout() throws Exception {
        // Initialize the database
        checkoutRepository.saveAndFlush(checkout);
        checkoutSearchRepository.save(checkout);
        int databaseSizeBeforeDelete = checkoutRepository.findAll().size();

        // Get the checkout
        restCheckoutMockMvc.perform(delete("/api/checkouts/{id}", checkout.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean checkoutExistsInEs = checkoutSearchRepository.exists(checkout.getId());
        assertThat(checkoutExistsInEs).isFalse();

        // Validate the database is empty
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCheckout() throws Exception {
        // Initialize the database
        checkoutRepository.saveAndFlush(checkout);
        checkoutSearchRepository.save(checkout);

        // Search the checkout
        restCheckoutMockMvc.perform(get("/api/_search/checkouts?query=id:" + checkout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkout.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHora").value(hasItem(sameInstant(DEFAULT_DATA_HORA))))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Checkout.class);
        Checkout checkout1 = new Checkout();
        checkout1.setId(1L);
        Checkout checkout2 = new Checkout();
        checkout2.setId(checkout1.getId());
        assertThat(checkout1).isEqualTo(checkout2);
        checkout2.setId(2L);
        assertThat(checkout1).isNotEqualTo(checkout2);
        checkout1.setId(null);
        assertThat(checkout1).isNotEqualTo(checkout2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckoutDTO.class);
        CheckoutDTO checkoutDTO1 = new CheckoutDTO();
        checkoutDTO1.setId(1L);
        CheckoutDTO checkoutDTO2 = new CheckoutDTO();
        assertThat(checkoutDTO1).isNotEqualTo(checkoutDTO2);
        checkoutDTO2.setId(checkoutDTO1.getId());
        assertThat(checkoutDTO1).isEqualTo(checkoutDTO2);
        checkoutDTO2.setId(2L);
        assertThat(checkoutDTO1).isNotEqualTo(checkoutDTO2);
        checkoutDTO1.setId(null);
        assertThat(checkoutDTO1).isNotEqualTo(checkoutDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(checkoutMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(checkoutMapper.fromId(null)).isNull();
    }
}
