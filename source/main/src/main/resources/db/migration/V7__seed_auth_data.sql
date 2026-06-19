-- Seed roles and demo users. Runs once on a fresh DB (Flyway-tracked).
-- Passwords are stored as BCrypt hashes; the plaintext below is what you send to
-- POST /auth/login. Change/add users at runtime via the admin-only /users endpoints.
--
--   role        username     password (plaintext)
--   ----------  -----------  ---------------------
--   ADMIN       admin        admin123
--   MANAGER     manager      manager123
--   SUPERVISOR  supervisor   supervisor123
INSERT INTO roles (name) VALUES
    ('ADMIN'),
    ('MANAGER'),
    ('SUPERVISOR');

INSERT INTO users (username, password, enabled) VALUES
    ('admin',      '$2a$10$Tx75xUAfnPvweHAZY0dNF.Ijs/X418aCd.Z3fS.wjBjWKz/0LbPdW', TRUE),
    ('manager',    '$2a$10$LGwH7Z33jWK4RIx73YFq3uf.UqM7N2d/PMMlqPhc0XsUc1tB1mqgK', TRUE),
    ('supervisor', '$2a$10$mVaRzQahd8qTlCPJ2r3fkeZRRL/TE/IBKwZ3f76XUXar7/HTUQaSO', TRUE);

-- Link each demo user to its role (resolve ids by natural keys, no hardcoded ids).
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
JOIN roles r ON (
        (u.username = 'admin'      AND r.name = 'ADMIN')
     OR (u.username = 'manager'    AND r.name = 'MANAGER')
     OR (u.username = 'supervisor' AND r.name = 'SUPERVISOR')
);
