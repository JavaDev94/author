CREATE TABLE transactions (
                              id SERIAL PRIMARY KEY,
                              author_id INT REFERENCES authors(id) ON DELETE CASCADE,
                              title VARCHAR(100),
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);