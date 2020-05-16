CREATE TABLE product
(
    id           varchar(255) PRIMARY KEY,
    name         varchar(255),
    ean          varchar(255),
    creationTime timestamp,
    updateTime   timestamp
);


CREATE TABLE shop
(
    id   varchar(255),
    name varchar(255)
);

CREATE TABLE shop_product
(
    shop_id    varchar(255),
    product_id varchar(255),
    price      decimal,
    PRIMARY KEY (shop_id, product_id)
);
