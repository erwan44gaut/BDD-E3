use pizzeria;

CREATE TABLE Pizza(
   pizza_id INT AUTO_INCREMENT PRIMARY KEY,
   pizza_base_price DECIMAL(10,2) NOT NULL,
   pizza_name VARCHAR(50) NOT NULL,
   UNIQUE(pizza_name)
);

CREATE TABLE Ingredient(
   ingredient_id INT AUTO_INCREMENT PRIMARY KEY,
   ingredient_name VARCHAR(50) NOT NULL,
   UNIQUE(ingredient_name)
);

CREATE TABLE Customer(
   customer_id INT AUTO_INCREMENT PRIMARY KEY,
   customer_name VARCHAR(50) NOT NULL,
   customer_balance DECIMAL(10,2) DEFAULT(0)
);

CREATE TABLE Pizza_Order(
   order_id INT AUTO_INCREMENT PRIMARY KEY,
   order_status ENUM('ACCEPTED', 'IN_PREPARATION', 'IN_DELIVERY', 'COMPLETED', 'CANCELED') DEFAULT 'ACCEPTED',
   order_datetime DATETIME DEFAULT NOW(),
   customer_id INT,
   pizza_id INT,
   pizza_size ENUM('SMALL', 'MEDIUM', 'LARGE') DEFAULT 'MEDIUM',
   FOREIGN KEY(pizza_id) REFERENCES Pizza(pizza_id) ON DELETE SET NULL,
   FOREIGN KEY(customer_id) REFERENCES Customer(customer_id) ON DELETE SET NULL
);

CREATE TABLE Vehicle(
   vehicle_id INT AUTO_INCREMENT  PRIMARY KEY,
   vehicle_type VARCHAR(50) UNIQUE
);

CREATE TABLE Delivery_Person(
   delivery_person_id INT AUTO_INCREMENT PRIMARY KEY,
   delivery_person_name VARCHAR(50) NOT NULL,
   vehicle_id INT,
   FOREIGN KEY(vehicle_id) REFERENCES Vehicle(vehicle_id)
);

CREATE TABLE Delivery(
   delivery_id INT AUTO_INCREMENT PRIMARY KEY,
   delivery_status ENUM('ACCEPTED', 'IN_PROGRESS', 'COMPLETE', 'LATE') DEFAULT "ACCEPTED",
   delivery_datetime DATETIME,
   delivery_person_id INT,
   order_id INT,
   UNIQUE(order_id),
   FOREIGN KEY(delivery_person_id) REFERENCES Delivery_Person(delivery_person_id) ON DELETE SET NULL,
   FOREIGN KEY(order_id) REFERENCES Pizza_Order(order_id) ON DELETE SET NULL
);

CREATE TABLE Has_Ingredient(
   pizza_id INT NOT NULL,
   ingredient_id INT NOT NULL,
   PRIMARY KEY(pizza_id, ingredient_id),
   FOREIGN KEY(pizza_id) REFERENCES Pizza(pizza_id) ON DELETE CASCADE,
   FOREIGN KEY(ingredient_id) REFERENCES Ingredient(ingredient_id) ON DELETE CASCADE
);
