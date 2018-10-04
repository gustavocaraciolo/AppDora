package com.appdora.repository.search;

import com.appdora.domain.Noticia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Noticia entity.
 */
public interface NoticiaSearchRepository extends ElasticsearchRepository<Noticia, Long> {
}
