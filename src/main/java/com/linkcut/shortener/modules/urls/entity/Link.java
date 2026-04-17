package com.linkcut.shortener.modules.urls.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(
        name = "links",
        indexes = {
                @Index(name = "idx_links_expires_at", columnList = "expires_at")
        }
)
@Getter
@Setter
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "link_seq")
    @SequenceGenerator(
            name = "link_seq",
            sequenceName = "link_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "long_url", nullable = false, length = 255)
    private String longUrl;

    @Column(name = "short_key", nullable = false, unique = true, length = 50)
    private String shortKey;

    @Column(name = "expires_at")
    private Instant expiresAt;
}