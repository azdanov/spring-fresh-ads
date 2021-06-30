CREATE TABLE roles
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name       TEXT                                     NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT pk_roles PRIMARY KEY (id),
    CONSTRAINT uc_roles_name UNIQUE (name)
);

INSERT INTO roles(name)
VALUES ('USER'),
       ('ADMIN');

CREATE TABLE users
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    email      TEXT                                     NOT NULL,
    password   TEXT                                     NOT NULL,
    name       TEXT                                     NOT NULL,
    enabled    BOOLEAN                     DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uc_users_email UNIQUE (email)
);

CREATE TABLE users_roles
(
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (role_id, user_id),
    CONSTRAINT fk_users_roles_on_users FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_users_roles_on_roles FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE verification_tokens
(
    id          INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    token       TEXT                                     NOT NULL,
    user_id     INTEGER                                  NOT NULL,
    expiry_date TIMESTAMP WITHOUT TIME ZONE              NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT pk_verification_tokens PRIMARY KEY (id),
    CONSTRAINT fk_verification_tokens_on_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE areas
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name       TEXT                                     NOT NULL,
    slug       TEXT                                     NOT NULL,
    usable     BOOL                                     NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE                       DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE                       DEFAULT NOW(),
    parent_id  INTEGER,
    tree_left  BIGINT                                   NOT NULL,
    tree_right BIGINT                                   NOT NULL,
    tree_level BIGINT                                   NOT NULL,
    CONSTRAINT pk_areas PRIMARY KEY (id),
    CONSTRAINT uc_areas_slug UNIQUE (slug)
);

CREATE TABLE categories
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name       TEXT                                     NOT NULL,
    slug       TEXT                                     NOT NULL,
    price      NUMERIC(12, 2)                           NOT NULL DEFAULT 0,
    usable     BOOL                                     NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE                       DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE                       DEFAULT NOW(),
    parent_id  INTEGER,
    tree_left  BIGINT                                   NOT NULL,
    tree_right BIGINT                                   NOT NULL,
    tree_level BIGINT                                   NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT uc_categories_slug UNIQUE (slug)
);

CREATE TABLE listings
(
    id          INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id     INTEGER,
    area_id     INTEGER,
    category_id INTEGER,
    title       TEXT,
    body        TEXT,
    live        BOOLEAN,
    deleted     BOOLEAN,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_listings PRIMARY KEY (id),
    CONSTRAINT fk_listings_on_area FOREIGN KEY (area_id) REFERENCES areas (id),
    CONSTRAINT fk_listings_on_category FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_listings_on_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE user_favorite_listing
(
    user_id    INTEGER NOT NULL,
    listing_id INTEGER NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT pk_user_favorite_listing PRIMARY KEY (user_id, listing_id),
    CONSTRAINT fk_user_favorite_listing_on_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_favorite_listing_on_listing FOREIGN KEY (user_id) REFERENCES listings (id) ON DELETE CASCADE
);

CREATE TABLE user_visited_listing
(
    user_id    INTEGER NOT NULL,
    listing_id INTEGER NOT NULL,
    visited    INTEGER NOT NULL            DEFAULT 1,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_user_visited_listing PRIMARY KEY (user_id, listing_id),
    CONSTRAINT fk_user_visited_listing_on_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_visited_listing_on_listing FOREIGN KEY (user_id) REFERENCES listings (id) ON DELETE CASCADE
);

CREATE TABLE payment
(
    listing_id INTEGER        NOT NULL,
    payment_id TEXT,
    price      NUMERIC(12, 2) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_payment PRIMARY KEY (listing_id),
    CONSTRAINT fk_payment_on_listing FOREIGN KEY (listing_id) REFERENCES listings (id)
);
