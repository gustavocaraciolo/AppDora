package com.appdora.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of NoticiaSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class NoticiaSearchRepositoryMockConfiguration {

    @MockBean
    private NoticiaSearchRepository mockNoticiaSearchRepository;

}
