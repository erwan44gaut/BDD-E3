-- Insert into Pizza table
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
    (1, 'John Doe', 100.00),
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
    (1, 'ACCEPTED', NOW(), 1, 'MEDIUM', 1),
    (2, 'IN_PREPARATION', NOW(), 2, 'LARGE', 2),
    (3, 'IN_DELIVERY', NOW(), 3, 'SMALL', 3),
    (4, 'COMPLETED', NOW(), 8, 'MEDIUM', 8),
    (5, 'COMPLETED', NOW(), 5, 'LARGE', 5),
    (6, 'IN_PREPARATION', NOW(), 6, 'SMALL', 6),
    (7, 'ACCEPTED', NOW(), 7, 'MEDIUM', 7),
    (8, 'COMPLETED', NOW(), 8, 'LARGE', 8),
    (9, 'IN_DELIVERY', NOW(), 9, 'SMALL', 9),
    (10, 'IN_DELIVERY', NOW(), 10, 'MEDIUM', 10);

-- Insert into Vehicle table
INSERT INTO Vehicle (vehicle_id, vehicle_type)
VALUES
    (1, 'CAR'),
    (2, 'CAR'),
    (3, 'MOTORBIKE'),
    (4, 'CAR'),
    (5, 'MOTORBIKE'),
    (6, 'MOTORBIKE'),
    (7, 'CAR'),
    (8, 'MOTORBIKE'),
    (9, 'MOTORBIKE'),
    (10, 'CAR');
    
-- Insert into Delivery_Person table
INSERT INTO Delivery_Person (delivery_person_id, delivery_person_name)
VALUES
    (1, 'Mike'),
    (2, 'Sarah'),
    (3, 'David'),
    (4, 'Emma'),
    (5, 'John'),
    (6, 'Emily'),
    (7, 'Daniel'),
    (8, 'Sophia'),
    (9, 'William'),
    (10, 'Olivia');

-- Insert into Invoice table
INSERT INTO Invoice (invoice_id, pizza_id, pizza_name, pizza_size, total_price, customer_id, customer_name, order_time, delivery_time)
VALUES
    (1, 1, 'Margherita', 'MEDIUM', 9.99, 1, 'John Doe', NOW(), NOW()),
    (2, 2, 'Pepperoni', 'LARGE', 10.99, 2, 'Jane Smith', NOW(), NOW()),
    (3, 3, 'Supreme', 'SMALL', 11.99, 3, 'Michael Johnson', NOW(), NOW()),
    (4, 4, 'Vegetarian', 'MEDIUM', 8.99, 4, 'Sarah Davis', NOW(), NOW()),
    (5, 5, 'Hawaiian', 'LARGE', 12.99, 5, 'Robert Wilson', NOW(), NOW()),
    (6, 6, 'BBQ Chicken', 'SMALL', 10.99, 6, 'Emily Jones', NOW(), NOW()),
    (7, 7, 'Meat Lovers', 'MEDIUM', 13.99, 7, 'Daniel Brown', NOW(), NOW()),
    (8, 8, 'Mushroom', 'LARGE', 11.99, 8, 'Sophia Miller', NOW(), NOW()),
    (9, 9, 'Cheese', 'SMALL', 9.99, 9, 'William Taylor', NOW(), NOW()),
    (10, 10, 'Sausage', 'MEDIUM', 10.99, 10, 'Olivia Anderson', NOW(), NOW());

-- Insert into Delivery table
INSERT INTO Delivery (delivery_id, delivery_status, delivery_datetime, delivery_person_id, vehicle_id, order_id)
VALUES
    (1, 'COMPLETE', NOW(), 1, 1, 1),
    (2, 'COMPLETE', NOW(), 2, 2, 2),
    (3, 'LATE', NOW(), 4, 4, 3),
    (4, 'LATE', NOW(), 4, 4, 4),
    (5, 'LATE', NOW(), 5, 5, 5),
    (6, 'IN_PROGRESS', NOW(), 6, 6, 6),
    (7, 'IN_PROGRESS', NOW(), 7, 7, 7),
    (8, 'COMPLETE', NOW(), 1, 1, 8),
    (9, 'IN_PROGRESS', NOW(), 9, 9, 9),
    (10, 'IN_PROGRESS', NOW(), 10, 10, 10);
    
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
