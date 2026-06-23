-- Demo/seed data. Runs once on a fresh database (Flyway tracks it), so the
-- books table is pre-populated the first time the app boots in any Docker mode.
INSERT INTO books (title, author, isbn, price, description) VALUES
    ('The Pragmatic Programmer', 'Andrew Hunt, David Thomas', '9780201616224', 49.99, 'Your journey to mastery'),
    ('Clean Code',               'Robert C. Martin',          '9780132350884', 39.99, 'A handbook of agile software craftsmanship'),
    ('Effective Java',           'Joshua Bloch',              '9780134685991', 54.99, 'Best practices for the Java platform'),
    ('Designing Data-Intensive Applications', 'Martin Kleppmann', '9781449373320', 59.99, 'The big ideas behind reliable, scalable systems'),
    ('Refactoring',              'Martin Fowler',             '9780134757599', 47.99, 'Improving the design of existing code');
