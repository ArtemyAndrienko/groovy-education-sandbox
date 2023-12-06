CREATE TABLE IF NOT EXISTS todo
(
    id        SERIAL PRIMARY KEY NOT NULL,
    title     TEXT               NOT NULL,
    completed BOOLEAN            NOT NULL
);
