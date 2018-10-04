package com.appdora.web.rest;

import com.appdora.AppDoraApp;

import com.appdora.domain.Noticia;
import com.appdora.domain.Portal;
import com.appdora.repository.NoticiaRepository;
import com.appdora.repository.search.NoticiaSearchRepository;
import com.appdora.service.NoticiaService;
import com.appdora.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static com.appdora.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NoticiaResource REST controller.
 *
 * @see NoticiaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppDoraApp.class)
public class NoticiaResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private NoticiaRepository noticiaRepository;
    
    @Autowired
    private NoticiaService noticiaService;

    /**
     * This repository is mocked in the com.appdora.repository.search test package.
     *
     * @see com.appdora.repository.search.NoticiaSearchRepositoryMockConfiguration
     */
    @Autowired
    private NoticiaSearchRepository mockNoticiaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNoticiaMockMvc;

    private Noticia noticia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NoticiaResource noticiaResource = new NoticiaResource(noticiaService);
        this.restNoticiaMockMvc = MockMvcBuilders.standaloneSetup(noticiaResource)
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
    public static Noticia createEntity(EntityManager em) {
        Noticia noticia = new Noticia()
            .descricao(DEFAULT_DESCRICAO)
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataFim(DEFAULT_DATA_FIM);
        // Add required entity
        Portal portal = PortalResourceIntTest.createEntity(em);
        em.persist(portal);
        em.flush();
        noticia.setPortal(portal);
        return noticia;
    }

    @Before
    public void initTest() {
        noticia = createEntity(em);
    }

    @Test
    @Transactional
    public void createNoticia() throws Exception {
        int databaseSizeBeforeCreate = noticiaRepository.findAll().size();

        // Create the Noticia
        restNoticiaMockMvc.perform(post("/api/noticias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noticia)))
            .andExpect(status().isCreated());

        // Validate the Noticia in the database
        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeCreate + 1);
        Noticia testNoticia = noticiaList.get(noticiaList.size() - 1);
        assertThat(testNoticia.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testNoticia.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testNoticia.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);

        // Validate the Noticia in Elasticsearch
        verify(mockNoticiaSearchRepository, times(1)).save(testNoticia);
    }

    @Test
    @Transactional
    public void createNoticiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = noticiaRepository.findAll().size();

        // Create the Noticia with an existing ID
        noticia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoticiaMockMvc.perform(post("/api/noticias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noticia)))
            .andExpect(status().isBadRequest());

        // Validate the Noticia in the database
        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Noticia in Elasticsearch
        verify(mockNoticiaSearchRepository, times(0)).save(noticia);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticiaRepository.findAll().size();
        // set the field null
        noticia.setDescricao(null);

        // Create the Noticia, which fails.

        restNoticiaMockMvc.perform(post("/api/noticias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noticia)))
            .andExpect(status().isBadRequest());

        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticiaRepository.findAll().size();
        // set the field null
        noticia.setDataInicio(null);

        // Create the Noticia, which fails.

        restNoticiaMockMvc.perform(post("/api/noticias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noticia)))
            .andExpect(status().isBadRequest());

        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataFimIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticiaRepository.findAll().size();
        // set the field null
        noticia.setDataFim(null);

        // Create the Noticia, which fails.

        restNoticiaMockMvc.perform(post("/api/noticias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noticia)))
            .andExpect(status().isBadRequest());

        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNoticias() throws Exception {
        // Initialize the database
        noticiaRepository.saveAndFlush(noticia);

        // Get all the noticiaList
        restNoticiaMockMvc.perform(get("/api/noticias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noticia.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())));
    }
    
    @Test
    @Transactional
    public void getNoticia() throws Exception {
        // Initialize the database
        noticiaRepository.saveAndFlush(noticia);

        // Get the noticia
        restNoticiaMockMvc.perform(get("/api/noticias/{id}", noticia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(noticia.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataFim").value(DEFAULT_DATA_FIM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNoticia() throws Exception {
        // Get the noticia
        restNoticiaMockMvc.perform(get("/api/noticias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNoticia() throws Exception {
        // Initialize the database
        noticiaService.save(noticia);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockNoticiaSearchRepository);

        int databaseSizeBeforeUpdate = noticiaRepository.findAll().size();

        // Update the noticia
        Noticia updatedNoticia = noticiaRepository.findById(noticia.getId()).get();
        // Disconnect from session so that the updates on updatedNoticia are not directly saved in db
        em.detach(updatedNoticia);
        updatedNoticia
            .descricao(UPDATED_DESCRICAO)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM);

        restNoticiaMockMvc.perform(put("/api/noticias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNoticia)))
            .andExpect(status().isOk());

        // Validate the Noticia in the database
        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeUpdate);
        Noticia testNoticia = noticiaList.get(noticiaList.size() - 1);
        assertThat(testNoticia.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testNoticia.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testNoticia.getDataFim()).isEqualTo(UPDATED_DATA_FIM);

        // Validate the Noticia in Elasticsearch
        verify(mockNoticiaSearchRepository, times(1)).save(testNoticia);
    }

    @Test
    @Transactional
    public void updateNonExistingNoticia() throws Exception {
        int databaseSizeBeforeUpdate = noticiaRepository.findAll().size();

        // Create the Noticia

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoticiaMockMvc.perform(put("/api/noticias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noticia)))
            .andExpect(status().isBadRequest());

        // Validate the Noticia in the database
        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Noticia in Elasticsearch
        verify(mockNoticiaSearchRepository, times(0)).save(noticia);
    }

    @Test
    @Transactional
    public void deleteNoticia() throws Exception {
        // Initialize the database
        noticiaService.save(noticia);

        int databaseSizeBeforeDelete = noticiaRepository.findAll().size();

        // Get the noticia
        restNoticiaMockMvc.perform(delete("/api/noticias/{id}", noticia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Noticia in Elasticsearch
        verify(mockNoticiaSearchRepository, times(1)).deleteById(noticia.getId());
    }

    @Test
    @Transactional
    public void searchNoticia() throws Exception {
        // Initialize the database
        noticiaService.save(noticia);
        when(mockNoticiaSearchRepository.search(queryStringQuery("id:" + noticia.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(noticia), PageRequest.of(0, 1), 1));
        // Search the noticia
        restNoticiaMockMvc.perform(get("/api/_search/noticias?query=id:" + noticia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noticia.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Noticia.class);
        Noticia noticia1 = new Noticia();
        noticia1.setId(1L);
        Noticia noticia2 = new Noticia();
        noticia2.setId(noticia1.getId());
        assertThat(noticia1).isEqualTo(noticia2);
        noticia2.setId(2L);
        assertThat(noticia1).isNotEqualTo(noticia2);
        noticia1.setId(null);
        assertThat(noticia1).isNotEqualTo(noticia2);
    }
}
