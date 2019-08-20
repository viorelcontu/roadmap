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

-- root can add other privileged users
-- onboarding can add new clients
-- accounting can register payments
-- audit can see history of transactions
-- services can used payed services
CREATE TABLE groups_permissions
(
    group_id        NUMBER(1),
    permission_id   NUMBER(1),
    permission_name VARCHAR2(10) GENERATED ALWAYS AS (
                        decode(permission_id,
                               8, 'ROOT',
                               6, 'ONBOARDING',
                               4, 'ACCOUNTING',
                               2, 'AUDIT',
                               0, 'SERVICES')) VIRTUAL,
    CONSTRAINT pk_groups_permissions
        PRIMARY KEY (group_id, permission_id),
    CONSTRAINT ck_permission_enum CHECK ( permission_id IN (0, 2, 4, 6, 8))
);

ALTER TABLE users
    ADD
        CONSTRAINT fk_users_groups
            FOREIGN KEY (group_id) REFERENCES groups;

ALTER TABLE groups_permissions
    ADD
        CONSTRAINT fk_group_permissions_groups
            FOREIGN KEY (group_id) REFERENCES groups;