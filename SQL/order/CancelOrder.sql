
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