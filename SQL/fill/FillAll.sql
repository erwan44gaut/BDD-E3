
INSERT INTO Pizza (pizza_id, pizza_base_price, pizza_name)
VALUES
    (1, 9.99, 'Margherita'),
    (2, 10.99, 'Pepperoni'),
    (3, 11.99, 'Supreme'),
    (4, 8.99, 'Vegetarian'),
    (5, 12.99, 'Hawaiian'),
    (6, 10.99, 'BBQ Chicken'),
    (7, 13.99, 'Meat Lovers'),
    (8, 11.99, 'Mushroom'),
    (9, 9.99, 'Cheese'),
    (10, 10.99, 'Sausage');

-- Insert into Ingredient table
INSERT INTO Ingredient (ingredient_id, ingredient_name)
VALUES
    (1, 'Tomato Sauce'),
    (2, 'Mozzarella Cheese'),
    (3, 'Pepperoni'),
    (4, 'Onions'),
    (5, 'Bell Peppers'),
    (6, 'Olives');

-- Insert into Customer table
INSERT INTO Customer (customer_id, customer_name, customer_balance)
VALUES
    (1, 'ADMIN', 1000000.00),
    (2, 'Jane Smith', 150.00),
    (3, 'Michael Johnson', 200.00),
    (4, 'Sarah Davis', 120.00),
    (5, 'Robert Wilson', 180.00),
    (6, 'Emily Jones', 90.00),
    (7, 'Daniel Brown', 160.00),
    (8, 'Sophia Miller', 140.00),
    (9, 'William Taylor', 110.00),
    (10, 'Olivia Anderson', 130.00);

-- Insert into Pizza_Order table
INSERT INTO Pizza_Order (order_id, order_status, order_datetime, pizza_id, pizza_size, customer_id)
VALUES
    (1, 'COMPLETED', DATE_SUB(NOW(), INTERVAL 3 HOUR), 1, 'MEDIUM', 1),
    (2, 'COMPLETED', DATE_SUB(NOW(), INTERVAL 2 HOUR), 2, 'LARGE', 2),
    (3, 'COMPLETED', NOW(), 3, 'SMALL', 3),
    (4, 'COMPLETED', DATE_SUB(NOW(), INTERVAL 4 HOUR), 8, 'MEDIUM', 8),
    (5, 'COMPLETED', DATE_SUB(NOW(), INTERVAL 5 HOUR), 5, 'LARGE', 5),
    (6, 'IN_DELIVERY', NOW(), 6, 'SMALL', 6),
    (7, 'IN_DELIVERY', DATE_SUB(NOW(), INTERVAL 1 HOUR), 7, 'MEDIUM', 7),
    (8, 'COMPLETED', NOW(), 8, 'LARGE', 8),
    (9, 'IN_DELIVERY', NOW(), 9, 'SMALL', 9),
    (10, 'IN_DELIVERY', DATE_SUB(NOW(), INTERVAL 6 HOUR), 10, 'MEDIUM', 10),
    (11, 'ACCEPTED', NOW(), 8, 'LARGE', 8),
    (12, 'IN_PREPARATION', DATE_SUB(NOW(), INTERVAL 2 HOUR), 9, 'SMALL', 9),
    (13, 'ACCEPTED', NOW(), 10, 'MEDIUM', 10);

-- Insert into Vehicle table
INSERT INTO Vehicle (vehicle_id, vehicle_type, vehicle_model)
VALUES
	(1, 'CAR', 'Toyota Camry'),
	(2, 'MOTORBIKE', 'Honda CBR500R'),
	(3, 'MOTORBIKE', 'Trek Fuel EX 8'),
	(4, 'CAR', 'Ford Mustang'),
	(5, 'MOTORBIKE', 'Kawasaki Ninja 650'),
	(6, 'CAR', 'Giant Trance Advanced Pro 29'),
	(7, 'CAR', 'Chevrolet Corvette'),
	(8, 'MOTORBIKE', 'Yamaha MT-07'),
	(9, 'CAR', 'Audi A4'),
	(10, 'MOTORBIKE', 'Ducati Panigale V4'),
	(11, 'CAR', 'BMW X5'),
	(12, 'MOTORBIKE', 'Suzuki GSX-R1000'),
	(13, 'CAR', 'Mercedes-Benz C-Class'),
	(14, 'MOTORBIKE', 'Triumph Street Triple'),
	(15, 'CAR', 'Volkswagen Golf'),
	(16, 'MOTORBIKE', 'KTM Duke 390');
    
-- Insert into Delivery_Person table
INSERT INTO Delivery_Person (delivery_person_id, delivery_person_name, vehicle_id)
VALUES
    (1, 'ADMIN', 1),
    (2, 'Sarah', 2),
    (3, 'David', 3),
    (4, 'Emma', 4),
    (5, 'John', 5),
    (6, 'Emily', 6),
    (7, 'Daniel', 7),
    (8, 'Sophia', 8);

-- Insert into Delivery table
INSERT INTO Delivery (delivery_id, delivery_status, delivery_datetime, delivery_person_id, order_id)
VALUES
    (1, 'COMPLETED', NOW(), 1, 1),
    (2, 'COMPLETED', NOW(), 2, 2),
    (3, 'LATE', NOW(), 4, 3),
    (4, 'LATE', NOW(), 4, 4),
    (5, 'LATE', NOW(), 5, 5),
    (6, 'IN_PROGRESS', NULL, 6, 6),
    (7, 'IN_PROGRESS', NULL, 7, 7),
    (8, 'COMPLETED', NOW(), 1, 8),
    (9, 'IN_PROGRESS', NULL, 2, 9),
    (10, 'ACCEPTED', NULL, 8, 10);
    
-- Insert into Has_Ingredient table
INSERT INTO Has_Ingredient (pizza_id, ingredient_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (2, 3),
    (2, 4),
    (3, 1),
    (3, 2),
    (3, 5),
    (3, 6),
    (4, 1),
    (5, 1),
    (5, 2),
    (5, 4),
    (6, 1),
    (6, 3),
    (6, 5),
    (6, 6),
    (7, 1),
    (7, 2),
    (7, 3),
    (7, 4),
    (7, 5),
    (8, 1),
    (8, 2),
    (8, 4),
    (8, 6),
    (9, 2),
    (10, 1),
    (10, 3),
    (10, 4),
    (10, 5);