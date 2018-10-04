package com.appdora.repository.search;

import com.appdora.domain.Portal;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Portal entity.
 */
public interface PortalSearchRepository extends ElasticsearchRepository<Portal, Long> {
}
