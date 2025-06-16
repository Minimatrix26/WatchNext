CREATE TABLE review (
                        id SERIAL PRIMARY KEY,
                        movie_id INTEGER NOT NULL,
                        user_id INTEGER NOT NULL,
                        text TEXT NOT NULL,
                        CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES users_v2(id),
                        CONSTRAINT fk_review_movie FOREIGN KEY (movie_id) REFERENCES movies(id)
);
