-- Insert data into Pizza table
INSERT INTO Pizza (pizza_id, pizza_size, pizza_base_price, pizza_name)
VALUES
  (1, 'Small', 10.99, 'Margherita'),
  (2, 'Medium', 14.99, 'Pepperoni'),
  (3, 'Large', 18.99, 'Supreme');

-- Insert data into Ingredient table
INSERT INTO Ingredient (ingredient_id, ingredient_name)
VALUES
  (1, 'Tomato Sauce'),
  (2, 'Cheese'),
  (3, 'Pepperoni'),
  (4, 'Mushrooms'),
  (5, 'Onions'),
  (6, 'Bell Peppers');

-- Insert data into Customer table
INSERT INTO Customer (customer_id, customer_name, customer_balance)
VALUES
  (1, 'John Doe', 100.00),
  (2, 'Jane Smith', 75.50),
  (3, 'David Johnson', 50.25);

-- Insert data into Pizza_Order table
INSERT INTO Pizza_Order (order_id, order_status, order_time, pizza_id, customer_id)
VALUES
  (1, 'Pending', NOW(), 1, 1),
  (2, 'Delivered', NOW(), 2, 2),
  (3, 'In Progress', NOW(), 3, 3);

-- Insert data into Vehicle table
INSERT INTO Vehicle (vehicle_id, vehicle_type)
VALUES
  (1, 'Car'),
  (2, 'Bike'),
  (3, 'Scooter');

-- Insert data into Delivery_Person table
INSERT INTO Delivery_Person (delivery_person_id, delivery_person_name)
VALUES
  (1, 'Michael Brown'),
  (2, 'Emily Davis'),
  (3, 'Daniel Wilson');

-- Insert data into Invoice table
INSERT INTO Invoice (invoice_id, order_id)
VALUES
  (1, 1),
  (2, 2),
  (3, 3);

-- Insert data into Delivery table
INSERT INTO Delivery (delivery_id, delivery_status, delivery_time, delivery_person_id, vehicle_id, order_id)
VALUES
  (1, 'In Transit', NOW(), 1, 1, 1),
  (2, 'Delivered', NOW(), 2, 2, 2),
  (3, 'In Progress', NOW(), 3, 3, 3);

-- Insert data into Has_Ingredient table
INSERT INTO Has_Ingredient (pizza_id, ingredient_id)
VALUES
  (1, 1),
  (1, 2),
  (2, 1),
  (2, 2),
  (2, 3),
  (3, 1),
  (3, 2),
  (3, 3),
  (3, 4),
  (3, 5),
  (3, 6);
