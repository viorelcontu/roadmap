INSERT INTO groups (group_id, group_name) VALUES (9, 'ADMIN');
INSERT INTO groups (group_id, group_name) VALUES (7, 'MANAGER');
INSERT INTO groups (group_id, group_name) VALUES (5, 'AUDIT');
INSERT INTO groups (group_id, group_name) VALUES (3, 'CLIENT');

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

-- user data

INSERT INTO users (user_id, token, user_name, nickname, email, group_id, credits, active)
VALUES (1, 'c4d8135519dd4c45a7550d19f3ea402c', 'vcontu', 'Viorel Contu', 'viorel@mail.md', 9, 0, 1);

-- John Smith: Manager
INSERT INTO users (user_id, token, user_name, nickname, email, group_id, credits, active)
VALUES (2, '970f88cb29004530a4a8719ae882058c','asmith', 'Agent Smith', 'a.smith@mail.com', 7, 0, 1);

-- Luke Skywalker: Accountant
INSERT INTO users (user_id, token, user_name, nickname, email, group_id, credits, active)
VALUES (3, 'bd365f12b18a4608946ad63d456bc361','lsky', 'Luke Skywalker', 'l.skywalker@starwars.org', 5, 0 , 1);

-- Average Joe: User
INSERT INTO users (user_id, token, user_name, nickname, email, group_id, credits, active)
VALUES (4, '5f0909af46724824807d087730c7ec62', 'ajoe', 'Average Joe', 'average@users.net', 3, 0 ,1);

INSERT INTO users (user_id, token, user_name, nickname, email, group_id, credits, active)
VALUES (5, '213e9d1b143644b981b30a3348ae2332', 'inactive', 'Inactive Bruce', 'inactive@users.net', 3, 0 ,1);