-- Demo/seed data. Runs once on a fresh database (Flyway tracks it), so the
-- workers table is pre-populated the first time the app boots in any Docker mode.
INSERT INTO workers (nid, worker_name, mobile_no) VALUES
    ('1990123456789', 'Rahim Uddin',    '01711000001'),
    ('1985987654321', 'Karim Hossain',  '01811000002'),
    ('1992456789123', 'Fatima Begum',   '01911000003'),
    ('1988321654987', 'Ayesha Akter',   '01611000004'),
    ('1995789123456', 'Jamal Mia',      '01511000005');
