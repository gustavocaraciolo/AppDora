package com.appdora.service;

import com.appdora.service.dto.ItensCheckoutDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ItensCheckout.
 */
public interface ItensCheckoutService {

    /**
     * Save a itensCheckout.
     *
     * @param itensCheckoutDTO the entity to save
     * @return the persisted entity
     */
    ItensCheckoutDTO save(ItensCheckoutDTO itensCheckoutDTO);

    /**
     * Get all the itensCheckouts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ItensCheckoutDTO> findAll(Pageable pageable);

    /**
     * Get the "id" itensCheckout.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ItensCheckoutDTO findOne(Long id);

    /**
     * Delete the "id" itensCheckout.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the itensCheckout corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ItensCheckoutDTO> search(String query, Pageable pageable);
}
