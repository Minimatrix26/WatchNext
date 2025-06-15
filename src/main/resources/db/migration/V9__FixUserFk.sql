-- Ștergi mai întâi constrângerile greșite (dacă s-au aplicat deja)
ALTER TABLE wishlist DROP CONSTRAINT IF EXISTS fk_user;
ALTER TABLE wishlist DROP CONSTRAINT IF EXISTS fk_movie;

-- Adaugi constrângeri corecte
ALTER TABLE wishlist
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users_v2(id),
    ADD CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES movies(id);
