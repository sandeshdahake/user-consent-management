package com.user.consent.management.repository;

import com.user.consent.management.domain.Preference;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Preference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreferenceRepository extends MongoRepository<Preference, String> {
}
