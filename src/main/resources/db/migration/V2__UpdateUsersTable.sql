-- Update tabel users
-- adaugare fields pt Spring Security

ALTER TABLE users
    ADD COLUMN name VARCHAR(255) NOT NULL,
    ADD COLUMN user_role VARCHAR(20) NOT NULL,
    ADD COLUMN locked BOOLEAN DEFAULT FALSE,
    ADD COLUMN enabled BOOLEAN DEFAULT TRUE;