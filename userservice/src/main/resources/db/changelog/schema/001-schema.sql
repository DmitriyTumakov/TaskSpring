CREATE TABLE public.Users (
    id       SERIAL  PRIMARY KEY,
    name     VARCHAR NOT NULL,
    email    VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

select
    u.email,
    u."id",
    u."name",
    u."password"
from
    users u;

