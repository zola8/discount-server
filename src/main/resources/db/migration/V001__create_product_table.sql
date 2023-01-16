DROP TABLE IF EXISTS products;

CREATE TABLE products(
    product_id      IDENTITY PRIMARY KEY,
    code            VARCHAR(10) NOT NULL UNIQUE,
    description     VARCHAR(255),
    price           NUMBER(19,2),
    discount_id     BIGINT,

    PRIMARY KEY (product_id)
);
