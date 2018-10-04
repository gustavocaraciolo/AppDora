package com.appdora.service;

import com.appdora.domain.Noticia;
import com.appdora.repository.NoticiaRepository;
import com.appdora.repository.search.NoticiaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Noticia.
 */
@Service
@Transactional
public class NoticiaService {

    private final Logger log = LoggerFactory.getLogger(NoticiaService.class);

    private final NoticiaRepository noticiaRepository;

    private final NoticiaSearchRepository noticiaSearchRepository;

    public NoticiaService(NoticiaRepository noticiaRepository, NoticiaSearchRepository noticiaSearchRepository) {
        this.noticiaRepository = noticiaRepository;
        this.noticiaSearchRepository = noticiaSearchRepository;
    }

    /**
     * Save a noticia.
     *
     * @param noticia the entity to save
     * @return the persisted entity
     */
    public Noticia save(Noticia noticia) {
        log.debug("Request to save Noticia : {}", noticia);        Noticia result = noticiaRepository.save(noticia);
        noticiaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the noticias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Noticia> findAll(Pageable pageable) {
        log.debug("Request to get all Noticias");
        return noticiaRepository.findAll(pageable);
    }


    /**
     * Get one noticia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Noticia> findOne(Long id) {
        log.debug("Request to get Noticia : {}", id);
        return noticiaRepository.findById(id);
    }

    /**
     * Delete the noticia by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Noticia : {}", id);
        noticiaRepository.deleteById(id);
        noticiaSearchRepository.deleteById(id);
    }

    /**
     * Search for the noticia corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Noticia> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Noticias for query {}", query);
        return noticiaSearchRepository.search(queryStringQuery(query), pageable);    }
}
