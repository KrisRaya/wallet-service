CREATE TABLE IF NOT EXISTS wallet
(
 id NUMERIC NOT NULL,
 name varchar(50) NOT NULL ,
 email varchar(50) DEFAULT NULL,
 phone_number varchar(20) NOT NULL,
 balance NUMERIC,
 CONSTRAINT wallet_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS merchant
(
 id NUMERIC NOT NULL,
 name varchar(50) NOT NULL ,
 balance NUMERIC,
 CONSTRAINT merchant_pkey PRIMARY KEY (id)
);

INSERT INTO wallet
    (id, name, email, phone_number, balance) VALUES
    (95, 'Kris', 'kris@mail.com', '081321111222', 500000),
    (96, 'Raya', 'raya@mail.com', '081321222111', 500000),
    (97, 'Ngurah', 'ngurah@mail.com', '081321333111', 500000);

INSERT INTO merchant
    (id, name, balance) VALUES
    (97, 'Tokopedia', 10000),
    (98, 'Bukalapak', 50000),
    (99, 'Shopee', 20000);