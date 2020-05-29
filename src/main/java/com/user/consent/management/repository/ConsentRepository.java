package com.user.consent.management.repository;

import com.user.consent.management.domain.Consent;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Consent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsentRepository extends MongoRepository<Consent, String> {
}
