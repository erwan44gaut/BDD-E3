use pizzeria;
CREATE TABLE Vehicle(
   vehicle_id INT PRIMARY KEY AUTO_INCREMENT,
   type ENUM('motorbike', 'car') NOT NULL
);
