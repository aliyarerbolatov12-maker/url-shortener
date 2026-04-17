package com.linkcut.shortener.modules.urls.repository;

import com.linkcut.shortener.modules.urls.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    boolean existsByShortKey(String shortKey);

    @Query("SELECT l.longUrl FROM Link l WHERE l.shortKey = :key")
    Optional<String> findLongUrlByShortKey(@Param("key") String key);

    int deleteByExpiresAtBefore(Instant time);
}