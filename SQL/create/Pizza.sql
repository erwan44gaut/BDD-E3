use pizzeria;
CREATE TABLE Pizza (
    pizza_id INT PRIMARY KEY AUTO_INCREMENT,
    size ENUM('naine', 'humaine', 'ogresse'),
    base_price DECIMAL(10, 2),
    name VARCHAR(50)
);