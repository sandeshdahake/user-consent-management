package com.user.consent.management.repository;

import com.user.consent.management.domain.Purpose;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Purpose entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurposeRepository extends MongoRepository<Purpose, String> {
}
