package com.appdora.repository;

import com.appdora.domain.Utilizador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Utilizador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UtilizadorRepository extends JpaRepository<Utilizador, Long> {

    @Query(value = "select distinct utilizador from Utilizador utilizador left join fetch utilizador.portals",
        countQuery = "select count(distinct utilizador) from Utilizador utilizador")
    Page<Utilizador> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct utilizador from Utilizador utilizador left join fetch utilizador.portals")
    List<Utilizador> findAllWithEagerRelationships();

    @Query("select utilizador from Utilizador utilizador left join fetch utilizador.portals where utilizador.id =:id")
    Optional<Utilizador> findOneWithEagerRelationships(@Param("id") Long id);

}
