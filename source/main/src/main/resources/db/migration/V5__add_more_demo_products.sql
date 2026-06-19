-- More demo/seed data. Adds 15 products on top of the 5 from V4 for 20 total.
-- A separate migration (rather than editing V4) keeps Flyway checksums stable on
-- databases where V4 has already run — important since pgdata is a persistent volume.
INSERT INTO products (name, price, description) VALUES
    ('Laptop Stand',           34.99, 'Adjustable aluminum laptop stand'),
    ('Noise-Cancelling Headphones', 199.99, 'Over-ear Bluetooth headphones with ANC'),
    ('Webcam 1080p',           59.99, 'Full HD webcam with built-in microphone'),
    ('External SSD 1TB',      119.99, 'Portable USB-C NVMe SSD, 1050 MB/s'),
    ('Mechanical Numpad',      44.99, 'Wireless 21-key mechanical numpad'),
    ('Desk Mat',               19.99, 'Large water-resistant desk mat'),
    ('USB Microphone',         89.99, 'Cardioid condenser USB microphone'),
    ('Smart LED Bulb',         14.99, 'Wi-Fi RGB smart bulb, 800 lumens'),
    ('Portable Charger',       49.99, '20000mAh power bank with USB-C PD'),
    ('Bluetooth Speaker',      79.99, 'Waterproof portable speaker, 12h battery'),
    ('Wireless Earbuds',       99.99, 'True wireless earbuds with charging case'),
    ('Graphics Tablet',       129.99, '10-inch pen tablet, 8192 pressure levels'),
    ('Cable Management Kit',   12.99, 'Cable clips, sleeves and ties set'),
    ('Monitor Arm',            69.99, 'Single-monitor gas-spring desk mount'),
    ('Surge Protector',        29.99, '8-outlet surge protector with USB ports');
