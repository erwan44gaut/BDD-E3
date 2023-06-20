CREATE TABLE Pizza(
   pizza_id INT,
   pizza_base_price DECIMAL(10,2) NOT NULL,
   pizza_name VARCHAR(50) NOT NULL,
   PRIMARY KEY(pizza_id),
   UNIQUE(pizza_name)
);

CREATE TABLE Ingredient(
   ingredient_id INT AUTO_INCREMENT,
   ingredient_name VARCHAR(50) NOT NULL,
   PRIMARY KEY(ingredient_id),
   UNIQUE(ingredient_name)
);

CREATE TABLE Customer(
   customer_id INT AUTO_INCREMENT,
   customer_name VARCHAR(50) NOT NULL,
   customer_balance DECIMAL(10,2) DEFAULT(0),
   PRIMARY KEY(customer_id)
);

CREATE TABLE Pizza_Order(
   order_id INT AUTO_INCREMENT,
   order_status VARCHAR(50) NOT NULL,
   order_datetime DATETIME NOT NULL,
   customer_id INT,
   pizza_id INT,
   pizza_size VARCHAR(50) DEFAULT 'medium',
   PRIMARY KEY(order_id),
   FOREIGN KEY(pizza_id) REFERENCES Pizza(pizza_id) ON DELETE SET NULL,
   FOREIGN KEY(customer_id) REFERENCES Customer(customer_id) ON DELETE SET NULL
);

CREATE TABLE Vehicle(
   vehicle_id INT AUTO_INCREMENT,
   vehicle_type VARCHAR(50) NOT NULL,
   PRIMARY KEY(vehicle_id),
   UNIQUE(vehicle_type)
);

CREATE TABLE Delivery_Person(
   delivery_person_id INT AUTO_INCREMENT,
   delivery_person_name VARCHAR(50) NOT NULL,
   PRIMARY KEY(delivery_person_id)
);

CREATE TABLE Invoice(
   invoice_id INT,
   pizza_id INT,
   pizza_name VARCHAR(50),
   pizza_size VARCHAR(50),
   total_price FLOAT,
   customer_id INT,
   customer_name VARCHAR(50),
   order_time DATETIME,
   delivery_time DATETIME,
   PRIMARY KEY(invoice_id)
);


CREATE TABLE Delivery(
   delivery_id INT AUTO_INCREMENT,
   delivery_status VARCHAR(24),
   delivery_datetime DATETIME,
   delivery_person_id INT,
   vehicle_id INT,
   order_id INT,
   PRIMARY KEY(delivery_id),
   UNIQUE(order_id),
   FOREIGN KEY(delivery_person_id) REFERENCES Delivery_Person(delivery_person_id) ON DELETE SET NULL,
   FOREIGN KEY(vehicle_id) REFERENCES Vehicle(vehicle_id) ON DELETE SET NULL,
   FOREIGN KEY(order_id) REFERENCES Pizza_Order(order_id) ON DELETE SET NULL
);

CREATE TABLE Has_Ingredient(
   pizza_id INT NOT NULL,
   ingredient_id INT NOT NULL,
   PRIMARY KEY(pizza_id, ingredient_id),
   FOREIGN KEY(pizza_id) REFERENCES Pizza(pizza_id) ON DELETE CASCADE,
   FOREIGN KEY(ingredient_id) REFERENCES Ingredient(ingredient_id) ON DELETE CASCADE
);
