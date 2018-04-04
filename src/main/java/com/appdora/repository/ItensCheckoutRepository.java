package com.appdora.repository;

import com.appdora.domain.ItensCheckout;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ItensCheckout entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItensCheckoutRepository extends JpaRepository<ItensCheckout, Long> {

}
