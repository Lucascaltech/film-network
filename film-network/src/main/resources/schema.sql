-- Drop tables in reverse order of dependencies
DROP TABLE IF EXISTS token;
DROP TABLE IF EXISTS film_rental_history;
DROP TABLE IF EXISTS film_feedback;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS film;
DROP TABLE IF EXISTS _user;

-- Create the _user table (for the User entity)
CREATE TABLE _user (
    id SERIAL PRIMARY KEY,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    date_of_birth DATE,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    account_locked BOOLEAN,
    enabled BOOLEAN,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the film table (for the Film entity)
CREATE TABLE film (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    director VARCHAR(255),
    imdb_id VARCHAR(255),
    synopsis TEXT,
    film_poster TEXT,
    year VARCHAR(4),
    archive BOOLEAN,
    rating DOUBLE PRECISION,
    genre VARCHAR(50),
    added_by INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by INT,
    updated_by INT,
    version INT DEFAULT 0,
    CONSTRAINT fk_film_addedby FOREIGN KEY (added_by) REFERENCES _user(id)
);


-- Create the role table (for the Role entity)
CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the join table for User and Role (ManyToMany)
CREATE TABLE _user_roles (
    user_id INT NOT NULL,
    roles_id INT NOT NULL,
    PRIMARY KEY (user_id, roles_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES _user(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (roles_id) REFERENCES role(id) ON DELETE CASCADE
);

-- Create the film_feedback table (for the FilmFeedback entity)
CREATE TABLE film_feedback (
    id SERIAL PRIMARY KEY,
    rating DOUBLE PRECISION,
    review TEXT,
    film_id INT,
    user_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_feedback_film FOREIGN KEY (film_id) REFERENCES film(id) ON DELETE SET NULL,
    CONSTRAINT fk_feedback_user FOREIGN KEY (user_id) REFERENCES _user(id) ON DELETE SET NULL
);

-- Create the film_rental_history table (for the FilmRentalHistory entity)
CREATE TABLE film_rental_history (
    id SERIAL PRIMARY KEY,
    rental_date TIMESTAMP,
    return_date TIMESTAMP,
    returned BOOLEAN,
    returned_approved BOOLEAN,
    rental_price DOUBLE PRECISION,
    user_id INT,
    film_id INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_history_user FOREIGN KEY (user_id) REFERENCES _user(id) ON DELETE SET NULL,
    CONSTRAINT fk_history_film FOREIGN KEY (film_id) REFERENCES film(id) ON DELETE SET NULL
);

-- Create the token table (for the Token entity)
CREATE TABLE token (
    id SERIAL PRIMARY KEY,
    token VARCHAR(512),
    created_at TIMESTAMP,
    expires_at TIMESTAMP,
    validated_at TIMESTAMP,
    user_id INT NOT NULL,
    CONSTRAINT fk_token_user FOREIGN KEY (user_id) REFERENCES _user(id) ON DELETE CASCADE
);
