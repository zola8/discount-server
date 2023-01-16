DROP TABLE IF EXISTS discounts;

CREATE TABLE discounts(
    discount_id     IDENTITY PRIMARY KEY,
    discount_type   VARCHAR(20) NOT NULL,
    amount          NUMBER(19,2) NOT NULL DEFAULT 0,
    after_piece     INTEGER NOT NULL DEFAULT 1,
    valid_from      DATE NOT NULL,
    valid_until     DATE,

    PRIMARY KEY (discount_id)
);

ALTER TABLE products
ADD CONSTRAINT FK_products_discounts FOREIGN KEY (discount_id) REFERENCES discounts(discount_id);
