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