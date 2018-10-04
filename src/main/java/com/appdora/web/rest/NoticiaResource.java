package com.appdora.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.appdora.domain.Noticia;
import com.appdora.service.NoticiaService;
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
 * REST controller for managing Noticia.
 */
@RestController
@RequestMapping("/api")
public class NoticiaResource {

    private final Logger log = LoggerFactory.getLogger(NoticiaResource.class);

    private static final String ENTITY_NAME = "noticia";

    private final NoticiaService noticiaService;

    public NoticiaResource(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }

    /**
     * POST  /noticias : Create a new noticia.
     *
     * @param noticia the noticia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new noticia, or with status 400 (Bad Request) if the noticia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/noticias")
    @Timed
    public ResponseEntity<Noticia> createNoticia(@Valid @RequestBody Noticia noticia) throws URISyntaxException {
        log.debug("REST request to save Noticia : {}", noticia);
        if (noticia.getId() != null) {
            throw new BadRequestAlertException("A new noticia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Noticia result = noticiaService.save(noticia);
        return ResponseEntity.created(new URI("/api/noticias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /noticias : Updates an existing noticia.
     *
     * @param noticia the noticia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated noticia,
     * or with status 400 (Bad Request) if the noticia is not valid,
     * or with status 500 (Internal Server Error) if the noticia couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/noticias")
    @Timed
    public ResponseEntity<Noticia> updateNoticia(@Valid @RequestBody Noticia noticia) throws URISyntaxException {
        log.debug("REST request to update Noticia : {}", noticia);
        if (noticia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Noticia result = noticiaService.save(noticia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, noticia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /noticias : get all the noticias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of noticias in body
     */
    @GetMapping("/noticias")
    @Timed
    public ResponseEntity<List<Noticia>> getAllNoticias(Pageable pageable) {
        log.debug("REST request to get a page of Noticias");
        Page<Noticia> page = noticiaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/noticias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /noticias/:id : get the "id" noticia.
     *
     * @param id the id of the noticia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the noticia, or with status 404 (Not Found)
     */
    @GetMapping("/noticias/{id}")
    @Timed
    public ResponseEntity<Noticia> getNoticia(@PathVariable Long id) {
        log.debug("REST request to get Noticia : {}", id);
        Optional<Noticia> noticia = noticiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noticia);
    }

    /**
     * DELETE  /noticias/:id : delete the "id" noticia.
     *
     * @param id the id of the noticia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/noticias/{id}")
    @Timed
    public ResponseEntity<Void> deleteNoticia(@PathVariable Long id) {
        log.debug("REST request to delete Noticia : {}", id);
        noticiaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/noticias?query=:query : search for the noticia corresponding
     * to the query.
     *
     * @param query the query of the noticia search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/noticias")
    @Timed
    public ResponseEntity<List<Noticia>> searchNoticias(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Noticias for query {}", query);
        Page<Noticia> page = noticiaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/noticias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
