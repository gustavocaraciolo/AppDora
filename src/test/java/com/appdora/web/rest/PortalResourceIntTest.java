package com.appdora.web.rest;

import com.appdora.AppDoraApp;

import com.appdora.domain.Portal;
import com.appdora.domain.Utilizador;
import com.appdora.repository.PortalRepository;
import com.appdora.repository.search.PortalSearchRepository;
import com.appdora.service.PortalService;
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

import com.appdora.domain.enumeration.FlagSimNao;
import com.appdora.domain.enumeration.FlagSimNao;
/**
 * Test class for the PortalResource REST controller.
 *
 * @see PortalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppDoraApp.class)
public class PortalResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_ATIVACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ATIVACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final FlagSimNao DEFAULT_FLAG_DEFAULT = FlagSimNao.SIM;
    private static final FlagSimNao UPDATED_FLAG_DEFAULT = FlagSimNao.NAO;

    private static final String DEFAULT_TIPO_IDIOMA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_IDIOMA = "BBBBBBBBBB";

    private static final FlagSimNao DEFAULT_FLAG_SENHA_ENCRIPTADA = FlagSimNao.SIM;
    private static final FlagSimNao UPDATED_FLAG_SENHA_ENCRIPTADA = FlagSimNao.NAO;

    @Autowired
    private PortalRepository portalRepository;
    
    @Autowired
    private PortalService portalService;

    /**
     * This repository is mocked in the com.appdora.repository.search test package.
     *
     * @see com.appdora.repository.search.PortalSearchRepositoryMockConfiguration
     */
    @Autowired
    private PortalSearchRepository mockPortalSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPortalMockMvc;

    private Portal portal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PortalResource portalResource = new PortalResource(portalService);
        this.restPortalMockMvc = MockMvcBuilders.standaloneSetup(portalResource)
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
    public static Portal createEntity(EntityManager em) {
        Portal portal = new Portal()
            .descricao(DEFAULT_DESCRICAO)
            .dataAtivacao(DEFAULT_DATA_ATIVACAO)
            .flagDefault(DEFAULT_FLAG_DEFAULT)
            .tipoIdioma(DEFAULT_TIPO_IDIOMA)
            .flagSenhaEncriptada(DEFAULT_FLAG_SENHA_ENCRIPTADA);
        // Add required entity
        Utilizador utilizador = UtilizadorResourceIntTest.createEntity(em);
        em.persist(utilizador);
        em.flush();
        portal.getUtilizdors().add(utilizador);
        return portal;
    }

    @Before
    public void initTest() {
        portal = createEntity(em);
    }

    @Test
    @Transactional
    public void createPortal() throws Exception {
        int databaseSizeBeforeCreate = portalRepository.findAll().size();

        // Create the Portal
        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isCreated());

        // Validate the Portal in the database
        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeCreate + 1);
        Portal testPortal = portalList.get(portalList.size() - 1);
        assertThat(testPortal.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPortal.getDataAtivacao()).isEqualTo(DEFAULT_DATA_ATIVACAO);
        assertThat(testPortal.getFlagDefault()).isEqualTo(DEFAULT_FLAG_DEFAULT);
        assertThat(testPortal.getTipoIdioma()).isEqualTo(DEFAULT_TIPO_IDIOMA);
        assertThat(testPortal.getFlagSenhaEncriptada()).isEqualTo(DEFAULT_FLAG_SENHA_ENCRIPTADA);

        // Validate the Portal in Elasticsearch
        verify(mockPortalSearchRepository, times(1)).save(testPortal);
    }

    @Test
    @Transactional
    public void createPortalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = portalRepository.findAll().size();

        // Create the Portal with an existing ID
        portal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        // Validate the Portal in the database
        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeCreate);

        // Validate the Portal in Elasticsearch
        verify(mockPortalSearchRepository, times(0)).save(portal);
    }

    @Test
    @Transactional
    public void checkFlagDefaultIsRequired() throws Exception {
        int databaseSizeBeforeTest = portalRepository.findAll().size();
        // set the field null
        portal.setFlagDefault(null);

        // Create the Portal, which fails.

        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIdiomaIsRequired() throws Exception {
        int databaseSizeBeforeTest = portalRepository.findAll().size();
        // set the field null
        portal.setTipoIdioma(null);

        // Create the Portal, which fails.

        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPortals() throws Exception {
        // Initialize the database
        portalRepository.saveAndFlush(portal);

        // Get all the portalList
        restPortalMockMvc.perform(get("/api/portals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portal.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dataAtivacao").value(hasItem(DEFAULT_DATA_ATIVACAO.toString())))
            .andExpect(jsonPath("$.[*].flagDefault").value(hasItem(DEFAULT_FLAG_DEFAULT.toString())))
            .andExpect(jsonPath("$.[*].tipoIdioma").value(hasItem(DEFAULT_TIPO_IDIOMA.toString())))
            .andExpect(jsonPath("$.[*].flagSenhaEncriptada").value(hasItem(DEFAULT_FLAG_SENHA_ENCRIPTADA.toString())));
    }
    
    @Test
    @Transactional
    public void getPortal() throws Exception {
        // Initialize the database
        portalRepository.saveAndFlush(portal);

        // Get the portal
        restPortalMockMvc.perform(get("/api/portals/{id}", portal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(portal.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.dataAtivacao").value(DEFAULT_DATA_ATIVACAO.toString()))
            .andExpect(jsonPath("$.flagDefault").value(DEFAULT_FLAG_DEFAULT.toString()))
            .andExpect(jsonPath("$.tipoIdioma").value(DEFAULT_TIPO_IDIOMA.toString()))
            .andExpect(jsonPath("$.flagSenhaEncriptada").value(DEFAULT_FLAG_SENHA_ENCRIPTADA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPortal() throws Exception {
        // Get the portal
        restPortalMockMvc.perform(get("/api/portals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePortal() throws Exception {
        // Initialize the database
        portalService.save(portal);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPortalSearchRepository);

        int databaseSizeBeforeUpdate = portalRepository.findAll().size();

        // Update the portal
        Portal updatedPortal = portalRepository.findById(portal.getId()).get();
        // Disconnect from session so that the updates on updatedPortal are not directly saved in db
        em.detach(updatedPortal);
        updatedPortal
            .descricao(UPDATED_DESCRICAO)
            .dataAtivacao(UPDATED_DATA_ATIVACAO)
            .flagDefault(UPDATED_FLAG_DEFAULT)
            .tipoIdioma(UPDATED_TIPO_IDIOMA)
            .flagSenhaEncriptada(UPDATED_FLAG_SENHA_ENCRIPTADA);

        restPortalMockMvc.perform(put("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPortal)))
            .andExpect(status().isOk());

        // Validate the Portal in the database
        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeUpdate);
        Portal testPortal = portalList.get(portalList.size() - 1);
        assertThat(testPortal.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPortal.getDataAtivacao()).isEqualTo(UPDATED_DATA_ATIVACAO);
        assertThat(testPortal.getFlagDefault()).isEqualTo(UPDATED_FLAG_DEFAULT);
        assertThat(testPortal.getTipoIdioma()).isEqualTo(UPDATED_TIPO_IDIOMA);
        assertThat(testPortal.getFlagSenhaEncriptada()).isEqualTo(UPDATED_FLAG_SENHA_ENCRIPTADA);

        // Validate the Portal in Elasticsearch
        verify(mockPortalSearchRepository, times(1)).save(testPortal);
    }

    @Test
    @Transactional
    public void updateNonExistingPortal() throws Exception {
        int databaseSizeBeforeUpdate = portalRepository.findAll().size();

        // Create the Portal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortalMockMvc.perform(put("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        // Validate the Portal in the database
        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Portal in Elasticsearch
        verify(mockPortalSearchRepository, times(0)).save(portal);
    }

    @Test
    @Transactional
    public void deletePortal() throws Exception {
        // Initialize the database
        portalService.save(portal);

        int databaseSizeBeforeDelete = portalRepository.findAll().size();

        // Get the portal
        restPortalMockMvc.perform(delete("/api/portals/{id}", portal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Portal in Elasticsearch
        verify(mockPortalSearchRepository, times(1)).deleteById(portal.getId());
    }

    @Test
    @Transactional
    public void searchPortal() throws Exception {
        // Initialize the database
        portalService.save(portal);
        when(mockPortalSearchRepository.search(queryStringQuery("id:" + portal.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(portal), PageRequest.of(0, 1), 1));
        // Search the portal
        restPortalMockMvc.perform(get("/api/_search/portals?query=id:" + portal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portal.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dataAtivacao").value(hasItem(DEFAULT_DATA_ATIVACAO.toString())))
            .andExpect(jsonPath("$.[*].flagDefault").value(hasItem(DEFAULT_FLAG_DEFAULT.toString())))
            .andExpect(jsonPath("$.[*].tipoIdioma").value(hasItem(DEFAULT_TIPO_IDIOMA.toString())))
            .andExpect(jsonPath("$.[*].flagSenhaEncriptada").value(hasItem(DEFAULT_FLAG_SENHA_ENCRIPTADA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Portal.class);
        Portal portal1 = new Portal();
        portal1.setId(1L);
        Portal portal2 = new Portal();
        portal2.setId(portal1.getId());
        assertThat(portal1).isEqualTo(portal2);
        portal2.setId(2L);
        assertThat(portal1).isNotEqualTo(portal2);
        portal1.setId(null);
        assertThat(portal1).isNotEqualTo(portal2);
    }
}
