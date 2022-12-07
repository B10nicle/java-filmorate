DROP TABLE IF EXISTS film_genres CASCADE;
DROP TABLE IF EXISTS friends CASCADE;
DROP TABLE IF EXISTS ratings CASCADE;
DROP TABLE IF EXISTS genres CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS films CASCADE;
DROP TABLE IF EXISTS likes;

CREATE TABLE IF NOT EXISTS ratings
(
    rating_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name      VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS films
(
    film_id      BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         VARCHAR(50) NOT NULL,
    description  VARCHAR(200),
    release_date DATE,
    duration     INTEGER CHECK (duration > 0),
    rating_id    INTEGER REFERENCES ratings (rating_id)
);

CREATE TABLE IF NOT EXISTS genres
(
    genre_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name     VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS film_genres
(
    film_id  BIGINT  NOT NULL REFERENCES films (film_id),
    genre_id INTEGER NOT NULL REFERENCES genres (genre_id)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email    VARCHAR(30) NOT NULL UNIQUE,
    login    VARCHAR(30) NOT NULL UNIQUE,
    name     VARCHAR(50) NOT NULL,
    birthday DATE
);

CREATE TABLE IF NOT EXISTS friends
(
    user_id       BIGINT  NOT NULL REFERENCES users (user_id),
    friend_id     BIGINT  NOT NULL REFERENCES users (user_id),
    mutual_friend BOOLEAN NOT NULL,
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS likes
(
    user_id BIGINT NOT NULL REFERENCES users (user_id),
    film_id BIGINT NOT NULL REFERENCES films (film_id)
);