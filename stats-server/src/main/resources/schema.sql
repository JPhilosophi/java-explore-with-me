DROP TABLE IF EXISTS hits;

CREATE TABLE IF NOT EXISTS hits
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    app     VARCHAR(255),
    uri     VARCHAR(2000),
    ip      VARCHAR(255),
    created TIMESTAMP WITHOUT TIME ZONE
);