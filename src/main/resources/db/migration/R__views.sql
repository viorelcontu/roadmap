CREATE OR REPLACE VIEW user_entitlements AS
SELECT u.user_id,
       u.user_name AS user_name,
       group_id,
       g.group_name AS group_name,
       gp.permission_id,
       gp.permission_name

FROM users u
         INNER JOIN groups g USING (group_id)
         INNER JOIN groups_permissions gp USING (group_id)