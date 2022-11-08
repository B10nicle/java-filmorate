create table user_film (
    user_id int8 not null references usr,
    film_id int8 not null references usr,
    primary key (user_id, film_id)
)