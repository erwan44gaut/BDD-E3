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