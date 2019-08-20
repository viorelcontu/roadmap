--Roles
INSERT INTO groups (group_id, group_name) VALUES (9, 'ADMIN');
INSERT INTO groups (group_id, group_name) VALUES (7, 'MANAGER');
INSERT INTO groups (group_id, group_name) VALUES (5, 'ACCOUNTANT');
INSERT INTO groups (group_id, group_name) VALUES (3, 'CLIENT');

--Permissions

-- admin privileges: root, onboarding, accounting, audit, services
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (9, 8);
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (9, 6);
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (9, 5);
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (9, 3);
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (9, 0);

-- manager privileges: onboarding, accounting, audit, services
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (7, 6);
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (7, 4);
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (7, 2);
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (7, 0);

-- accountant privileges: accounting, audit
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (5, 4);
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (5, 2);

-- client privileges: services
INSERT INTO groups_permissions (GROUP_ID, permission_id) VALUES (3, 0);