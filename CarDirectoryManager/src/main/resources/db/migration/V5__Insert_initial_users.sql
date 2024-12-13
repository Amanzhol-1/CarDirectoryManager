INSERT INTO users (username, password) VALUES
('admin', '$2b$12$.hRHpU2GHXQuOdffCnxQ7uXHk5oDHzorjat.dYCPjFURaO/6r39DG'), -- Пароль: admin123
('user', '$2b$12$TOuco4WLnNN7NEDftb5tsOgCGWgLuVOUgVuo/jQjzCfpI.ulMmrJG'); -- Пароль: user123


INSERT INTO user_roles (user_id, role) VALUES
(1, 'ADMIN'),
(2, 'USER');