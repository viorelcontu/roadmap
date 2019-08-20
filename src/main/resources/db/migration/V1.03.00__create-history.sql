CREATE TABLE credit_history
(
    payment_id    NUMBER(10) GENERATED ALWAYS AS IDENTITY,
    payment_date  TIMESTAMP NOT NULL,
    client_id     NUMBER(6) NOT NULL,
    accountant_id NUMBER(6) NOT NULL,
    amount        NUMBER(6) NOT NULL,
    CONSTRAINT credit_history_pk PRIMARY KEY (payment_id),
    CONSTRAINT amount_min_zero CHECK (amount >= 0 ),
    CONSTRAINT credit_history_client_id_fk
        FOREIGN KEY (client_id) REFERENCES users (user_id),
    CONSTRAINT credit_history_accountant_id_fk
        FOREIGN KEY (accountant_id) REFERENCES users (user_id)
);

CREATE INDEX credit_history_client ON credit_history (client_id);

CREATE TABLE request_history
(
    request_id       NUMBER(15) GENERATED ALWAYS AS IDENTITY,
    request_date     TIMESTAMP           NOT NULL,
    client_id        NUMBER(6)           NOT NULL,
    service_cost     NUMBER(2) DEFAULT 1 NOT NULL CHECK ( service_cost >= 0),
    service_type     VARCHAR2(7) GENERATED ALWAYS AS ( DECODE(service_cost, 0, 'free', 'premium')) VIRTUAL,
    request_endpoint VARCHAR2(255)       NOT NULL,
    CONSTRAINT service_history_pk PRIMARY KEY (request_id),
    FOREIGN KEY (client_id) REFERENCES users (user_id)
);

CREATE INDEX service_history_client ON request_history (client_id);