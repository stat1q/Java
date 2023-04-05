DROP TABLE IF EXISTS product;
create table product
(
    id    INTEGER IDENTITY PRIMARY KEY NOT NULL,
    name  VARCHAR(30),
    price INTEGER
);