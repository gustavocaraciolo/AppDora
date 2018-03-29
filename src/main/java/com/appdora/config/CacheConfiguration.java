package com.appdora.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.appdora.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.appdora.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.appdora.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.appdora.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.appdora.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.appdora.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.appdora.domain.Checkout.class.getName(), jcacheConfiguration);
            cm.createCache(com.appdora.domain.Checkout.class.getName() + ".produtos", jcacheConfiguration);
            cm.createCache(com.appdora.domain.Produto.class.getName(), jcacheConfiguration);
            cm.createCache(com.appdora.domain.Produto.class.getName() + ".checkouts", jcacheConfiguration);
            cm.createCache(com.appdora.domain.Categoria.class.getName(), jcacheConfiguration);
            cm.createCache(com.appdora.domain.Cliente.class.getName(), jcacheConfiguration);
            cm.createCache(com.appdora.domain.Tag.class.getName(), jcacheConfiguration);
            cm.createCache(com.appdora.domain.Tag.class.getName() + ".clientes", jcacheConfiguration);
            cm.createCache(com.appdora.domain.Categoria.class.getName() + ".produtos", jcacheConfiguration);
            cm.createCache(com.appdora.domain.Cliente.class.getName() + ".checkouts", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
