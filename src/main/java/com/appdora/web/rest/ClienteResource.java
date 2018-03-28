package com.appdora.web.rest;

import com.appdora.repository.UserRepository;
import com.appdora.service.MailService;
import com.appdora.web.rest.errors.EmailAlreadyUsedException;
import com.codahale.metrics.annotation.Timed;
import com.appdora.service.ClienteService;
import com.appdora.web.rest.errors.BadRequestAlertException;
import com.appdora.web.rest.util.HeaderUtil;
import com.appdora.web.rest.util.PaginationUtil;
import com.appdora.service.dto.ClienteDTO;
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
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Cliente.
 */
@RestController
@RequestMapping("/api")
public class ClienteResource {

    private final Logger log = LoggerFactory.getLogger(ClienteResource.class);

    private static final String ENTITY_NAME = "cliente";

    private final ClienteService clienteService;

    private final UserRepository userRepository;

    private final MailService mailService;

    public ClienteResource(ClienteService clienteService, UserRepository userRepository, MailService mailService) {
        this.clienteService = clienteService;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    /**
     * POST  /clientes : Create a new cliente.
     *
     * @param clienteDTO the clienteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clienteDTO, or with status 400 (Bad Request) if the cliente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clientes")
    @Timed
    public ResponseEntity<ClienteDTO> createCliente(@Valid @RequestBody ClienteDTO clienteDTO) throws URISyntaxException {
        log.debug("REST request to save Cliente : {}", clienteDTO);
        if (clienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new cliente cannot already have an ID", ENTITY_NAME, "idexists");
        } else if (userRepository.findOneByEmailIgnoreCase(clienteDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            ClienteDTO result = clienteService.save(clienteDTO);
            return ResponseEntity.created(new URI("/api/clientes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        }
    }

    /**
     * PUT  /clientes : Updates an existing cliente.
     *
     * @param clienteDTO the clienteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clienteDTO,
     * or with status 400 (Bad Request) if the clienteDTO is not valid,
     * or with status 500 (Internal Server Error) if the clienteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clientes")
    @Timed
    public ResponseEntity<ClienteDTO> updateCliente(@Valid @RequestBody ClienteDTO clienteDTO) throws URISyntaxException {
        log.debug("REST request to update Cliente : {}", clienteDTO);
        if (clienteDTO.getId() == null) {
            return createCliente(clienteDTO);
        }
        ClienteDTO result = clienteService.save(clienteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clientes : get all the clientes.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of clientes in body
     */
    @GetMapping("/clientes")
    @Timed
    public ResponseEntity<List<ClienteDTO>> getAllClientes(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("checkout-is-null".equals(filter)) {
            log.debug("REST request to get all Clientes where checkout is null");
            return new ResponseEntity<>(clienteService.findAllWhereCheckoutIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Clientes");
        Page<ClienteDTO> page = clienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clientes/:id : get the "id" cliente.
     *
     * @param id the id of the clienteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clienteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clientes/{id}")
    @Timed
    public ResponseEntity<ClienteDTO> getCliente(@PathVariable Long id) {
        log.debug("REST request to get Cliente : {}", id);
        ClienteDTO clienteDTO = clienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clienteDTO));
    }

    /**
     * DELETE  /clientes/:id : delete the "id" cliente.
     *
     * @param id the id of the clienteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clientes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        log.debug("REST request to delete Cliente : {}", id);
        clienteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/clientes?query=:query : search for the cliente corresponding
     * to the query.
     *
     * @param query the query of the cliente search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/clientes")
    @Timed
    public ResponseEntity<List<ClienteDTO>> searchClientes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Clientes for query {}", query);
        Page<ClienteDTO> page = clienteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/clientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
