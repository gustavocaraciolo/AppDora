package com.appdora.service.impl;

import com.appdora.service.ItensCheckoutService;
import com.appdora.domain.ItensCheckout;
import com.appdora.repository.ItensCheckoutRepository;
import com.appdora.repository.search.ItensCheckoutSearchRepository;
import com.appdora.service.dto.ItensCheckoutDTO;
import com.appdora.service.mapper.ItensCheckoutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ItensCheckout.
 */
@Service
@Transactional
public class ItensCheckoutServiceImpl implements ItensCheckoutService {

    private final Logger log = LoggerFactory.getLogger(ItensCheckoutServiceImpl.class);

    private final ItensCheckoutRepository itensCheckoutRepository;

    private final ItensCheckoutMapper itensCheckoutMapper;

    private final ItensCheckoutSearchRepository itensCheckoutSearchRepository;

    public ItensCheckoutServiceImpl(ItensCheckoutRepository itensCheckoutRepository, ItensCheckoutMapper itensCheckoutMapper, ItensCheckoutSearchRepository itensCheckoutSearchRepository) {
        this.itensCheckoutRepository = itensCheckoutRepository;
        this.itensCheckoutMapper = itensCheckoutMapper;
        this.itensCheckoutSearchRepository = itensCheckoutSearchRepository;
    }

    /**
     * Save a itensCheckout.
     *
     * @param itensCheckoutDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ItensCheckoutDTO save(ItensCheckoutDTO itensCheckoutDTO) {
        log.debug("Request to save ItensCheckout : {}", itensCheckoutDTO);
        ItensCheckout itensCheckout = itensCheckoutMapper.toEntity(itensCheckoutDTO);
        itensCheckout = itensCheckoutRepository.save(itensCheckout);
        ItensCheckoutDTO result = itensCheckoutMapper.toDto(itensCheckout);
        itensCheckoutSearchRepository.save(itensCheckout);
        return result;
    }

    /**
     * Get all the itensCheckouts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ItensCheckoutDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItensCheckouts");
        return itensCheckoutRepository.findAll(pageable)
            .map(itensCheckoutMapper::toDto);
    }

    /**
     * Get one itensCheckout by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ItensCheckoutDTO findOne(Long id) {
        log.debug("Request to get ItensCheckout : {}", id);
        ItensCheckout itensCheckout = itensCheckoutRepository.findOne(id);
        return itensCheckoutMapper.toDto(itensCheckout);
    }

    /**
     * Delete the itensCheckout by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItensCheckout : {}", id);
        itensCheckoutRepository.delete(id);
        itensCheckoutSearchRepository.delete(id);
    }

    /**
     * Search for the itensCheckout corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ItensCheckoutDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ItensCheckouts for query {}", query);
        Page<ItensCheckout> result = itensCheckoutSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(itensCheckoutMapper::toDto);
    }
}
