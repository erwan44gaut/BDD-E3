
DELIMITER //

CREATE PROCEDURE CancelOrder(
    IN order_id INT
)
BEGIN
    DECLARE current_status ENUM('ACCEPTED', 'IN_PREPARATION', 'IN_DELIVERY', 'COMPLETED');
    
    SELECT order_status INTO current_status FROM Pizza_Order WHERE Pizza_Order.order_id = order_id;
    
    -- If possible cancel the order
    IF current_status = 'ACCEPTED' THEN
		UPDATE Pizza_Order SET Pizza_Order.order_status = 'CANCELED' WHERE Pizza_Order.order_id = order_id;
        SELECT 1; -- Return 1 to indicate successful cancellation
    ELSE
        SELECT 0; -- Return 0 to indicate that the order cannot be canceled
    END IF;
END //

DELIMITER ;