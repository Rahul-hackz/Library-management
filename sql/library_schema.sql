CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

CREATE TABLE users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL,
  role ENUM('ADMIN','MEMBER') NOT NULL,
  email VARCHAR(100)
);

CREATE TABLE books (
  book_id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  author VARCHAR(200),
  category VARCHAR(100),
  quantity INT DEFAULT 1
);

CREATE TABLE transactions (
  transaction_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  book_id INT NOT NULL,
  issue_date DATE,
  due_date DATE,
  return_date DATE,
  fine DECIMAL(7,2) DEFAULT 0,
  status ENUM('ISSUED','RETURNED') DEFAULT 'ISSUED',
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (book_id) REFERENCES books(book_id)
);

-- Seed an admin user (plaintext password 'admin123' for demo)
INSERT INTO users (username, password, role, email) VALUES
('admin','admin123','ADMIN','admin@example.com');
