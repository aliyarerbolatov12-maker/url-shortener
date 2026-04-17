package com.linkcut.shortener.modules.urls.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SequenceService {

    private final EntityManager em;

    public long getNextSequenceValue(String sequenceName) {
        return ((Number) em.createNativeQuery("SELECT nextval(:seqName)")
                .setParameter("seqName", sequenceName)
                .getSingleResult())
                .longValue();
    }

    public long getNextShortKeySequence() {
        return getNextSequenceValue("short_key_sequence");
    }
}