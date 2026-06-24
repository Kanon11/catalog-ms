-- Demo/seed data. Runs once on a fresh database (Flyway tracks it), so the
-- factories table is pre-populated the first time the app boots in any Docker mode.
INSERT INTO factories (name) VALUES
    ('Dhaka Textile Mills'),
    ('Chittagong Steel Works'),
    ('Khulna Jute Factory'),
    ('Rajshahi Ceramics'),
    ('Sylhet Tea Processing');
