-- Crearea tabelului users
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

-- Crearea tabelului categories
CREATE TABLE categories (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL UNIQUE
);

-- Crearea tabelului movies
CREATE TABLE movies (
                        id SERIAL PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        categoryId INTEGER REFERENCES categories(id),
                        imdbId VARCHAR(255) NOT NULL,
                        imdbScore FLOAT,
                        description VARCHAR(255),
                        releaseDate TIMESTAMP NOT NULL
);
