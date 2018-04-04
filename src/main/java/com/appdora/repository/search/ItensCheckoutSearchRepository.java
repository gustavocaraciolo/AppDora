package com.appdora.repository.search;

import com.appdora.domain.ItensCheckout;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ItensCheckout entity.
 */
public interface ItensCheckoutSearchRepository extends ElasticsearchRepository<ItensCheckout, Long> {
}
