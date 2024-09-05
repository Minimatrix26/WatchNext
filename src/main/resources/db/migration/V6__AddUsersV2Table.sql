CREATE TABLE users_v2 (
                          id SERIAL PRIMARY KEY,
                          firstname VARCHAR(255) NOT NULL,
                          lastname VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          role VARCHAR(255) NOT NULL
);