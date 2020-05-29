package com.user.consent.management.repository;

import com.user.consent.management.domain.Validation;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Validation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValidationRepository extends MongoRepository<Validation, String> {
}
