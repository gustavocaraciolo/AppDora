package com.appdora.repository;

import com.appdora.domain.Noticia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Noticia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

}
