-- Insert dummy values into the Pizza table

INSERT INTO Pizza (pizza_id, pizza_base_price, pizza_name) VALUES
(1, 10.99, 'Margherita'),
(2, 8.99, 'Pepperoni'),
(3, 6.99, 'Cheese');

-- Insert dummy values into the Ingredient table
INSERT INTO Ingredient (ingredient_id, ingredient_name) VALUES
(1, 'Tomato Sauce'),
(2, 'Mozzarella Cheese'),
(3, 'Pepperoni'),
(4, 'Mushrooms'),
(5, 'Onions');

-- Insert dummy values into the Customer table
INSERT INTO Customer (customer_id, customer_name, customer_balance) VALUES
(1, 'John Doe', 100.0),
(2, 'Jane Smith', 50.0),
(3, 'David Johnson', 75.0);

-- Insert dummy values into the Pizza_Order table
INSERT INTO Pizza_Order (order_id, order_status, order_datetime, pizza_id, customer_id, pizza_size) VALUES
(1, 'Pending', '2023-06-01 10:00:00', 1, 1, 'large'),
(2, 'Completed', '2023-06-02 12:30:00', 2, 2, 'small'),
(3, 'Pending', '2023-06-03 15:45:00', 3, 3, 'medium');

-- Insert dummy values into the Vehicle table
INSERT INTO Vehicle (vehicle_id, vehicle_type) VALUES
(1, 'Car'),
(2, 'Motorcycle'),
(3, 'Bicycle');

-- Insert dummy values into the Delivery_Person table
INSERT INTO Delivery_Person (delivery_person_id, delivery_person_name) VALUES
(1, 'Mike Smith'),
(2, 'Sarah Johnson'),
(3, 'Mark Davis');

-- Insert dummy values into the Invoice table
INSERT INTO Invoice (invoice_id, order_id) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Insert dummy values into the Delivery table
INSERT INTO Delivery (delivery_id, delivery_status, delivery_datetime, delivery_person_id, vehicle_id, order_id) VALUES
(1, 'Delivered', '2023-06-01 11:30:00', 1, 1, 1),
(2, 'In Progress', '2023-06-02 13:00:00', 2, 2, 2),
(3, 'Scheduled', '2023-06-03 16:00:00', 3, 3, 3);

-- Insert dummy values into the Has_Ingredient table
INSERT INTO Has_Ingredient (pizza_id, ingredient_id) VALUES
(1, 1),
(1, 2),
(2, 2),
(2, 3),
(2, 5),
(3, 1),
(3, 2),
(3, 4),
(3, 5);
