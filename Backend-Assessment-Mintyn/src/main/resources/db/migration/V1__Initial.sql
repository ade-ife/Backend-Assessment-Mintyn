

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE token (
                             id BIGSERIAL PRIMARY KEY,
                             token VARCHAR(255) NOT NULL,
                             expired_time TIMESTAMP NOT NULL,
                             created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             CONSTRAINT token_store_token_key UNIQUE (token)
);


CREATE TABLE card (
                      id BIGSERIAL PRIMARY KEY,
                      scheme VARCHAR(255),
                      bin VARCHAR(255) UNIQUE,
                      type VARCHAR(255),
                      bank VARCHAR(255),
                      count BIGINT NOT NULL DEFAULT 0
);







