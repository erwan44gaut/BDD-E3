DELIMITER //

CREATE PROCEDURE GetOrderCountByCustomerId(IN p_customer_id INT)
BEGIN
   SELECT COUNT(*) AS order_count
   FROM Pizza_Order
   WHERE customer_id = p_customer_id
	AND Pizza_Order.order_status = "COMPLETED";
END //

DELIMITER ;