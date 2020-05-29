package com.user.consent.management.repository;

import com.user.consent.management.domain.Delegate;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Delegate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DelegateRepository extends MongoRepository<Delegate, String> {
}
