DELIMITER //

CREATE PROCEDURE UpdateOrderStatus(IN order_id INT, IN order_status ENUM('ACCEPTED', 'IN_PREPARATION', 'IN_DELIVERY', 'COMPLETED', 'CANCELED'))
BEGIN
	DECLARE delivery_status ENUM ('ACCEPTED', 'IN_PROGRESS', 'COMPLETED', 'LATE');
	DECLARE old_order_status ENUM('ACCEPTED', 'IN_PREPARATION', 'IN_DELIVERY', 'COMPLETED', 'CANCELED');
    SET delivery_status = NULL;
    SELECT Pizza_Order.order_status INTO old_order_status FROM Pizza_Order WHERE Pizza_Order.order_id = order_id LIMIT 1;
    SELECT Delivery.delivery_status INTO delivery_status FROM Delivery WHERE Delivery.order_id = order_id LIMIT 1;
	
    IF old_order_status != order_status THEN
    
		-- Prevent editing finished orders
		CASE old_order_status
			WHEN 'CANCELED' THEN
				SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = 'Modifying orders with status CANCELED or COMPLETE is not allowed.';
			WHEN "COMPLETED" THEN
				SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = 'Modifying orders with status CANCELED or COMPLETE is not allowed.';
			ELSE BEGIN END;
		END CASE;
        
		IF delivery_status IS NULL AND order_status = 'IN_DELIVERY' THEN
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'Setting order with no assigned delivery to IN_DELIVERY is not allowed';
		END IF;

        
        UPDATE Pizza_Order SET Pizza_Order.order_status = order_status WHERE Pizza_Order.order_id = order_id;
        
        -- Impact changes on deliveries
		CASE order_status
			WHEN 'ACCEPTED' THEN
				DELETE FROM Delivery
				WHERE Delivery.order_id = order_id;
			WHEN 'IN_PREPARATION' THEN
				DELETE FROM Delivery
				WHERE Delivery.order_id = order_id;
			WHEN 'IN_DELIVERY' THEN
				IF delivery_status != NULL AND delivery_status != "ACCEPTED" AND delivery_status != "IN_PROGRESS" THEN
					UPDATE Delivery
					SET Delivery.delivery_status = "ACCEPTED"
					WHERE Delivery.order_id = order_id;
				END IF;
			WHEN "CANCELED" THEN
				DELETE FROM Delivery
				WHERE Delivery.order_id = order_id;
				CALL RefundOrder(order_id);
			WHEN "COMPLETED" THEN
				IF delivery_status != "COMPLETED" THEN
					UPDATE Delivery
					SET Delivery.delivery_status = "COMPLETED"
					WHERE Delivery.order_id = order_id;
				END IF;
			ELSE BEGIN END;
		END CASE;
	END IF;
END //

DELIMITER ;
