CREATE TABLE confirmation_tokens (
                       id SERIAL PRIMARY KEY,
                       token VARCHAR(255) NOT NULL,
                       createdAt TIMESTAMP NOT NULL,
                       expiresAt TIMESTAMP NOT NULL,
                        confirmedAt TIMESTAMP NOT NULL,
                        appUser INTEGER REFERENCES users(id)
);