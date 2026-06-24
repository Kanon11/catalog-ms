ALTER TABLE workers ADD COLUMN factory_id BIGINT;

ALTER TABLE workers ADD CONSTRAINT fk_workers_factory
    FOREIGN KEY (factory_id) REFERENCES factories (id);
