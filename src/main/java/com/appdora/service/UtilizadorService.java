package com.appdora.service;

import com.appdora.domain.Utilizador;
import com.appdora.repository.UtilizadorRepository;
import com.appdora.repository.search.UtilizadorSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Utilizador.
 */
@Service
@Transactional
public class UtilizadorService {

    private final Logger log = LoggerFactory.getLogger(UtilizadorService.class);

    private final UtilizadorRepository utilizadorRepository;

    private final UtilizadorSearchRepository utilizadorSearchRepository;

    public UtilizadorService(UtilizadorRepository utilizadorRepository, UtilizadorSearchRepository utilizadorSearchRepository) {
        this.utilizadorRepository = utilizadorRepository;
        this.utilizadorSearchRepository = utilizadorSearchRepository;
    }

    /**
     * Save a utilizador.
     *
     * @param utilizador the entity to save
     * @return the persisted entity
     */
    public Utilizador save(Utilizador utilizador) {
        log.debug("Request to save Utilizador : {}", utilizador);        Utilizador result = utilizadorRepository.save(utilizador);
        utilizadorSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the utilizadors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Utilizador> findAll(Pageable pageable) {
        log.debug("Request to get all Utilizadors");
        return utilizadorRepository.findAll(pageable);
    }

    /**
     * Get all the Utilizador with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Utilizador> findAllWithEagerRelationships(Pageable pageable) {
        return utilizadorRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one utilizador by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Utilizador> findOne(Long id) {
        log.debug("Request to get Utilizador : {}", id);
        return utilizadorRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the utilizador by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Utilizador : {}", id);
        utilizadorRepository.deleteById(id);
        utilizadorSearchRepository.deleteById(id);
    }

    /**
     * Search for the utilizador corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Utilizador> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Utilizadors for query {}", query);
        return utilizadorSearchRepository.search(queryStringQuery(query), pageable);    }
}
