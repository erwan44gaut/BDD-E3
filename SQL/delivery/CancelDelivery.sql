DELIMITER //

CREATE PROCEDURE CancelDelivery(IN delivery_id INT)
BEGIN
	DECLARE order_id INT;
	DECLARE delivery_status ENUM ('ACCEPTED', 'IN_PROGRESS', 'COMPLETED', 'LATE');
	DECLARE order_status ENUM('ACCEPTED', 'IN_PREPARATION', 'IN_DELIVERY', 'COMPLETED', 'CANCELED');
	
    
    SELECT Delivery.order_id INTO order_id FROM Delivery WHERE Delivery.delivery_id = delivery_id LIMIT 1;
    SELECT Delivery.delivery_status INTO delivery_status FROM Delivery WHERE Delivery.delivery_id = delivery_id LIMIT 1;
    SELECT Delivery.order_id INTO order_id FROM Delivery WHERE Delivery.delivery_id = delivery_id;
	SELECT Pizza_Order.order_status INTO order_status FROM Pizza_Order WHERE Pizza_Order.order_id = order_id;

	-- Impact orders
	CASE delivery_status
		WHEN 'COMPLETED' THEN
			BEGIN	
				SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = 'Cannot cancel finished delivery.';
			END;
		WHEN 'LATE' THEN
			BEGIN	
				SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = 'Cannot cancel finished delivery.';
			END;
		ELSE
			CASE order_status
				WHEN 'COMPLETED' THEN
					BEGIN
					END;
				WHEN 'CANCELED' THEN
					BEGIN
					END;
				ELSE
					CALL UpdateOrderStatus(order_id, "CANCELED");	
			END CASE;
	END CASE;
	
	-- Delete delivery
	DELETE FROM Delivery WHERE Delivery.delivery_id = delivery_id;
END //

DELIMITER ;