package com.appdora.service.impl;

import com.appdora.config.Constants;
import com.appdora.domain.Authority;
import com.appdora.domain.User;
import com.appdora.repository.AuthorityRepository;
import com.appdora.repository.UserRepository;
import com.appdora.security.AuthoritiesConstants;
import com.appdora.service.ClienteService;
import com.appdora.domain.Cliente;
import com.appdora.repository.ClienteRepository;
import com.appdora.repository.search.ClienteSearchRepository;
import com.appdora.service.dto.ClienteDTO;
import com.appdora.service.mapper.ClienteMapper;
import com.appdora.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Cliente.
 */
@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private final ClienteRepository clienteRepository;

    private final ClienteMapper clienteMapper;

    private final UserRepository userRepository;

    private final ClienteSearchRepository clienteSearchRepository;

    private final PasswordEncoder passwordEncoder;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper, UserRepository userRepository, ClienteSearchRepository clienteSearchRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.userRepository = userRepository;
        this.clienteSearchRepository = clienteSearchRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Save a cliente.
     *
     * @param clienteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClienteDTO save(ClienteDTO clienteDTO) {
        log.debug("Request to save Cliente : {}", clienteDTO);
        User user = saveUser(clienteDTO);
        clienteDTO.setUserId(user.getId());
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente = clienteRepository.save(cliente);
        ClienteDTO result = clienteMapper.toDto(cliente);
        clienteSearchRepository.save(cliente);
        return result;
    }

    private User saveUser(ClienteDTO clienteDTO) {
        User user = new User();
        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.ADMIN);
        authorities.add(authority);

        user.setLogin(clienteDTO.getEmail());
        user.setFirstName(clienteDTO.getName());
        user.setLastName(clienteDTO.getName());
        user.setEmail(clienteDTO.getEmail());
        user.setImageUrl("http://facebook.com/keith.donald");
        user.setAuthorities(authorities);
        if (user.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(user.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        user = userRepository.saveAndFlush(user);
        return user;
    }

    /**
     * Get all the clientes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clientes");
        return clienteRepository.findAll(pageable)
            .map(clienteMapper::toDto);
    }


    /**
     *  get all the clientes where Checkout is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ClienteDTO> findAllWhereCheckoutIsNull() {
        log.debug("Request to get all clientes where Checkout is null");
        return StreamSupport
            .stream(clienteRepository.findAll().spliterator(), false)
            .filter(cliente -> cliente.getCheckout() == null)
            .map(clienteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one cliente by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClienteDTO findOne(Long id) {
        log.debug("Request to get Cliente : {}", id);
        Cliente cliente = clienteRepository.findOne(id);
        return clienteMapper.toDto(cliente);
    }

    /**
     * Delete the cliente by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cliente : {}", id);
        clienteRepository.delete(id);
        clienteSearchRepository.delete(id);
    }

    /**
     * Search for the cliente corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClienteDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Clientes for query {}", query);
        Page<Cliente> result = clienteSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(clienteMapper::toDto);
    }
}
