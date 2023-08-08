DROP DATABASE IF EXISTS note_taker_db;

CREATE DATABASE note_taker_db;

USE note_taker_db;

CREATE TABLE todo (
    id INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(255) NOT NULL
);