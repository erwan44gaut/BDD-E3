use pizzeria; -- !! Change database name according to yours

-- ############### CREATE ###############

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
   vehicle_type ENUM('CAR', 'MOTORBIKE') DEFAULT 'CAR',
   vehicle_model VARCHAR(50)
);

CREATE TABLE Delivery_Person(
   delivery_person_id INT AUTO_INCREMENT PRIMARY KEY,
   delivery_person_name VARCHAR(50) NOT NULL,
   vehicle_id INT,
   FOREIGN KEY(vehicle_id) REFERENCES Vehicle(vehicle_id) ON DELETE SET NULL
);

CREATE TABLE Delivery(
   delivery_id INT AUTO_INCREMENT PRIMARY KEY,
   delivery_status ENUM('ACCEPTED', 'IN_PROGRESS', 'COMPLETED', 'LATE') DEFAULT "ACCEPTED",
   delivery_datetime DATETIME,
   delivery_person_id INT,
   vehicle_id INT,
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

-- ############### FILL ###############

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
    (1, 'ACCEPTED', NOW(), 1, 'MEDIUM', 1),
    (2, 'IN_PREPARATION', NOW(), 2, 'LARGE', 2),
    (3, 'IN_DELIVERY', NOW(), 3, 'SMALL', 3),
    (4, 'COMPLETED', NOW(), 8, 'MEDIUM', 8),
    (5, 'COMPLETED', NOW(), 5, 'LARGE', 5),
    (6, 'IN_PREPARATION', NOW(), 6, 'SMALL', 6),
    (7, 'ACCEPTED', NOW(), 7, 'MEDIUM', 7),
    (8, 'COMPLETED', NOW(), 8, 'LARGE', 8),
    (9, 'IN_DELIVERY', NOW(), 9, 'SMALL', 9),
    (10, 'IN_DELIVERY', NOW(), 10, 'MEDIUM', 10),
    (11, 'ACCEPTED', NOW(), 8, 'LARGE', 8),
    (12, 'IN_PREPARATION', NOW(), 9, 'SMALL', 9),
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
    (6, 'IN_PROGRESS', NOW(), 6, 6), 
    (7, 'IN_PROGRESS', NOW(), 7, 7),
    (8, 'COMPLETED', NOW(), 1, 8),
    (9, 'IN_PROGRESS', NOW(), 2, 9),
    (10, 'ACCEPTED', NOW(), 8, 10);
    
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

-- ############### DELIVERY PROCEDURES ###############

DELIMITER //

CREATE PROCEDURE AssignDelivery(IN delivery_person_id INT, order_id INT)
BEGIN
	DECLARE delivery_id INT;
    DECLARE vehicle_id INT;
	DECLARE order_status ENUM('ACCEPTED', 'IN_PREPARATION', 'IN_DELIVERY', 'COMPLETED', 'CANCELED');

    SELECT Delivery.delivery_id INTO delivery_id FROM Delivery WHERE Delivery.order_id = order_id;
	SELECT Pizza_Order.order_status INTO order_status FROM Pizza_Order WHERE Pizza_Order.order_id = order_id;
	SELECT Delivery_Person.vehicle_id INTO vehicle_id FROM Delivery_Person WHERE Delivery_Person.delivery_person_id = delivery_person_id;

    IF order_status = "COMPLETED" OR order_status = "CANCELED" OR order_status = "IN_DELIVERY" THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Cannot assign delivery to a finished order or one that already has an assigned delivery';
    END IF;

    IF delivery_id IS NOT NULL THEN	
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Cannot assign delivery if there is already one existing for the same order';
	END IF;

	INSERT INTO Delivery (delivery_person_id, order_id, vehicle_id) VALUES (delivery_person_id, order_id, vehicle_id);
	CALL UpdateOrderStatus(order_id, "IN_DELIVERY");
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE CancelDelivery(IN delivery_id INT)
BEGIN
	DECLARE order_id INT;
	DECLARE delivery_status ENUM ('ACCEPTED', 'IN_PROGRESS', 'COMPLETED', 'LATE');
	DECLARE order_status ENUM('ACCEPTED', 'IN_PREPARATION', 'IN_DELIVERY', 'COMPLETED', 'CANCELED');
	
    
    SELECT Delivery.order_id INTO order_id FROM Delivery WHERE Delivery.delivery_id = delivery_id LIMIT 1;
    SELECT Delivery.delivery_status INTO delivery_status FROM Delivery WHERE Delivery.delivery_id = delivery_id LIMIT 1;
    SELECT Delivery.order_id INTO order_id FROM Delivery WHERE Delivery.delivery_id = delivery_id;
	SELECT Pizza_Order.order_status INTO order_status FROM Pizza_Order WHERE Pizza_Order.order_id = order_id;

	-- Impact orders
	CASE delivery_status
		WHEN 'COMPLETED' THEN
			BEGIN	
				SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = 'Cannot cancel finished delivery.';
			END;
		WHEN 'LATE' THEN
			BEGIN	
				SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = 'Cannot cancel finished delivery.';
			END;
		ELSE
			CASE order_status
				WHEN 'COMPLETED' THEN
					BEGIN
					END;
				WHEN 'CANCELED' THEN
					BEGIN
					END;
				ELSE
					CALL UpdateOrderStatus(order_id, "CANCELED");	
			END CASE;
	END CASE;
	
	-- Delete delivery
	DELETE FROM Delivery WHERE Delivery.delivery_id = delivery_id;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE DeleteDelivery(IN delivery_id INT)
BEGIN
	DECLARE order_id INT;
	DECLARE delivery_status ENUM ('ACCEPTED', 'IN_PROGRESS', 'COMPLETED', 'LATE');
	DECLARE order_status ENUM('ACCEPTED', 'IN_PREPARATION', 'IN_DELIVERY', 'COMPLETED', 'CANCELED');
	
    
    SELECT Delivery.order_id INTO order_id FROM Delivery WHERE Delivery.delivery_id = delivery_id LIMIT 1;
    SELECT Delivery.delivery_status INTO delivery_status FROM Delivery WHERE Delivery.delivery_id = delivery_id LIMIT 1;
    SELECT Delivery.order_id INTO order_id FROM Delivery WHERE Delivery.delivery_id = delivery_id;
	SELECT Pizza_Order.order_status INTO order_status FROM Pizza_Order WHERE Pizza_Order.order_id = order_id;

	-- Impact orders
	CASE delivery_status
		WHEN 'COMPLETED' THEN
			BEGIN
			END;
		WHEN 'LATE' THEN
			BEGIN
			END;
		ELSE
			CASE order_status
				WHEN 'COMPLETED' THEN
					BEGIN
					END;
				WHEN 'CANCELED' THEN
					BEGIN
					END;
				ELSE
					CALL UpdateOrderStatus(order_id, "CANCELED");	
			END CASE;
	END CASE;
	
	-- Delete delivery
	DELETE FROM Delivery WHERE Delivery.delivery_id = delivery_id;
END //

DELIMITER ;

DELIMITER //
CREATE PROCEDURE FinishDelivery(IN delivery_id INT)
BEGIN    
    DECLARE order_datetime DATETIME;
    DECLARE elapsed_minutes INT;
    DECLARE order_id INT;
    
	UPDATE Delivery SET delivery_datetime = NOW() WHERE Delivery.delivery_id = delivery_id;
    SELECT Delivery.order_id INTO order_id FROM Delivery WHERE Delivery.delivery_id = delivery_id;

    -- Calculate the elapsed time
    SELECT Pizza_Order.order_datetime INTO order_datetime FROM Pizza_Order WHERE Pizza_Order.order_id = order_id LIMIT 1;
    SET elapsed_minutes = TIMESTAMPDIFF(MINUTE, NOW(), order_datetime);
    
    -- Update status 
    IF elapsed_minutes < 30 THEN
        UPDATE Delivery SET Delivery.delivery_status = 'COMPLETED' WHERE Delivery.delivery_id = delivery_id;
    ELSE
        UPDATE Delivery SET Delivery.delivery_status = 'LATE' WHERE Delivery.delivery_id = delivery_id;
    END IF;
    
	UPDATE Pizza_Order
	SET Pizza_Order.order_status = 'COMPLETED'
	WHERE Pizza_Order.order_id = order_id;
END // 

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetDeliveriesDetails()
BEGIN
    SELECT
		   Delivery.delivery_id,
		   Pizza_Order.order_id,
           Delivery.delivery_person_id,
           Delivery_Person.delivery_person_name,
           Delivery.vehicle_id,
           Vehicle.vehicle_type,
           Delivery.delivery_status,
           Delivery.delivery_datetime
    FROM Delivery
    LEFT JOIN Pizza_Order ON Delivery.order_id = Pizza_Order.order_id
    LEFT JOIN Delivery_Person ON Delivery_Person.delivery_person_id = Delivery.delivery_person_id
    LEFT JOIN Vehicle ON Vehicle.vehicle_id = Delivery.vehicle_id
    ORDER BY Delivery.delivery_datetime;
END; //

DELIMITER ;


DELIMITER //

CREATE PROCEDURE GetDeliveriesDetailsOfDeliveryPerson(IN delivery_person_id INT)
BEGIN
    SELECT
		   Delivery.delivery_id,
		   Pizza_Order.order_id,
           Delivery.delivery_person_id,
           Delivery_Person.delivery_person_name,
           Delivery.vehicle_id,
           Vehicle.vehicle_type,
           Delivery.delivery_status,
           Delivery.delivery_datetime
    FROM Delivery
    LEFT JOIN Pizza_Order ON Delivery.order_id = Pizza_Order.order_id
    LEFT JOIN Delivery_Person ON Delivery_Person.delivery_person_id = Delivery.delivery_person_id
    LEFT JOIN Vehicle ON Vehicle.vehicle_id = Delivery.vehicle_id
    WHERE Delivery_Person.delivery_person_id = delivery_person_id
    ORDER BY Delivery.delivery_datetime;
END; //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE UpdateDeliveryStatus(IN delivery_id INT, IN delivery_status ENUM('ACCEPTED', 'IN_PROGRESS', 'COMPLETED', 'LATE'))
BEGIN
	DECLARE order_status ENUM('ACCEPTED', 'IN_PREPARATION', 'IN_DELIVERY', 'COMPLETED', 'CANCELED');
    DECLARE order_id INT;
    DECLARE old_delivery_status ENUM ('ACCEPTED', 'IN_PROGRESS', 'COMPLETED', 'LATE');
    
	SELECT Delivery.delivery_status INTO old_delivery_status FROM Delivery WHERE Delivery.delivery_id = delivery_id LIMIT 1;
    SELECT Delivery.order_id INTO order_id FROM Delivery WHERE Delivery.delivery_id = delivery_id LIMIT 1;
	SELECT Pizza_Order.order_status INTO order_status FROM Pizza_Order WHERE Pizza_Order.order_id = order_id LIMIT 1;
	
	IF old_delivery_status != delivery_status THEN
    
		-- Prevent editing finished deliveries
		CASE old_delivery_status
			WHEN 'COMPLETED' THEN
				SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = 'Modifying deliveries with status LATE or COMPLETE is not allowed.';
			WHEN "LATE" THEN
				SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = 'Modifying deliveries with status LATE or COMPLETE is not allowed.';
			ELSE BEGIN END;
		END CASE;

		-- Update status
		UPDATE Delivery SET Delivery.delivery_status = delivery_status WHERE Delivery.delivery_id = delivery_id;
        
        -- Impact orders
		CASE delivery_status
			WHEN 'ACCEPTED' THEN
				IF order_status != 'IN_DELIVERY' THEN
					UPDATE Pizza_Order
					SET Pizza_Order.order_status = 'IN_DELIVERY'
					WHERE Pizza_Order.order_id = order_id;
				END IF;
			WHEN 'IN_PROGRESS' THEN
				IF order_status != 'IN_DELIVERY' THEN
					UPDATE Pizza_Order
					SET Pizza_Order.order_status = 'IN_DELIVERY'
					WHERE Pizza_Order.order_id = order_id;
				END IF;
			WHEN 'COMPLETED' THEN
				IF order_status != 'COMPLETED' THEN
					UPDATE Pizza_Order
					SET Pizza_Order.order_status = 'COMPLETED'
					WHERE Pizza_Order.order_id = order_id;
				END IF;
			WHEN 'LATE' THEN
				IF order_status != 'COMPLETED' THEN
					UPDATE Pizza_Order
					SET Pizza_Order.order_status = 'COMPLETED'
					WHERE Pizza_Order.order_id = order_id;
				END IF;
				CALL RefundOrder(order_id);
			ELSE BEGIN END;
		END CASE;
	END IF;
END //

DELIMITER ;

-- ############### DIVERS ###############

DELIMITER //

CREATE PROCEDURE CalculateAverageDeliveryTime()
BEGIN
   DECLARE avg_delivery_time DECIMAL(10, 2);

   -- Calculer le temps moyen de livraison des commandes complétées
   SELECT AVG(TIMESTAMPDIFF(MINUTE, P.order_datetime, D.delivery_datetime)) INTO avg_delivery_time
   FROM Pizza_Order P
   JOIN Delivery D ON P.order_id = D.order_id
   WHERE P.order_status = 'COMPLETED'
   AND D.delivery_datetime IS NOT NULL;

   SELECT avg_delivery_time AS average_delivery_time;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE CalculateAverageOrderPrice()
BEGIN
   DECLARE average_price DECIMAL(10,2);

   SELECT AVG(pizza_base_price) INTO average_price
   FROM Pizza_Order
   JOIN Pizza ON Pizza_Order.pizza_id = Pizza.pizza_id;

   SELECT average_price AS average_order_price;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetCustomersAboveAverage()
BEGIN
   DECLARE average_orders DECIMAL(10,2);

   -- Calculer la moyenne du nombre de commandes par client
   SELECT AVG(order_count) INTO average_orders
   FROM (
      SELECT COUNT(*) AS order_count
      FROM Pizza_Order
      GROUP BY customer_id
   ) AS order_counts;

   -- Récupérer les clients ayant un nombre de commandes supérieur à la moyenne
   SELECT c.customer_id, c.customer_name
   FROM Customer c
   JOIN (
      SELECT customer_id, COUNT(*) AS order_count
      FROM Pizza_Order
      GROUP BY customer_id
   ) AS customer_orders ON c.customer_id = customer_orders.customer_id
   WHERE customer_orders.order_count > average_orders;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE CalculateMonthlyRevenue()
BEGIN
   DECLARE start_date DATE;
   DECLARE end_date DATE;
   DECLARE revenue DECIMAL(10,2);

   -- Calculer la date de début et de fin du mois actuel
   SET start_date = DATE_FORMAT(NOW(), '%Y-%m-01');
   SET end_date = LAST_DAY(start_date);

   -- Calculer le chiffre d'affaires du mois actuel
   SELECT SUM(pizza_base_price) INTO revenue
   FROM Pizza_Order
   JOIN Pizza ON Pizza_Order.pizza_id = Pizza.pizza_id
   WHERE order_status = 'COMPLETED'
   AND order_datetime >= start_date
   AND order_datetime <= end_date;

   SELECT revenue AS monthly_revenue;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE CalculateOrderCountByCustomer()
BEGIN
   -- Calculer le nombre de commandes par client
   SELECT c.customer_id, c.customer_name, COUNT(*) AS order_count
   FROM Customer c
   LEFT JOIN Pizza_Order po ON c.customer_id = po.customer_id
   GROUP BY c.customer_id, c.customer_name;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GenerateSalesDistributionByPizzaSize()
BEGIN
   -- Table temporaire pour stocker les résultats intermédiaires
   CREATE TEMPORARY TABLE temp_sales_distribution (
      pizza_size ENUM('SMALL', 'MEDIUM', 'LARGE'),
      sales_count INT
   );

   -- Insérer les données de répartition des ventes par taille de pizza dans la table temporaire
   INSERT INTO temp_sales_distribution (pizza_size, sales_count)
   SELECT pizza_size, COUNT(*) AS count
   FROM Pizza_Order
   WHERE order_status = 'COMPLETED'
   GROUP BY pizza_size;

   -- Sélectionner le total des ventes
   SET @total_sales := (SELECT SUM(sales_count) FROM temp_sales_distribution);

   -- Sélectionner la répartition des ventes par taille de pizza avec la part dans le total des ventes
   SELECT pizza_size, sales_count, (sales_count / @total_sales) * 100 AS sales_percentage
   FROM temp_sales_distribution;

   -- Supprimer la table temporaire
   DROP TABLE IF EXISTS temp_sales_distribution;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE CalculateTotalRevenue()
BEGIN
   DECLARE total_revenue DECIMAL(10,2);

   -- Calculer le chiffre d'affaires total
   SELECT SUM(pizza_base_price) INTO total_revenue
   FROM Pizza_Order
   JOIN Pizza ON Pizza_Order.pizza_id = Pizza.pizza_id
   WHERE order_status = 'COMPLETED';

   SELECT total_revenue AS total_revenue;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE FindUnusedVehicles()
BEGIN
   SELECT V.vehicle_id, V.vehicle_type, V.vehicle_model
   FROM Vehicle V
   LEFT JOIN Delivery_Person DP ON V.vehicle_id = DP.vehicle_id
   WHERE DP.vehicle_id IS NULL;
END //

DELIMITER ;

-- ############### FUNCTIONS ###############

DELIMITER //

CREATE FUNCTION CalculateAdjustedPrice(pizza_id INT, pizza_size ENUM('SMALL', 'MEDIUM', 'LARGE')) RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE basePrice DECIMAL(10,2);
    DECLARE adjustedPrice DECIMAL(10,2);

    -- Retrieve the base price and pizza size from the Pizza table based on the pizza ID
    SELECT Pizza.pizza_base_price INTO basePrice FROM Pizza WHERE Pizza.pizza_id = pizza_id LIMIT 1;

    -- Calculate the adjusted price based on the retrieved base price and pizza size
    CASE
        WHEN pizza_size = 'large' THEN SET adjustedPrice = basePrice * 1.33;
        WHEN pizza_size = 'small' THEN SET adjustedPrice = basePrice * 0.66;
        ELSE SET adjustedPrice = basePrice;
    END CASE;

    RETURN adjustedPrice;
END //

DELIMITER ;

-- ############### ORDER ###############


DELIMITER //

-- CUSTOMER PERSPECTIVE
CREATE PROCEDURE CancelOrder(
    IN order_id INT
)
BEGIN
    DECLARE current_status ENUM('ACCEPTED', 'IN_PREPARATION', 'IN_DELIVERY', 'COMPLETED', 'CANCELED');
    
    SELECT Pizza_order.order_status INTO current_status FROM Pizza_Order WHERE Pizza_Order.order_id = order_id;
    
    -- If possible cancel the order
    IF current_status = 'ACCEPTED' THEN
		CALL UpdateOrderStatus(order_id, "CANCELED");
	ELSE
		SELECT -1;
    END IF;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetOrderCountByCustomerId(IN p_customer_id INT)
BEGIN
   SELECT COUNT(*) AS order_count
   FROM Pizza_Order
   WHERE customer_id = p_customer_id
	AND Pizza_Order.order_status = "COMPLETED";
END //

DELIMITER ;


DELIMITER //
CREATE PROCEDURE GetOrderDetails(IN order_id INT)
BEGIN
    SELECT Customer.customer_name,
           Pizza.pizza_name,
           Pizza_Order.pizza_size AS pizza_size,
           CalculateAdjustedPrice(Pizza.pizza_id, Pizza_Order.pizza_size) AS final_price,
           Pizza_Order.order_status,
           Delivery.delivery_status,
           Pizza_Order.order_datetime
    FROM Pizza_Order
    LEFT JOIN Customer ON Pizza_Order.customer_id = Customer.customer_id
    LEFT JOIN Pizza ON Pizza_Order.Pizza_id = Pizza.pizza_id
    LEFT JOIN Delivery ON order_id = Delivery.order_id
    WHERE Pizza_Order.order_id = order_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE GetOrdersDetailsForCustomer(IN customer_id INT)
BEGIN
    SELECT 
		   Pizza_Order.order_id,
		   Customer.customer_name, 
		   Pizza.pizza_name,
           Pizza_order.pizza_size,
           CalculateAdjustedPrice(Pizza.pizza_id, Pizza_Order.pizza_size) AS adjusted_price,
           Pizza_Order.order_status,
           Delivery.delivery_status,
           Pizza_Order.order_datetime
    FROM Pizza_Order
    LEFT JOIN Customer ON Pizza_Order.customer_id = Customer.customer_id
    LEFT JOIN Pizza ON Pizza_Order.Pizza_id = Pizza.pizza_id
    LEFT JOIN Delivery ON Pizza_Order.order_id = Delivery.order_id
    WHERE Pizza_Order.customer_id = customer_id
    ORDER BY Pizza_Order.order_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE GetOrdersDetails()
BEGIN
    SELECT 
		   Pizza_Order.order_id,
		   Customer.customer_name, 
		   Pizza.pizza_name,
           Pizza_order.pizza_size,
           CalculateAdjustedPrice(Pizza.pizza_id, Pizza_Order.pizza_size) AS adjusted_price,
           Pizza_Order.order_status,
           Delivery.delivery_status,
           Pizza_Order.order_datetime
    FROM Pizza_Order
    LEFT JOIN Customer ON Pizza_Order.customer_id = Customer.customer_id
    LEFT JOIN Pizza ON Pizza_Order.Pizza_id = Pizza.pizza_id
    LEFT JOIN Delivery ON Pizza_Order.order_id = Delivery.order_id
    ORDER BY Pizza_Order.order_id;
END //
DELIMITER ;

DELIMITER //

CREATE PROCEDURE PlaceOrder(
    IN p_customer_id INT,
    IN p_pizza_id INT,
    IN p_pizza_size ENUM('SMALL', 'MEDIUM', 'LARGE')
)
BEGIN
    DECLARE v_pizza_price DECIMAL(10, 2);
    DECLARE v_total_price DECIMAL(10, 2);
    DECLARE v_customer_balance DECIMAL(10, 2);
    DECLARE v_order_count INT;
    
    -- Calculate pizza price
    SELECT pizza_base_price INTO v_pizza_price FROM Pizza WHERE pizza_id = p_pizza_id LIMIT 1;
    SELECT CalculateAdjustedPrice(p_pizza_id, p_pizza_size) INTO v_total_price LIMIT 1;
    
    -- Check balance and order count
    SELECT customer_balance INTO v_customer_balance FROM Customer WHERE customer_id = p_customer_id;
    SELECT COUNT(*) INTO v_order_count FROM Pizza_Order WHERE customer_id = p_customer_id AND Pizza_Order.order_status = "COMPLETED";
    
    IF (v_customer_balance >= v_total_price or (v_order_count+1) % 10 = 0) THEN
        -- Place order
        INSERT INTO Pizza_Order (order_status, order_datetime, pizza_id, pizza_size, customer_id)
        VALUES ('ACCEPTED', NOW(), p_pizza_id, p_pizza_size, p_customer_id);
        
        -- Update balance if order count is not divisible by 10
        IF (v_order_count+1) % 10 != 0 THEN
            UPDATE Customer SET customer_balance = v_customer_balance - v_total_price WHERE customer_id = p_customer_id;
        END IF;
    ELSE
        SELECT -1;
    END IF;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE RefundOrder(in order_id INT)
BEGIN
	DECLARE total_price DECIMAL(10, 2);
	DECLARE pizza_id INT;
    DECLARE pizza_size ENUM("SMALL", "MEDIUM", "LARGE");
    DECLARE customer_id INT;
    
    SELECT Pizza_Order.pizza_id INTO pizza_id FROM Pizza_Order WHERE Pizza_Order.order_id = order_id;
    SELECT Pizza_Order.pizza_size INTO pizza_size FROM Pizza_Order WHERE Pizza_Order.order_id = order_id;
    SELECT CalculateAdjustedPrice(pizza_id, pizza_size) INTO total_price;
    SELECT Pizza_Order.customer_id  INTO customer_id FROM Pizza_Order WHERE Pizza_order.order_id = order_id;
    
    UPDATE Customer SET Customer.customer_balance = Customer.customer_balance + total_price WHERE Customer.customer_id = customer_id;
END; //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE UpdateOrderStatus(IN order_id INT, IN order_status ENUM('ACCEPTED', 'IN_PREPARATION', 'IN_DELIVERY', 'COMPLETED', 'CANCELED'))
BEGIN
	DECLARE delivery_status ENUM ('ACCEPTED', 'IN_PROGRESS', 'COMPLETED', 'LATE');
	DECLARE old_order_status ENUM('ACCEPTED', 'IN_PREPARATION', 'IN_DELIVERY', 'COMPLETED', 'CANCELED');
    SET delivery_status = NULL;
    SELECT Pizza_Order.order_status INTO old_order_status FROM Pizza_Order WHERE Pizza_Order.order_id = order_id LIMIT 1;
    SELECT Delivery.delivery_status INTO delivery_status FROM Delivery WHERE Delivery.order_id = order_id LIMIT 1;
	
    IF old_order_status != order_status THEN
    
		-- Prevent editing finished orders
		CASE old_order_status
			WHEN 'CANCELED' THEN
				SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = 'Modifying orders with status CANCELED or COMPLETE is not allowed.';
			WHEN "COMPLETED" THEN
				SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = 'Modifying orders with status CANCELED or COMPLETE is not allowed.';
			ELSE BEGIN END;
		END CASE;
        
		IF delivery_status IS NULL AND order_status = 'IN_DELIVERY' THEN
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'Setting order with no assigned delivery to IN_DELIVERY is not allowed';
		END IF;

        
        UPDATE Pizza_Order SET Pizza_Order.order_status = order_status WHERE Pizza_Order.order_id = order_id;
        
        -- Impact changes on deliveries
		CASE order_status
			WHEN 'ACCEPTED' THEN
				DELETE FROM Delivery
				WHERE Delivery.order_id = order_id;
			WHEN 'IN_PREPARATION' THEN
				DELETE FROM Delivery
				WHERE Delivery.order_id = order_id;
			WHEN 'IN_DELIVERY' THEN
				IF delivery_status != NULL AND delivery_status != "ACCEPTED" AND delivery_status != "IN_PROGRESS" THEN
					UPDATE Delivery
					SET Delivery.delivery_status = "ACCEPTED"
					WHERE Delivery.order_id = order_id;
				END IF;
			WHEN "CANCELED" THEN
				DELETE FROM Delivery
				WHERE Delivery.order_id = order_id;
				CALL RefundOrder(order_id);
			WHEN "COMPLETED" THEN
				IF delivery_status != "COMPLETED" THEN
					UPDATE Delivery
					SET Delivery.delivery_status = "COMPLETED"
					WHERE Delivery.order_id = order_id;
				END IF;
			ELSE BEGIN END;
		END CASE;
	END IF;
END //

DELIMITER ;


DELIMITER //
CREATE PROCEDURE GetWaitingForDeliveryOrdersDetails()
BEGIN
    SELECT 
		   Pizza_Order.order_id,
		   Customer.customer_name, 
		   Pizza.pizza_name,
           Pizza_order.pizza_size,
           CalculateAdjustedPrice(Pizza.pizza_id, Pizza_Order.pizza_size) AS adjusted_price,
           Pizza_Order.order_status,
           Delivery.delivery_status,
           Pizza_Order.order_datetime
    FROM Pizza_Order
    LEFT JOIN Customer ON Pizza_Order.customer_id = Customer.customer_id
    LEFT JOIN Pizza ON Pizza_Order.Pizza_id = Pizza.pizza_id
    LEFT JOIN Delivery ON Pizza_Order.order_id = Delivery.order_id
    WHERE Delivery.delivery_id IS NULL AND (Pizza_Order.order_status = "ACCEPTED" OR Pizza_Order.order_status = "IN_PREPARATION")
    ORDER BY Pizza_Order.order_id;
END //
DELIMITER ;

-- ############### STATS ###############

DELIMITER //

CREATE PROCEDURE GetBestCustomer()
BEGIN
   SELECT c.customer_id, c.customer_name, COUNT(*) AS num_completed_orders
   FROM Pizza_Order po
   JOIN Customer c ON c.customer_id = po.customer_id
   WHERE po.order_status = 'COMPLETED'
   GROUP BY c.customer_id
   ORDER BY num_completed_orders DESC
   LIMIT 1;
   
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetBestDeliveryPerson()
BEGIN
   SELECT DP.delivery_person_id, DP.delivery_person_name, V.vehicle_type, V.vehicle_model, COUNT(*) AS num_completed_deliveries
   FROM Delivery D
   JOIN Delivery_Person DP ON D.delivery_person_id = DP.delivery_person_id
   JOIN Vehicle V ON DP.vehicle_id = V.vehicle_id
   WHERE D.delivery_status = 'COMPLETED'
   GROUP BY DP.delivery_person_id, DP.delivery_person_name, V.vehicle_type, V.vehicle_model
   ORDER BY num_completed_deliveries DESC
   LIMIT 1;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetMostPopularIngredient()
BEGIN
    SELECT I.ingredient_id, I.ingredient_name, COUNT(*) AS ingredient_count
    FROM Has_Ingredient HI
    JOIN Pizza_Order PO ON HI.pizza_id = PO.pizza_id
    JOIN Ingredient I ON HI.ingredient_id = I.ingredient_id
    WHERE PO.order_status = 'COMPLETED'
    GROUP BY I.ingredient_id, I.ingredient_name
    ORDER BY ingredient_count DESC
    LIMIT 1;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetMostOrderedPizza()
BEGIN
   -- Sélectionner la pizza la plus commandée avec son ID, nom et nombre de commandes
   SELECT PO.pizza_id, P.pizza_name, COUNT(*) AS order_count
   FROM Pizza_Order PO
   JOIN Pizza P ON PO.pizza_id = P.pizza_id
   WHERE PO.order_status = 'COMPLETED'
   GROUP BY PO.pizza_id, P.pizza_name
   ORDER BY order_count DESC
   LIMIT 1;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetWorstDeliveryPerson()
BEGIN
   SELECT DP.delivery_person_id, DP.delivery_person_name, V.vehicle_type, V.vehicle_model, COUNT(*) AS num_late_deliveries
   FROM Delivery D
   JOIN Delivery_Person DP ON D.delivery_person_id = DP.delivery_person_id
   JOIN Vehicle V ON DP.vehicle_id = V.vehicle_id
   WHERE D.delivery_status = 'LATE'
   GROUP BY DP.delivery_person_id, DP.delivery_person_name, V.vehicle_type, V.vehicle_model
   ORDER BY num_late_deliveries DESC
   LIMIT 1;
END //

DELIMITER ;

-- ############### VEHICLE ###############

DELIMITER //

CREATE PROCEDURE GetAllVehicleModelUnassigned()
BEGIN
    SELECT *
    FROM Vehicle
    WHERE vehicle_id NOT IN (SELECT vehicle_id FROM Delivery_Person WHERE vehicle_id IS NOT NULL);
END //

DELIMITER ;