CREATE TABLE products
(
    id              SERIAL PRIMARY KEY,
    productId       VARCHAR(100)  NOT NULL,
    pricePerItem    NUMERIC(6, 2) NOT NULL,
    quantity        INT           NOT NULL
);

INSERT INTO products (productId, pricePerItem, quantity)
VALUES ('apple', 1.90, 13);