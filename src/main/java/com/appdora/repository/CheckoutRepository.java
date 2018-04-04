package com.appdora.repository;

import com.appdora.domain.Checkout;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Checkout entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    @Query("select distinct checkout from Checkout checkout left join fetch checkout.itensCheckouts")
    List<Checkout> findAllWithEagerRelationships();

    @Query("select checkout from Checkout checkout left join fetch checkout.itensCheckouts where checkout.id =:id")
    Checkout findOneWithEagerRelationships(@Param("id") Long id);

}
