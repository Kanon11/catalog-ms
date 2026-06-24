-- Demo/seed data. Runs once on a fresh database (Flyway tracks it), so the
-- applications table is pre-populated the first time the app boots in any Docker mode.
-- worker_id values reference the demo workers seeded in V11 (ids 1-5).
INSERT INTO applications (worker_id, application_date, reason_for_unemployment, status) VALUES
    (1, DATE '2026-01-15', 'Factory closure',            'APPROVED'),
    (2, DATE '2026-02-03', 'Seasonal layoff',            'PENDING'),
    (3, DATE '2026-02-20', 'Company downsizing',         'REJECTED'),
    (4, DATE '2026-03-10', 'End of contract',            'APPROVED'),
    (5, DATE '2026-03-28', 'Workplace injury',           'PENDING'),
    (1, DATE '2026-04-12', 'Business relocation abroad', 'PENDING');
