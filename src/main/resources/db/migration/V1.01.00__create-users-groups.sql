CREATE SEQUENCE sq_user_id MINVALUE 100 INCREMENT BY 1;

CREATE TABLE users
(
    user_id   NUMBER(6) DEFAULT sq_user_id.nextval
        CONSTRAINT pk_users PRIMARY KEY,
    token     RAW(16)   DEFAULT ON NULL sys_guid(),
    user_name VARCHAR2(32)        NOT NULL,
    nickname VARCHAR2(255),
    email     VARCHAR2(255),
    group_id  NUMBER(3)           NOT NULL,
    credits   NUMBER(6) DEFAULT 0 NOT NULL,
    active    NUMBER(1) DEFAULT 1 NOT NULL,
    CONSTRAINT uq_user_name UNIQUE (user_name),
    CONSTRAINT uq_user_token UNIQUE (token),
    CONSTRAINT ck_users_active_boolean CHECK ( active BETWEEN 0 AND 1),
    CONSTRAINT ck_users_valid_name CHECK (LENGTH(user_name) BETWEEN 4 AND 32),
    CONSTRAINT ck_users_credit_minimum CHECK ( credits >= 0 )
);

CREATE TABLE groups
(
    group_id   NUMBER(3),
    group_name VARCHAR2(15) NOT NULL,
    CONSTRAINT uq_group_name UNIQUE (group_name),
    CONSTRAINT pk_groups PRIMARY KEY (group_id)
);


-- OPERATOR_ADMIN(8), -> create/read/update/delete operators
-- CLIENT_ADMIN(6), -> create/read/update/delete clients
-- CREDIT_ADMIN(4), -> create/read/updated payments
-- HISTORY_AUDIT(2), -> read request history
-- SERVICE_USER(0); -> use payed services


CREATE TABLE groups_permissions
(
    group_id        NUMBER(1),
    permission_id   NUMBER(1),
    permission_name VARCHAR2(14) GENERATED ALWAYS AS (
                        decode(permission_id,
                               8, 'OPERATOR_ADMIN',
                               6, 'CLIENT_ADMIN',
                               2, 'HISTORY',
                               0, 'SERVICES')) VIRTUAL,
    CONSTRAINT pk_groups_permissions
        PRIMARY KEY (group_id, permission_id),
    CONSTRAINT ck_permission_enum CHECK ( permission_id IN (0, 2, 6, 8))
);

ALTER TABLE users
    ADD
        CONSTRAINT fk_users_groups
            FOREIGN KEY (group_id) REFERENCES groups;

ALTER TABLE groups_permissions
    ADD
        CONSTRAINT fk_group_permissions_groups
            FOREIGN KEY (group_id) REFERENCES groups;