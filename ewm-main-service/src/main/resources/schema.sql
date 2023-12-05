DROP TABLE IF EXISTS compilation_events CASCADE;
DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS locations CASCADE;
DROP TABLE IF EXISTS compilations CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id serial PRIMARY KEY,
    email varchar NOT NULL UNIQUE,
    name  varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    id serial PRIMARY KEY,
    name varchar not null unique
);

CREATE TABLE IF NOT EXISTS compilations
(
    id serial PRIMARY KEY,
    title varchar NOT NULL,
    pinned boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS locations
(
    id  serial PRIMARY KEY,
    lat float NOT NULL,
    lon float NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    id serial PRIMARY KEY,
    annotation varchar NOT NULL,
    category_id bigint NOT NULL,
    created_on timestamp without time zone NOT NULL,
    description varchar NOT NULL,
    event_date timestamp without time zone NOT NULL,
    initiator_id bigint NOT NULL,
    location_id bigint NOT NULL,
    paid boolean NOT NULL,
    participant_limit integer NOT NULL,
    published_on timestamp without time zone,
    request_moderation boolean NOT NULL,
    state varchar NOT NULL,
    title varchar NOT NULL
);

create table if not exists ratings
(
    user_id bigint NOT NULL,
    event_id bigint NOT NULL,
    is_positive boolean NOT NULL,
    initiator_id bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS requests
(
    id serial PRIMARY KEY,
    event_id bigint NOT NULL,
    requester_id bigint NOT NULL,
    status varchar NOT NULL,
    created timestamp without time zone NOT NULL
);

CREATE TABLE IF NOT EXISTS compilation_events
(
    compilation_id bigint NOT NULL,
    event_id bigint NOT NULL,
    constraint compilation_events_pk
        primary key (compilation_id, event_id)
);

ALTER TABLE events ADD CONSTRAINT fk_events_categories FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT;
ALTER TABLE events ADD CONSTRAINT fk_events_users FOREIGN KEY (initiator_id) REFERENCES users (id) ON DELETE RESTRICT;
ALTER TABLE events ADD CONSTRAINT fk_events_locations FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE RESTRICT;
ALTER TABLE requests ADD CONSTRAINT fk_requests_users FOREIGN KEY (requester_id) REFERENCES users (id) ON DELETE RESTRICT;
ALTER TABLE requests ADD CONSTRAINT fk_requests_events FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE RESTRICT;
ALTER TABLE compilation_events ADD CONSTRAINT fk1_compilation_events FOREIGN KEY (compilation_id) REFERENCES compilations (id) ON DELETE CASCADE;
ALTER TABLE compilation_events ADD CONSTRAINT fk2_compilation_events FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE;
ALTER TABLE ratings ADD CONSTRAINT fk_ratings_users FOREIGN KEY (user_id) references users (id) ON DELETE CASCADE;
ALTER TABLE ratings ADD CONSTRAINT fk_ratings_events FOREIGN KEY (event_id) references events (id) ON DELETE CASCADE;

CREATE INDEX IF NOT EXISTS events_event_date_index on events (event_date);
