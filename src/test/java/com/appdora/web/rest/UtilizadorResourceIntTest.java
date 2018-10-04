package com.appdora.web.rest;

import com.appdora.AppDoraApp;

import com.appdora.domain.Utilizador;
import com.appdora.domain.User;
import com.appdora.repository.UtilizadorRepository;
import com.appdora.repository.search.UtilizadorSearchRepository;
import com.appdora.service.UtilizadorService;
import com.appdora.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static com.appdora.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.appdora.domain.enumeration.Genero;
/**
 * Test class for the UtilizadorResource REST controller.
 *
 * @see UtilizadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppDoraApp.class)
public class UtilizadorResourceIntTest {

    private static final String DEFAULT_PRIMEIRO_NOME = "AAAAAAAAAA";
    private static final String UPDATED_PRIMEIRO_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_ULTIMO_NOME = "AAAAAAAAAA";
    private static final String UPDATED_ULTIMO_NOME = "BBBBBBBBBB";

    private static final Genero DEFAULT_GENERO = Genero.MASCULINO;
    private static final Genero UPDATED_GENERO = Genero.FEMININO;

    private static final String DEFAULT_EMAIL = "4'@yg.z";
    private static final String UPDATED_EMAIL = "O@V!.'-";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO_LINHA_1 = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO_LINHA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO_LINHA_2 = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO_LINHA_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @Mock
    private UtilizadorRepository utilizadorRepositoryMock;
    

    @Mock
    private UtilizadorService utilizadorServiceMock;

    @Autowired
    private UtilizadorService utilizadorService;

    /**
     * This repository is mocked in the com.appdora.repository.search test package.
     *
     * @see com.appdora.repository.search.UtilizadorSearchRepositoryMockConfiguration
     */
    @Autowired
    private UtilizadorSearchRepository mockUtilizadorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUtilizadorMockMvc;

    private Utilizador utilizador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UtilizadorResource utilizadorResource = new UtilizadorResource(utilizadorService);
        this.restUtilizadorMockMvc = MockMvcBuilders.standaloneSetup(utilizadorResource)
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
    public static Utilizador createEntity(EntityManager em) {
        Utilizador utilizador = new Utilizador()
            .primeiroNome(DEFAULT_PRIMEIRO_NOME)
            .ultimoNome(DEFAULT_ULTIMO_NOME)
            .genero(DEFAULT_GENERO)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE)
            .enderecoLinha1(DEFAULT_ENDERECO_LINHA_1)
            .enderecoLinha2(DEFAULT_ENDERECO_LINHA_2)
            .cidade(DEFAULT_CIDADE)
            .pais(DEFAULT_PAIS);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        utilizador.setUser(user);
        return utilizador;
    }

    @Before
    public void initTest() {
        utilizador = createEntity(em);
    }

    @Test
    @Transactional
    public void createUtilizador() throws Exception {
        int databaseSizeBeforeCreate = utilizadorRepository.findAll().size();

        // Create the Utilizador
        restUtilizadorMockMvc.perform(post("/api/utilizadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilizador)))
            .andExpect(status().isCreated());

        // Validate the Utilizador in the database
        List<Utilizador> utilizadorList = utilizadorRepository.findAll();
        assertThat(utilizadorList).hasSize(databaseSizeBeforeCreate + 1);
        Utilizador testUtilizador = utilizadorList.get(utilizadorList.size() - 1);
        assertThat(testUtilizador.getPrimeiroNome()).isEqualTo(DEFAULT_PRIMEIRO_NOME);
        assertThat(testUtilizador.getUltimoNome()).isEqualTo(DEFAULT_ULTIMO_NOME);
        assertThat(testUtilizador.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testUtilizador.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUtilizador.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testUtilizador.getEnderecoLinha1()).isEqualTo(DEFAULT_ENDERECO_LINHA_1);
        assertThat(testUtilizador.getEnderecoLinha2()).isEqualTo(DEFAULT_ENDERECO_LINHA_2);
        assertThat(testUtilizador.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testUtilizador.getPais()).isEqualTo(DEFAULT_PAIS);

        // Validate the Utilizador in Elasticsearch
        verify(mockUtilizadorSearchRepository, times(1)).save(testUtilizador);
    }

    @Test
    @Transactional
    public void createUtilizadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = utilizadorRepository.findAll().size();

        // Create the Utilizador with an existing ID
        utilizador.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUtilizadorMockMvc.perform(post("/api/utilizadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilizador)))
            .andExpect(status().isBadRequest());

        // Validate the Utilizador in the database
        List<Utilizador> utilizadorList = utilizadorRepository.findAll();
        assertThat(utilizadorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Utilizador in Elasticsearch
        verify(mockUtilizadorSearchRepository, times(0)).save(utilizador);
    }

    @Test
    @Transactional
    public void checkPrimeiroNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilizadorRepository.findAll().size();
        // set the field null
        utilizador.setPrimeiroNome(null);

        // Create the Utilizador, which fails.

        restUtilizadorMockMvc.perform(post("/api/utilizadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilizador)))
            .andExpect(status().isBadRequest());

        List<Utilizador> utilizadorList = utilizadorRepository.findAll();
        assertThat(utilizadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUltimoNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilizadorRepository.findAll().size();
        // set the field null
        utilizador.setUltimoNome(null);

        // Create the Utilizador, which fails.

        restUtilizadorMockMvc.perform(post("/api/utilizadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilizador)))
            .andExpect(status().isBadRequest());

        List<Utilizador> utilizadorList = utilizadorRepository.findAll();
        assertThat(utilizadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeneroIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilizadorRepository.findAll().size();
        // set the field null
        utilizador.setGenero(null);

        // Create the Utilizador, which fails.

        restUtilizadorMockMvc.perform(post("/api/utilizadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilizador)))
            .andExpect(status().isBadRequest());

        List<Utilizador> utilizadorList = utilizadorRepository.findAll();
        assertThat(utilizadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilizadorRepository.findAll().size();
        // set the field null
        utilizador.setEmail(null);

        // Create the Utilizador, which fails.

        restUtilizadorMockMvc.perform(post("/api/utilizadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilizador)))
            .andExpect(status().isBadRequest());

        List<Utilizador> utilizadorList = utilizadorRepository.findAll();
        assertThat(utilizadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilizadorRepository.findAll().size();
        // set the field null
        utilizador.setTelefone(null);

        // Create the Utilizador, which fails.

        restUtilizadorMockMvc.perform(post("/api/utilizadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilizador)))
            .andExpect(status().isBadRequest());

        List<Utilizador> utilizadorList = utilizadorRepository.findAll();
        assertThat(utilizadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoLinha1IsRequired() throws Exception {
        int databaseSizeBeforeTest = utilizadorRepository.findAll().size();
        // set the field null
        utilizador.setEnderecoLinha1(null);

        // Create the Utilizador, which fails.

        restUtilizadorMockMvc.perform(post("/api/utilizadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilizador)))
            .andExpect(status().isBadRequest());

        List<Utilizador> utilizadorList = utilizadorRepository.findAll();
        assertThat(utilizadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilizadorRepository.findAll().size();
        // set the field null
        utilizador.setCidade(null);

        // Create the Utilizador, which fails.

        restUtilizadorMockMvc.perform(post("/api/utilizadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilizador)))
            .andExpect(status().isBadRequest());

        List<Utilizador> utilizadorList = utilizadorRepository.findAll();
        assertThat(utilizadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilizadorRepository.findAll().size();
        // set the field null
        utilizador.setPais(null);

        // Create the Utilizador, which fails.

        restUtilizadorMockMvc.perform(post("/api/utilizadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilizador)))
            .andExpect(status().isBadRequest());

        List<Utilizador> utilizadorList = utilizadorRepository.findAll();
        assertThat(utilizadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUtilizadors() throws Exception {
        // Initialize the database
        utilizadorRepository.saveAndFlush(utilizador);

        // Get all the utilizadorList
        restUtilizadorMockMvc.perform(get("/api/utilizadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(utilizador.getId().intValue())))
            .andExpect(jsonPath("$.[*].primeiroNome").value(hasItem(DEFAULT_PRIMEIRO_NOME.toString())))
            .andExpect(jsonPath("$.[*].ultimoNome").value(hasItem(DEFAULT_ULTIMO_NOME.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())))
            .andExpect(jsonPath("$.[*].enderecoLinha1").value(hasItem(DEFAULT_ENDERECO_LINHA_1.toString())))
            .andExpect(jsonPath("$.[*].enderecoLinha2").value(hasItem(DEFAULT_ENDERECO_LINHA_2.toString())))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE.toString())))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS.toString())));
    }
    
    public void getAllUtilizadorsWithEagerRelationshipsIsEnabled() throws Exception {
        UtilizadorResource utilizadorResource = new UtilizadorResource(utilizadorServiceMock);
        when(utilizadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restUtilizadorMockMvc = MockMvcBuilders.standaloneSetup(utilizadorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUtilizadorMockMvc.perform(get("/api/utilizadors?eagerload=true"))
        .andExpect(status().isOk());

        verify(utilizadorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllUtilizadorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        UtilizadorResource utilizadorResource = new UtilizadorResource(utilizadorServiceMock);
            when(utilizadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restUtilizadorMockMvc = MockMvcBuilders.standaloneSetup(utilizadorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUtilizadorMockMvc.perform(get("/api/utilizadors?eagerload=true"))
        .andExpect(status().isOk());

            verify(utilizadorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUtilizador() throws Exception {
        // Initialize the database
        utilizadorRepository.saveAndFlush(utilizador);

        // Get the utilizador
        restUtilizadorMockMvc.perform(get("/api/utilizadors/{id}", utilizador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(utilizador.getId().intValue()))
            .andExpect(jsonPath("$.primeiroNome").value(DEFAULT_PRIMEIRO_NOME.toString()))
            .andExpect(jsonPath("$.ultimoNome").value(DEFAULT_ULTIMO_NOME.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()))
            .andExpect(jsonPath("$.enderecoLinha1").value(DEFAULT_ENDERECO_LINHA_1.toString()))
            .andExpect(jsonPath("$.enderecoLinha2").value(DEFAULT_ENDERECO_LINHA_2.toString()))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE.toString()))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUtilizador() throws Exception {
        // Get the utilizador
        restUtilizadorMockMvc.perform(get("/api/utilizadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUtilizador() throws Exception {
        // Initialize the database
        utilizadorService.save(utilizador);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockUtilizadorSearchRepository);

        int databaseSizeBeforeUpdate = utilizadorRepository.findAll().size();

        // Update the utilizador
        Utilizador updatedUtilizador = utilizadorRepository.findById(utilizador.getId()).get();
        // Disconnect from session so that the updates on updatedUtilizador are not directly saved in db
        em.detach(updatedUtilizador);
        updatedUtilizador
            .primeiroNome(UPDATED_PRIMEIRO_NOME)
            .ultimoNome(UPDATED_ULTIMO_NOME)
            .genero(UPDATED_GENERO)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .enderecoLinha1(UPDATED_ENDERECO_LINHA_1)
            .enderecoLinha2(UPDATED_ENDERECO_LINHA_2)
            .cidade(UPDATED_CIDADE)
            .pais(UPDATED_PAIS);

        restUtilizadorMockMvc.perform(put("/api/utilizadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUtilizador)))
            .andExpect(status().isOk());

        // Validate the Utilizador in the database
        List<Utilizador> utilizadorList = utilizadorRepository.findAll();
        assertThat(utilizadorList).hasSize(databaseSizeBeforeUpdate);
        Utilizador testUtilizador = utilizadorList.get(utilizadorList.size() - 1);
        assertThat(testUtilizador.getPrimeiroNome()).isEqualTo(UPDATED_PRIMEIRO_NOME);
        assertThat(testUtilizador.getUltimoNome()).isEqualTo(UPDATED_ULTIMO_NOME);
        assertThat(testUtilizador.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testUtilizador.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUtilizador.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testUtilizador.getEnderecoLinha1()).isEqualTo(UPDATED_ENDERECO_LINHA_1);
        assertThat(testUtilizador.getEnderecoLinha2()).isEqualTo(UPDATED_ENDERECO_LINHA_2);
        assertThat(testUtilizador.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testUtilizador.getPais()).isEqualTo(UPDATED_PAIS);

        // Validate the Utilizador in Elasticsearch
        verify(mockUtilizadorSearchRepository, times(1)).save(testUtilizador);
    }

    @Test
    @Transactional
    public void updateNonExistingUtilizador() throws Exception {
        int databaseSizeBeforeUpdate = utilizadorRepository.findAll().size();

        // Create the Utilizador

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUtilizadorMockMvc.perform(put("/api/utilizadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilizador)))
            .andExpect(status().isBadRequest());

        // Validate the Utilizador in the database
        List<Utilizador> utilizadorList = utilizadorRepository.findAll();
        assertThat(utilizadorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Utilizador in Elasticsearch
        verify(mockUtilizadorSearchRepository, times(0)).save(utilizador);
    }

    @Test
    @Transactional
    public void deleteUtilizador() throws Exception {
        // Initialize the database
        utilizadorService.save(utilizador);

        int databaseSizeBeforeDelete = utilizadorRepository.findAll().size();

        // Get the utilizador
        restUtilizadorMockMvc.perform(delete("/api/utilizadors/{id}", utilizador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Utilizador> utilizadorList = utilizadorRepository.findAll();
        assertThat(utilizadorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Utilizador in Elasticsearch
        verify(mockUtilizadorSearchRepository, times(1)).deleteById(utilizador.getId());
    }

    @Test
    @Transactional
    public void searchUtilizador() throws Exception {
        // Initialize the database
        utilizadorService.save(utilizador);
        when(mockUtilizadorSearchRepository.search(queryStringQuery("id:" + utilizador.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(utilizador), PageRequest.of(0, 1), 1));
        // Search the utilizador
        restUtilizadorMockMvc.perform(get("/api/_search/utilizadors?query=id:" + utilizador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(utilizador.getId().intValue())))
            .andExpect(jsonPath("$.[*].primeiroNome").value(hasItem(DEFAULT_PRIMEIRO_NOME.toString())))
            .andExpect(jsonPath("$.[*].ultimoNome").value(hasItem(DEFAULT_ULTIMO_NOME.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())))
            .andExpect(jsonPath("$.[*].enderecoLinha1").value(hasItem(DEFAULT_ENDERECO_LINHA_1.toString())))
            .andExpect(jsonPath("$.[*].enderecoLinha2").value(hasItem(DEFAULT_ENDERECO_LINHA_2.toString())))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE.toString())))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Utilizador.class);
        Utilizador utilizador1 = new Utilizador();
        utilizador1.setId(1L);
        Utilizador utilizador2 = new Utilizador();
        utilizador2.setId(utilizador1.getId());
        assertThat(utilizador1).isEqualTo(utilizador2);
        utilizador2.setId(2L);
        assertThat(utilizador1).isNotEqualTo(utilizador2);
        utilizador1.setId(null);
        assertThat(utilizador1).isNotEqualTo(utilizador2);
    }
}
