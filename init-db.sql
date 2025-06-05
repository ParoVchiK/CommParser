CREATE TABLE public.players(
    id INTEGER PRIMARY KEY,
    votes_up INTEGER,
    comms_count INTEGER
);

CREATE TABLE public.comms(
    id INTEGER PRIMARY KEY,
    user_id INTEGER REFERENCES players(id) ON DELETE CASCADE,
    parent_id INTEGER,
    commentable_type TEXT,
    commentable_id INTEGER,
    msg_text TEXT,
    msg_date TIMESTAMP,
    votes INTEGER
);