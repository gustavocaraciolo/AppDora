package com.appdora.service;

import com.appdora.service.dto.CategoriaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Categoria.
 */
public interface CategoriaService {

    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save
     * @return the persisted entity
     */
    CategoriaDTO save(CategoriaDTO categoriaDTO);

    /**
     * Get all the categorias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CategoriaDTO> findAll(Pageable pageable);
    /**
     * Get all the CategoriaDTO where Produto is null.
     *
     * @return the list of entities
     */
    List<CategoriaDTO> findAllWhereProdutoIsNull();

    /**
     * Get the "id" categoria.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CategoriaDTO findOne(Long id);

    /**
     * Delete the "id" categoria.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the categoria corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CategoriaDTO> search(String query, Pageable pageable);
}
