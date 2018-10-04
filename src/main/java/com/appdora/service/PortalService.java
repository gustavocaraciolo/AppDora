package com.appdora.service;

import com.appdora.domain.Portal;
import com.appdora.repository.PortalRepository;
import com.appdora.repository.search.PortalSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Portal.
 */
@Service
@Transactional
public class PortalService {

    private final Logger log = LoggerFactory.getLogger(PortalService.class);

    private final PortalRepository portalRepository;

    private final PortalSearchRepository portalSearchRepository;

    public PortalService(PortalRepository portalRepository, PortalSearchRepository portalSearchRepository) {
        this.portalRepository = portalRepository;
        this.portalSearchRepository = portalSearchRepository;
    }

    /**
     * Save a portal.
     *
     * @param portal the entity to save
     * @return the persisted entity
     */
    public Portal save(Portal portal) {
        log.debug("Request to save Portal : {}", portal);        Portal result = portalRepository.save(portal);
        portalSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the portals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Portal> findAll(Pageable pageable) {
        log.debug("Request to get all Portals");
        return portalRepository.findAll(pageable);
    }


    /**
     * Get one portal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Portal> findOne(Long id) {
        log.debug("Request to get Portal : {}", id);
        return portalRepository.findById(id);
    }

    /**
     * Delete the portal by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Portal : {}", id);
        portalRepository.deleteById(id);
        portalSearchRepository.deleteById(id);
    }

    /**
     * Search for the portal corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Portal> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Portals for query {}", query);
        return portalSearchRepository.search(queryStringQuery(query), pageable);    }
}
