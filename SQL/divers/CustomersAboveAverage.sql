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