CREATE TABLE wishlist (
                          id SERIAL PRIMARY KEY,
                          user_id INTEGER NOT NULL,
                          movie_id INTEGER NOT NULL,
                          CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
                          CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES movies(id),
                          CONSTRAINT uq_user_movie UNIQUE (user_id, movie_id)
);
