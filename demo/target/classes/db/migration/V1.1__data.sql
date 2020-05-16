INSERT INTO product(id, name, ean, creationTime, updateTime)
VALUES ('0', 'Arta subtila a nepasarii - Mark Manson', '9786067891096', '2017-11-05T00:00:00', '2017-11-05T00:00:00'),
       ('1', 'Unfu*k Yourself. Elibereaza-ti mintea si traieste-ti viata', '9786067891737', '2019-06-06T00:00:00',
        '2019-06-06T00:00:00'),
       ('2', 'Delia Owens - Acolo unde canta racii', '9786069782262', '2019-07-07T00:00:00', '2019-07-07T00:00:00');


INSERT INTO shop(id, name)
VALUES ('0', 'Headquarters'),
       ('1', 'Lipscani');

INSERT INTO shop_product(shop_id, product_id, price)
VALUES ('0', '0', 31.45),
       ('0', '1', 31.14),
       ('0', '2', 32.99),
       ('1', '0', 32.45),
       ('1', '1', 32.14),
       ('1', '2', 33.99);
