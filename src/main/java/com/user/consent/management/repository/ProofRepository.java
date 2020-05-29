package com.user.consent.management.repository;

import com.user.consent.management.domain.Proof;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Proof entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProofRepository extends MongoRepository<Proof, String> {
}
