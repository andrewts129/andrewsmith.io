-- Message schema

-- !Ups

CREATE TABLE Message (
    id INTEGER PRIMARY KEY,
    text TEXT NOT NULL,
    author TEXT NOT NULL,
    created INTEGER NOT NULL
);

-- !Downs

DROP TABLE Message;