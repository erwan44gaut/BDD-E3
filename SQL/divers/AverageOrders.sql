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