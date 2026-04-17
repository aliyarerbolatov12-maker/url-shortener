CREATE SEQUENCE link_sequence START WITH 1 INCREMENT BY 1;
create sequence short_key_sequence start with 1 increment by 1;

CREATE TABLE links (
    id BIGINT PRIMARY KEY DEFAULT nextval('link_sequence'),
    long_url VARCHAR(255) NOT NULL,
    short_key VARCHAR(50) NOT NULL UNIQUE,
    expires_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX idx_links_expires_at ON links (expires_at);