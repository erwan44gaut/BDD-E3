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