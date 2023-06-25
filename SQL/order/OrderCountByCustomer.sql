DELIMITER //

CREATE PROCEDURE GetOrderCountByCustomerId(IN p_customer_id INT, OUT p_order_count INT)
BEGIN
   SELECT COUNT(*) INTO p_order_count
   FROM Pizza_Order
   WHERE customer_id = p_customer_id;
END //

DELIMITER ;