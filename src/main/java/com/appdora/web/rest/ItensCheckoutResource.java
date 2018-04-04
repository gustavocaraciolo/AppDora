package com.appdora.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.appdora.service.ItensCheckoutService;
import com.appdora.web.rest.errors.BadRequestAlertException;
import com.appdora.web.rest.util.HeaderUtil;
import com.appdora.web.rest.util.PaginationUtil;
import com.appdora.service.dto.ItensCheckoutDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ItensCheckout.
 */
@RestController
@RequestMapping("/api")
public class ItensCheckoutResource {

    private final Logger log = LoggerFactory.getLogger(ItensCheckoutResource.class);

    private static final String ENTITY_NAME = "itensCheckout";

    private final ItensCheckoutService itensCheckoutService;

    public ItensCheckoutResource(ItensCheckoutService itensCheckoutService) {
        this.itensCheckoutService = itensCheckoutService;
    }

    /**
     * POST  /itens-checkouts : Create a new itensCheckout.
     *
     * @param itensCheckoutDTO the itensCheckoutDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itensCheckoutDTO, or with status 400 (Bad Request) if the itensCheckout has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/itens-checkouts")
    @Timed
    public ResponseEntity<ItensCheckoutDTO> createItensCheckout(@RequestBody ItensCheckoutDTO itensCheckoutDTO) throws URISyntaxException {
        log.debug("REST request to save ItensCheckout : {}", itensCheckoutDTO);
        if (itensCheckoutDTO.getId() != null) {
            throw new BadRequestAlertException("A new itensCheckout cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItensCheckoutDTO result = itensCheckoutService.save(itensCheckoutDTO);
        return ResponseEntity.created(new URI("/api/itens-checkouts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /itens-checkouts : Updates an existing itensCheckout.
     *
     * @param itensCheckoutDTO the itensCheckoutDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itensCheckoutDTO,
     * or with status 400 (Bad Request) if the itensCheckoutDTO is not valid,
     * or with status 500 (Internal Server Error) if the itensCheckoutDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/itens-checkouts")
    @Timed
    public ResponseEntity<ItensCheckoutDTO> updateItensCheckout(@RequestBody ItensCheckoutDTO itensCheckoutDTO) throws URISyntaxException {
        log.debug("REST request to update ItensCheckout : {}", itensCheckoutDTO);
        if (itensCheckoutDTO.getId() == null) {
            return createItensCheckout(itensCheckoutDTO);
        }
        ItensCheckoutDTO result = itensCheckoutService.save(itensCheckoutDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itensCheckoutDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /itens-checkouts : get all the itensCheckouts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of itensCheckouts in body
     */
    @GetMapping("/itens-checkouts")
    @Timed
    public ResponseEntity<List<ItensCheckoutDTO>> getAllItensCheckouts(Pageable pageable) {
        log.debug("REST request to get a page of ItensCheckouts");
        Page<ItensCheckoutDTO> page = itensCheckoutService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/itens-checkouts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /itens-checkouts/:id : get the "id" itensCheckout.
     *
     * @param id the id of the itensCheckoutDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itensCheckoutDTO, or with status 404 (Not Found)
     */
    @GetMapping("/itens-checkouts/{id}")
    @Timed
    public ResponseEntity<ItensCheckoutDTO> getItensCheckout(@PathVariable Long id) {
        log.debug("REST request to get ItensCheckout : {}", id);
        ItensCheckoutDTO itensCheckoutDTO = itensCheckoutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(itensCheckoutDTO));
    }

    /**
     * DELETE  /itens-checkouts/:id : delete the "id" itensCheckout.
     *
     * @param id the id of the itensCheckoutDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/itens-checkouts/{id}")
    @Timed
    public ResponseEntity<Void> deleteItensCheckout(@PathVariable Long id) {
        log.debug("REST request to delete ItensCheckout : {}", id);
        itensCheckoutService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/itens-checkouts?query=:query : search for the itensCheckout corresponding
     * to the query.
     *
     * @param query the query of the itensCheckout search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/itens-checkouts")
    @Timed
    public ResponseEntity<List<ItensCheckoutDTO>> searchItensCheckouts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ItensCheckouts for query {}", query);
        Page<ItensCheckoutDTO> page = itensCheckoutService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/itens-checkouts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
