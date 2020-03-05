CREATE TABLE BogoStat (
    key TEXT PRIMARY KEY,
    value INTEGER NOT NULL
);

INSERT INTO BogoStat(key, value) VALUES ('numCompletions', 0);
