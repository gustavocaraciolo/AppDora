package com.appdora.service;

import com.appdora.service.dto.CheckoutDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Checkout.
 */
public interface CheckoutService {

    /**
     * Save a checkout.
     *
     * @param checkoutDTO the entity to save
     * @return the persisted entity
     */
    CheckoutDTO save(CheckoutDTO checkoutDTO);

    /**
     * Get all the checkouts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CheckoutDTO> findAll(Pageable pageable);

    /**
     * Get the "id" checkout.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CheckoutDTO findOne(Long id);

    /**
     * Delete the "id" checkout.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the checkout corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CheckoutDTO> search(String query, Pageable pageable);
}
