-- Pablo schemas

-- !Ups

CREATE TABLE messages(
    id BIGSERIAL PRIMARY KEY,
    text TEXT NOT NULL CHECK ( char_length(text) <= 250 ),
    createdAt TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE stats(
    key TEXT PRIMARY KEY NOT NULL,
    value BIGINT
);

INSERT INTO stats (key, value) VALUES ('totalMessages', 0);

-- !Downs

DROP TABLE messages;
DROP TABLE stats;