package com.appdora.web.rest;

import com.appdora.AppDoraApp;

import com.appdora.domain.Produto;
import com.appdora.repository.ProdutoRepository;
import com.appdora.service.ProdutoService;
import com.appdora.repository.search.ProdutoSearchRepository;
import com.appdora.service.dto.ProdutoDTO;
import com.appdora.service.mapper.ProdutoMapper;
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
import java.util.List;

import static com.appdora.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProdutoResource REST controller.
 *
 * @see ProdutoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppDoraApp.class)
public class ProdutoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    private static final BigDecimal DEFAULT_PRECO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECO = new BigDecimal(2);

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoSearchRepository produtoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProdutoMockMvc;

    private Produto produto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProdutoResource produtoResource = new ProdutoResource(produtoService);
        this.restProdutoMockMvc = MockMvcBuilders.standaloneSetup(produtoResource)
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
    public static Produto createEntity(EntityManager em) {
        Produto produto = new Produto()
            .nome(DEFAULT_NOME)
            .quantidade(DEFAULT_QUANTIDADE)
            .preco(DEFAULT_PRECO);
        return produto;
    }

    @Before
    public void initTest() {
        produtoSearchRepository.deleteAll();
        produto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduto() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);
        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isCreated());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate + 1);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProduto.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testProduto.getPreco()).isEqualTo(DEFAULT_PRECO);

        // Validate the Produto in Elasticsearch
        Produto produtoEs = produtoSearchRepository.findOne(testProduto.getId());
        assertThat(produtoEs).isEqualToIgnoringGivenFields(testProduto);
    }

    @Test
    @Transactional
    public void createProdutoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto with an existing ID
        produto.setId(1L);
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setNome(null);

        // Create the Produto, which fails.
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);

        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProdutos() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList
        restProdutoMockMvc.perform(get("/api/produtos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.intValue())));
    }

    @Test
    @Transactional
    public void getProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", produto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.preco").value(DEFAULT_PRECO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProduto() throws Exception {
        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);
        produtoSearchRepository.save(produto);
        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Update the produto
        Produto updatedProduto = produtoRepository.findOne(produto.getId());
        // Disconnect from session so that the updates on updatedProduto are not directly saved in db
        em.detach(updatedProduto);
        updatedProduto
            .nome(UPDATED_NOME)
            .quantidade(UPDATED_QUANTIDADE)
            .preco(UPDATED_PRECO);
        ProdutoDTO produtoDTO = produtoMapper.toDto(updatedProduto);

        restProdutoMockMvc.perform(put("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isOk());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProduto.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testProduto.getPreco()).isEqualTo(UPDATED_PRECO);

        // Validate the Produto in Elasticsearch
        Produto produtoEs = produtoSearchRepository.findOne(testProduto.getId());
        assertThat(produtoEs).isEqualToIgnoringGivenFields(testProduto);
    }

    @Test
    @Transactional
    public void updateNonExistingProduto() throws Exception {
        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Create the Produto
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProdutoMockMvc.perform(put("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isCreated());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);
        produtoSearchRepository.save(produto);
        int databaseSizeBeforeDelete = produtoRepository.findAll().size();

        // Get the produto
        restProdutoMockMvc.perform(delete("/api/produtos/{id}", produto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean produtoExistsInEs = produtoSearchRepository.exists(produto.getId());
        assertThat(produtoExistsInEs).isFalse();

        // Validate the database is empty
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);
        produtoSearchRepository.save(produto);

        // Search the produto
        restProdutoMockMvc.perform(get("/api/_search/produtos?query=id:" + produto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produto.class);
        Produto produto1 = new Produto();
        produto1.setId(1L);
        Produto produto2 = new Produto();
        produto2.setId(produto1.getId());
        assertThat(produto1).isEqualTo(produto2);
        produto2.setId(2L);
        assertThat(produto1).isNotEqualTo(produto2);
        produto1.setId(null);
        assertThat(produto1).isNotEqualTo(produto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdutoDTO.class);
        ProdutoDTO produtoDTO1 = new ProdutoDTO();
        produtoDTO1.setId(1L);
        ProdutoDTO produtoDTO2 = new ProdutoDTO();
        assertThat(produtoDTO1).isNotEqualTo(produtoDTO2);
        produtoDTO2.setId(produtoDTO1.getId());
        assertThat(produtoDTO1).isEqualTo(produtoDTO2);
        produtoDTO2.setId(2L);
        assertThat(produtoDTO1).isNotEqualTo(produtoDTO2);
        produtoDTO1.setId(null);
        assertThat(produtoDTO1).isNotEqualTo(produtoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(produtoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(produtoMapper.fromId(null)).isNull();
    }
}
