CREATE TABLE roles ( id INT AUTO_INCREMENT PRIMARY KEY, name NVARCHAR(10) );

CREATE TABLE users ( id INT AUTO_INCREMENT PRIMARY KEY, username NVARCHAR(50), fullName NVARCHAR(200), email NVARCHAR(100), password NVARCHAR(200), avatar NVARCHAR(200), isUserLocked BIT );

CREATE TABLE userrole ( id INT AUTO_INCREMENT PRIMARY KEY, user_id INT, role_id INT, FOREIGN KEY (user_id) REFERENCES users(id), FOREIGN KEY (role_id) REFERENCES roles(id) );

CREATE TABLE vocabulary ( id INT AUTO_INCREMENT PRIMARY KEY, english NVARCHAR(200), vietnamese NVARCHAR(200), description NVARCHAR(500), user_id INT, FOREIGN KEY (user_id) REFERENCES users(id) );

INSERT INTO roles (name) VALUES ('ADMIN'), ('USER'), ('GUEST'); INSERT INTO users (username, fullName, email, password, avatar, isUserLocked) VALUES ('admin', 'Nong Hoang Vu', 'nonghoangvu04@gmail.com', '$2a$12$D5xFF0wZ2q6BJwbbcS8HPeizUghZLJcolnzskoeXLrpNk6aB4tQ8y', 'avatar1.jpg', 1), ('user', 'Jane Smith', 'jane.smith@example.com', '$2a$12$8dPLVaFKK1JyI88wIiHdgu4h/9PK/ZzfYrC3rk5htlE3DRwtvls02', 'avatar2.jpg', 1), ('userLocked', 'Alice Wonder', 'alice.wonder@example.com', '$2a$12$8dPLVaFKK1JyI88wIiHdgu4h/9PK/ZzfYrC3rk5htlE3DRwtvls02', 'avatar3.jpg', 0); INSERT INTO userrole (user_id, role_id) VALUES (1, 1), (1, 2), (1, 3), (2, 2), (2, 3), (3, 3);