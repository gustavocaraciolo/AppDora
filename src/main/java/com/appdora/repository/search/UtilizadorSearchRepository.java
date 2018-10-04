package com.appdora.repository.search;

import com.appdora.domain.Utilizador;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Utilizador entity.
 */
public interface UtilizadorSearchRepository extends ElasticsearchRepository<Utilizador, Long> {
}
