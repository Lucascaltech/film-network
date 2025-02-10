INSERT INTO _user (firstname, lastname, date_of_birth, email, password, account_locked, enabled)
VALUES 
('John', 'Doe', '1980-01-01', 'john.doe@example.com', '$2y$10$rhcAyaGs/VdQR6QvkQrN/OXjAaC6v8woAC.taTPjVFeWjGlR7lT4O', false, true),
('Jane', 'Smith', '1985-02-03', 'jane.smith@example.com', '$2y$10$rhcAyaGs/VdQR6QvkQrN/OXjAaC6v8woAC.taTPjVFeWjGlR7lT4O', false, true),
('Alice', 'Johnson', '1990-04-15', 'alice.johnson@example.com', '$2y$10$rhcAyaGs/VdQR6QvkQrN/OXjAaC6v8woAC.taTPjVFeWjGlR7lT4O', false, true),
('Bob', 'Brown', '1978-07-22', 'bob.brown@example.com', '$2y$10$rhcAyaGs/VdQR6QvkQrN/OXjAaC6v8woAC.taTPjVFeWjGlR7lT4O', false, true),
('Carol', 'White', '1982-03-30', 'carol.white@example.com', '$2y$10$rhcAyaGs/VdQR6QvkQrN/OXjAaC6v8woAC.taTPjVFeWjGlR7lT4O', false, true),
('David', 'Black', '1988-11-10', 'david.black@example.com', '$2y$10$rhcAyaGs/VdQR6QvkQrN/OXjAaC6v8woAC.taTPjVFeWjGlR7lT4O', false, true),
('Eve', 'Davis', '1992-12-05', 'eve.davis@example.com', '$2y$10$rhcAyaGs/VdQR6QvkQrN/OXjAaC6v8woAC.taTPjVFeWjGlR7lT4O', false, true),
('Frank', 'Wilson', '1983-09-09', 'frank.wilson@example.com', '$2y$10$rhcAyaGs/VdQR6QvkQrN/OXjAaC6v8woAC.taTPjVFeWjGlR7lT4O', false, true),
('Grace', 'Lee', '1995-05-20', 'grace.lee@example.com', '$2y$10$rhcAyaGs/VdQR6QvkQrN/OXjAaC6v8woAC.taTPjVFeWjGlR7lT4O', false, true),
('Henry', 'Clark', '1975-08-18', 'henry.clark@example.com', '$2y$10$rhcAyaGs/VdQR6QvkQrN/OXjAaC6v8woAC.taTPjVFeWjGlR7lT4O', false, true);


INSERT INTO role (name)
VALUES
('ROLE_ADMIN'),
('ROLE_USER'),
('ROLE_MODERATOR');

-- User 1 (John Doe) as Admin and User
INSERT INTO _user_roles (user_id, roles_id) VALUES (1, 1), (1, 2);

-- Users 2 through 10 as User only
INSERT INTO _user_roles (user_id, roles_id)
VALUES
(2, 2), (3, 2), (4, 2), (5, 2),
(6, 2), (7, 2), (8, 2), (9, 2), (10, 2);

INSERT INTO film (title, director, imdb_id, synopsis, film_poster, year, archive, rating, genre, added_by)
VALUES
('The Shawshank Redemption',
 'Frank Darabont', 'tt0111161', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', './uploads/ShawshankRedemptionMoviePoster.jpg', '1994', false, 4.3, 'DRAMA', 1),
('The Godfather', 'Francis Ford Coppola', 'tt0068646', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', './uploads/Godfather_ver1.jpg', '1972', false, 4.2, 'DRAMA', 2),
('The Dark Knight', 'Christopher Nolan', 'tt0468569', 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological tests of his ability to fight injustice.', './uploads/Dark_Night.jpeg', '2008', false, 5.0, 'ACTION', 3),
('Schindler''s List', 'Steven Spielberg', 'tt0108052', 'In German-occupied Poland during World War II, Oskar Schindler gradually becomes concerned for his Jewish workforce.', './uploads/Schindler''s_List_movie.jpg', '1993', false, 3.9, 'DRAMA', 4),
('Pulp Fiction', 'Quentin Tarantino', 'tt0110912', 'The lives of two mob hitmen, a boxer, a gangster''s wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', './uploads/Pulp_Fiction_cover.jpg', '1994', false, 8.9, 'CRIME', 5),
('Inception', 'Christopher Nolan', 'tt1375666', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.', './uploads/Inception_ver3.jpg', '2010', false, 8.8, 'SCI_FI', 6),
('Fight Club', 'David Fincher', 'tt0137523', 'An insomniac office worker and a devil-may-care soap maker form an underground fight club.', './uploads/Fight_Club_poster.jpg', '1999', false, 8.8, 'DRAMA', 7),
('Forrest Gump', 'Robert Zemeckis', 'tt0109830', 'The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate, and other history unfold through the perspective of an Alabama man.', './uploads/Forrest_Gump_poster.jpg', '1994', false, 8.8, 'DRAMA', 8),
('The Matrix', 'Lana Wachowski, Lilly Wachowski', 'tt0133093', 'A computer hacker learns about the true nature of his reality and his role in the war against its controllers.', './uploads/The_Matrix_Poster.jpg', '1999', false, 8.7, 'SCI_FI', 9),
('Goodfellas', 'Martin Scorsese', 'tt0099685', 'The story of Henry Hill and his life in the mob, covering his relationship with his wife and his mob partners.', './uploads/Goodfellas.jpg', '1990', false, 8.7, 'CRIME', 10),
('The Lord of the Rings: The Fellowship of the Ring', 'Peter Jackson', 'tt0120737', 'A meek Hobbit and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth.', './uploads/The_Fellowship_Of_The_Ring.jpg', '2001', false, 8.8, 'ADVENTURE', 1),
('Star Wars: Episode IV - A New Hope', 'George Lucas', 'tt0076759', 'Luke Skywalker joins forces with a Jedi Knight and a group of rebels to save the galaxy from the Empire.', './uploads/StarWarsMoviePoster1977.jpg', '1977', false, 8.6, 'SCI_FI', 2),
('The Silence of the Lambs', 'Jonathan Demme', 'tt0102926', 'A young FBI cadet seeks the help of an incarcerated cannibalistic serial killer to catch another killer.', './uploads/The_Silence_of_the_Lambs_poster.jpg', '1991', false, 8.6, 'CRIME', 3),
('Saving Private Ryan', 'Steven Spielberg', 'tt0120815', 'Following the Normandy Landings, a group of U.S. soldiers go behind enemy lines to retrieve a paratrooper.', './uploads/Saving_Private_Ryan_poster.jpg', '1998', false, 8.6, 'WAR', 4),
('Interstellar', 'Christopher Nolan', 'tt0816692', 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity''s survival.', './uploads/Interstellar_film_poster.jpg', '2014', false, 8.6, 'SCI_FI', 5),
('Gladiator', 'Ridley Scott', 'tt0172495', 'A former Roman General seeks revenge against the corrupt emperor who murdered his family.', './uploads/Gladiator_ver1.jpg', '2000', false, 8.5, 'ACTION', 6),
('The Green Mile', 'Frank Darabont', 'tt0120689', 'The lives of guards on Death Row are affected by one of their charges: a gentle giant with a mysterious gift.', './uploads/Green_mile.jpg', '1999', false, 8.6, 'DRAMA', 7),
('Se7en', 'David Fincher', 'tt0114369', 'Two detectives hunt a serial killer who uses the seven deadly sins as his motives.', './uploads/Seven_movie_poster.jpg', '1995', false, 8.6, 'CRIME', 8),
('The Prestige', 'Christopher Nolan', 'tt0482571', 'Two rival magicians engage in a battle to create the ultimate illusion.', './uploads/Prestige_poster.jpg', '2006', false, 8.5, 'DRAMA', 9),
('Parasite', 'Bong Joon Ho', 'tt6751668', 'Greed and class discrimination threaten the symbiotic relationship between the wealthy Park family and the destitute Kim clan.', './uploads/Parasite2019_film.png', '2019', false, 8.6, 'DRAMA', 10);
