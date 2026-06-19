-- Demo/seed data. Runs once on a fresh database (Flyway tracks it), so the
-- products table is pre-populated the first time the app boots in any Docker mode.
INSERT INTO products (name, price, description) VALUES
    ('Laptop',              1299.99, 'High-performance 14-inch laptop'),
    ('Wireless Mouse',        24.99, 'Ergonomic wireless mouse'),
    ('Mechanical Keyboard',   89.99, 'RGB mechanical keyboard with brown switches'),
    ('USB-C Hub',             39.99, '7-in-1 USB-C hub adapter'),
    ('27-inch Monitor',      249.99, '1440p IPS display, 165Hz');
