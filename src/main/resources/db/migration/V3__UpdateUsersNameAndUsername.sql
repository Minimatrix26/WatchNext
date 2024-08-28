-- Actualizare users: name -> firstName, username -> lastName
-- In cazul nostru, username = email

ALTER TABLE users RENAME COLUMN name to firstname;
ALTER TABLE users RENAME COLUMN username to lastName;