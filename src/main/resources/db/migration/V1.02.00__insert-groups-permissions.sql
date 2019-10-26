INSERT INTO groups (group_id, group_name) VALUES (9, 'ADMIN');
INSERT INTO groups (group_id, group_name) VALUES (7, 'MANAGER');
INSERT INTO groups (group_id, group_name) VALUES (5, 'AUDIT');
INSERT INTO groups (group_id, group_name) VALUES (3, 'CLIENT');

ALTER TABLE groups READ ONLY;

--ADMIN 9
    -- OPERATOR_ADMIN(8),
    -- CLIENT_ADMIN(6),
    -- HISTORY(2),
    -- SERVICES(0);

--MANAGER 7
    -- CLIENT_ADMIN(6),
    -- HISTORY(2),
    -- SERVICES(0);

--AUDIT 5
    -- HISTORY(2);

--CLIENT 3
    -- SERVICES (0)

INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (9, 8);
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (9, 6);
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (9, 2);
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (9, 0);


INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (7, 6);
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (7, 2);
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (7, 0);


INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (5, 2);

-- client privileges: services
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (3, 0);

ALTER TABLE groups_permissions READ ONLY;