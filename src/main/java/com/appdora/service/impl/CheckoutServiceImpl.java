package com.appdora.service.impl;

import com.appdora.service.CheckoutService;
import com.appdora.domain.Checkout;
import com.appdora.repository.CheckoutRepository;
import com.appdora.repository.search.CheckoutSearchRepository;
import com.appdora.service.dto.CheckoutDTO;
import com.appdora.service.mapper.CheckoutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Checkout.
 */
@Service
@Transactional
public class CheckoutServiceImpl implements CheckoutService {

    private final Logger log = LoggerFactory.getLogger(CheckoutServiceImpl.class);

    private final CheckoutRepository checkoutRepository;

    private final CheckoutMapper checkoutMapper;

    private final CheckoutSearchRepository checkoutSearchRepository;

    public CheckoutServiceImpl(CheckoutRepository checkoutRepository, CheckoutMapper checkoutMapper, CheckoutSearchRepository checkoutSearchRepository) {
        this.checkoutRepository = checkoutRepository;
        this.checkoutMapper = checkoutMapper;
        this.checkoutSearchRepository = checkoutSearchRepository;
    }

    /**
     * Save a checkout.
     *
     * @param checkoutDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CheckoutDTO save(CheckoutDTO checkoutDTO) {
        log.debug("Request to save Checkout : {}", checkoutDTO);
        Checkout checkout = checkoutMapper.toEntity(checkoutDTO);
        checkout = checkoutRepository.save(checkout);
        CheckoutDTO result = checkoutMapper.toDto(checkout);
        checkoutSearchRepository.save(checkout);
        return result;
    }

    /**
     * Get all the checkouts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CheckoutDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Checkouts");
        return checkoutRepository.findAll(pageable)
            .map(checkoutMapper::toDto);
    }

    /**
     * Get one checkout by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CheckoutDTO findOne(Long id) {
        log.debug("Request to get Checkout : {}", id);
        Checkout checkout = checkoutRepository.findOneWithEagerRelationships(id);
        return checkoutMapper.toDto(checkout);
    }

    /**
     * Delete the checkout by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Checkout : {}", id);
        checkoutRepository.delete(id);
        checkoutSearchRepository.delete(id);
    }

    /**
     * Search for the checkout corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CheckoutDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Checkouts for query {}", query);
        Page<Checkout> result = checkoutSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(checkoutMapper::toDto);
    }
}
