package com.user.consent.management.service.impl;

import com.user.consent.management.service.PreferenceService;
import com.user.consent.management.domain.Preference;
import com.user.consent.management.repository.PreferenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Preference}.
 */
@Service
public class PreferenceServiceImpl implements PreferenceService {

    private final Logger log = LoggerFactory.getLogger(PreferenceServiceImpl.class);

    private final PreferenceRepository preferenceRepository;

    public PreferenceServiceImpl(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    /**
     * Save a preference.
     *
     * @param preference the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Preference save(Preference preference) {
        log.debug("Request to save Preference : {}", preference);
        return preferenceRepository.save(preference);
    }

    /**
     * Get all the preferences.
     *
     * @return the list of entities.
     */
    @Override
    public List<Preference> findAll() {
        log.debug("Request to get all Preferences");
        return preferenceRepository.findAll();
    }


    /**
     * Get one preference by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Preference> findOne(String id) {
        log.debug("Request to get Preference : {}", id);
        return preferenceRepository.findById(id);
    }

    /**
     * Delete the preference by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Preference : {}", id);

        preferenceRepository.deleteById(id);
    }
}
