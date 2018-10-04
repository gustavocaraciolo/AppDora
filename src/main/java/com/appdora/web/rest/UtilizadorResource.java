package com.appdora.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.appdora.domain.Utilizador;
import com.appdora.service.UtilizadorService;
import com.appdora.web.rest.errors.BadRequestAlertException;
import com.appdora.web.rest.util.HeaderUtil;
import com.appdora.web.rest.util.PaginationUtil;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Utilizador.
 */
@RestController
@RequestMapping("/api")
public class UtilizadorResource {

    private final Logger log = LoggerFactory.getLogger(UtilizadorResource.class);

    private static final String ENTITY_NAME = "utilizador";

    private final UtilizadorService utilizadorService;

    public UtilizadorResource(UtilizadorService utilizadorService) {
        this.utilizadorService = utilizadorService;
    }

    /**
     * POST  /utilizadors : Create a new utilizador.
     *
     * @param utilizador the utilizador to create
     * @return the ResponseEntity with status 201 (Created) and with body the new utilizador, or with status 400 (Bad Request) if the utilizador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/utilizadors")
    @Timed
    public ResponseEntity<Utilizador> createUtilizador(@Valid @RequestBody Utilizador utilizador) throws URISyntaxException {
        log.debug("REST request to save Utilizador : {}", utilizador);
        if (utilizador.getId() != null) {
            throw new BadRequestAlertException("A new utilizador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Utilizador result = utilizadorService.save(utilizador);
        return ResponseEntity.created(new URI("/api/utilizadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /utilizadors : Updates an existing utilizador.
     *
     * @param utilizador the utilizador to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated utilizador,
     * or with status 400 (Bad Request) if the utilizador is not valid,
     * or with status 500 (Internal Server Error) if the utilizador couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/utilizadors")
    @Timed
    public ResponseEntity<Utilizador> updateUtilizador(@Valid @RequestBody Utilizador utilizador) throws URISyntaxException {
        log.debug("REST request to update Utilizador : {}", utilizador);
        if (utilizador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Utilizador result = utilizadorService.save(utilizador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, utilizador.getId().toString()))
            .body(result);
    }

    /**
     * GET  /utilizadors : get all the utilizadors.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of utilizadors in body
     */
    @GetMapping("/utilizadors")
    @Timed
    public ResponseEntity<List<Utilizador>> getAllUtilizadors(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Utilizadors");
        Page<Utilizador> page;
        if (eagerload) {
            page = utilizadorService.findAllWithEagerRelationships(pageable);
        } else {
            page = utilizadorService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/utilizadors?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /utilizadors/:id : get the "id" utilizador.
     *
     * @param id the id of the utilizador to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the utilizador, or with status 404 (Not Found)
     */
    @GetMapping("/utilizadors/{id}")
    @Timed
    public ResponseEntity<Utilizador> getUtilizador(@PathVariable Long id) {
        log.debug("REST request to get Utilizador : {}", id);
        Optional<Utilizador> utilizador = utilizadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(utilizador);
    }

    /**
     * DELETE  /utilizadors/:id : delete the "id" utilizador.
     *
     * @param id the id of the utilizador to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/utilizadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteUtilizador(@PathVariable Long id) {
        log.debug("REST request to delete Utilizador : {}", id);
        utilizadorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/utilizadors?query=:query : search for the utilizador corresponding
     * to the query.
     *
     * @param query the query of the utilizador search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/utilizadors")
    @Timed
    public ResponseEntity<List<Utilizador>> searchUtilizadors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Utilizadors for query {}", query);
        Page<Utilizador> page = utilizadorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/utilizadors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
