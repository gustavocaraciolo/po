package com.dora.repository;

import com.dora.domain.RequestItems;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RequestItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestItemsRepository extends JpaRepository<RequestItems, Long> {

}
