package com.appdora.repository.search;

import com.appdora.domain.Checkout;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Checkout entity.
 */
public interface CheckoutSearchRepository extends ElasticsearchRepository<Checkout, Long> {
}
