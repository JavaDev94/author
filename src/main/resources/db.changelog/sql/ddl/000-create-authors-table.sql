CREATE TABLE authors (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);