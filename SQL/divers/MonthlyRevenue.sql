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