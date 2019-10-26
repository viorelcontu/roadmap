-- user data
-- Viorel Contu: Admin
INSERT INTO users (user_id, token, user_name, nickname, email, group_id, credits, active)
VALUES (1, 'c4d8135519dd4c45a7550d19f3ea402c', 'vcontu', 'Viorel Contu', 'viorel@mail.md', 9, 0, 1);

-- John Smith: Manager
INSERT INTO users (user_id, token, user_name, nickname, email, group_id, credits, active)
VALUES (2, '970f88cb29004530a4a8719ae882058c','asmith', 'Agent Smith', 'a.smith@mail.com', 7, 0, 1);

-- Luke Skywalker: Auditor
INSERT INTO users (user_id, token, user_name, nickname, email, group_id, credits, active)
VALUES (3, 'bd365f12b18a4608946ad63d456bc361','lsky', 'Luke Skywalker', 'l.skywalker@starwars.org', 5, 0 , 1);

-- Average Joe: User
INSERT INTO users (user_id, token, user_name, nickname, email, group_id, credits, active)
VALUES (4, '5f0909af46724824807d087730c7ec62', 'ajoe', 'Average Joe', 'average@users.net', 3, 0 ,1);