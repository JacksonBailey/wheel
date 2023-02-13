CREATE TABLE flag (
    name TEXT PRIMARY KEY NOT NULL,
    state INTEGER NOT NULL CONSTRAINT state_is_boolean CHECK (state IN (0, 1))
) STRICT;
