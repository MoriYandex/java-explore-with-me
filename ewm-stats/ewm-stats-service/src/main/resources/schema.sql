DROP TABLE IF EXISTS hits CASCADE;

CREATE TABLE hits (
    id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    app varchar NOT NULL,
    uri varchar NOT NULL,
    ip varchar NOT NULL,
    hit_date TIMESTAMP NOT NULL
);

COMMENT ON COLUMN hits.id IS 'ИД';

COMMENT ON COLUMN hits.app IS 'ИД сервиса';

COMMENT ON COLUMN hits.uri IS 'URI запроса';

COMMENT ON COLUMN hits.ip IS 'IP пользователя';

COMMENT ON COLUMN hits.hit_date IS 'Дата и время запроса';