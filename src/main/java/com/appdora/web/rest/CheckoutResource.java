package com.appdora.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.appdora.service.CheckoutService;
import com.appdora.web.rest.errors.BadRequestAlertException;
import com.appdora.web.rest.util.HeaderUtil;
import com.appdora.web.rest.util.PaginationUtil;
import com.appdora.service.dto.CheckoutDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Checkout.
 */
@RestController
@RequestMapping("/api")
public class CheckoutResource {

    private final Logger log = LoggerFactory.getLogger(CheckoutResource.class);

    private static final String ENTITY_NAME = "checkout";

    private final CheckoutService checkoutService;

    public CheckoutResource(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    /**
     * POST  /checkouts : Create a new checkout.
     *
     * @param checkoutDTO the checkoutDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new checkoutDTO, or with status 400 (Bad Request) if the checkout has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/checkouts")
    @Timed
    public ResponseEntity<CheckoutDTO> createCheckout(@Valid @RequestBody CheckoutDTO checkoutDTO) throws URISyntaxException {
        log.debug("REST request to save Checkout : {}", checkoutDTO);
        if (checkoutDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkout cannot already have an ID", ENTITY_NAME, "idexists");
        }
        checkoutDTO.setDataHora(ZonedDateTime.now());
        CheckoutDTO result = checkoutService.save(checkoutDTO);
        return ResponseEntity.created(new URI("/api/checkouts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /checkouts : Updates an existing checkout.
     *
     * @param checkoutDTO the checkoutDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated checkoutDTO,
     * or with status 400 (Bad Request) if the checkoutDTO is not valid,
     * or with status 500 (Internal Server Error) if the checkoutDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/checkouts")
    @Timed
    public ResponseEntity<CheckoutDTO> updateCheckout(@Valid @RequestBody CheckoutDTO checkoutDTO) throws URISyntaxException {
        log.debug("REST request to update Checkout : {}", checkoutDTO);
        if (checkoutDTO.getId() == null) {
            return createCheckout(checkoutDTO);
        }
        CheckoutDTO result = checkoutService.save(checkoutDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, checkoutDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /checkouts : get all the checkouts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of checkouts in body
     */
    @GetMapping("/checkouts")
    @Timed
    public ResponseEntity<List<CheckoutDTO>> getAllCheckouts(Pageable pageable) {
        log.debug("REST request to get a page of Checkouts");
        Page<CheckoutDTO> page = checkoutService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/checkouts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /checkouts/:id : get the "id" checkout.
     *
     * @param id the id of the checkoutDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the checkoutDTO, or with status 404 (Not Found)
     */
    @GetMapping("/checkouts/{id}")
    @Timed
    public ResponseEntity<CheckoutDTO> getCheckout(@PathVariable Long id) {
        log.debug("REST request to get Checkout : {}", id);
        CheckoutDTO checkoutDTO = checkoutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(checkoutDTO));
    }

    /**
     * DELETE  /checkouts/:id : delete the "id" checkout.
     *
     * @param id the id of the checkoutDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/checkouts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCheckout(@PathVariable Long id) {
        log.debug("REST request to delete Checkout : {}", id);
        checkoutService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/checkouts?query=:query : search for the checkout corresponding
     * to the query.
     *
     * @param query the query of the checkout search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/checkouts")
    @Timed
    public ResponseEntity<List<CheckoutDTO>> searchCheckouts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Checkouts for query {}", query);
        Page<CheckoutDTO> page = checkoutService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/checkouts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
