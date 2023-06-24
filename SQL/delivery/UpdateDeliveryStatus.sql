DELIMITER //

CREATE PROCEDURE UpdateDeliveryStatus(IN delivery_id INT, IN delivery_status ENUM('ACCEPTED', 'IN_PROGRESS', 'COMPLETED', 'LATE'))
BEGIN
	DECLARE order_status ENUM('ACCEPTED', 'IN_PREPARATION', 'IN_DELIVERY', 'COMPLETED', 'CANCELED');
    DECLARE order_id INT;
    DECLARE old_delivery_status ENUM ('ACCEPTED', 'IN_PROGRESS', 'COMPLETED', 'LATE');
    
	SELECT Delivery.delivery_status INTO old_delivery_status FROM Delivery WHERE Delivery.delivery_id = delivery_id LIMIT 1;
    SELECT Delivery.order_id INTO order_id FROM Delivery WHERE Delivery.delivery_id = delivery_id LIMIT 1;
	SELECT Pizza_Order.order_status INTO order_status FROM Pizza_Order WHERE Pizza_Order.order_id = order_id LIMIT 1;
	
	IF old_delivery_status != delivery_status THEN
    
		-- Prevent editing finished deliveries
		CASE old_delivery_status
			WHEN 'COMPLETED' THEN
				SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = 'Modifying deliveries with status LATE or COMPLETE is not allowed.';
			WHEN "LATE" THEN
				SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = 'Modifying deliveries with status LATE or COMPLETE is not allowed.';
			ELSE BEGIN END;
		END CASE;

		-- Update status
		UPDATE Delivery SET Delivery.delivery_status = delivery_status WHERE Delivery.delivery_id = delivery_id;
        
        -- Impact orders
		CASE delivery_status
			WHEN 'ACCEPTED' THEN
				IF order_status != 'IN_DELIVERY' THEN
					UPDATE Pizza_Order
					SET Pizza_Order.order_status = 'IN_DELIVERY'
					WHERE Pizza_Order.order_id = order_id;
				END IF;
			WHEN 'IN_PROGRESS' THEN
				IF order_status != 'IN_DELIVERY' THEN
					UPDATE Pizza_Order
					SET Pizza_Order.order_status = 'IN_DELIVERY'
					WHERE Pizza_Order.order_id = order_id;
				END IF;
			WHEN 'COMPLETED' THEN
				IF order_status != 'COMPLETED' THEN
					UPDATE Pizza_Order
					SET Pizza_Order.order_status = 'COMPLETED'
					WHERE Pizza_Order.order_id = order_id;
				END IF;
			WHEN 'LATE' THEN
				IF order_status != 'COMPLETED' THEN
					UPDATE Pizza_Order
					SET Pizza_Order.order_status = 'COMPLETED'
					WHERE Pizza_Order.order_id = order_id;
				END IF;
				CALL RefundOrder(order_id);
			ELSE BEGIN END;
		END CASE;
	END IF;
END //

DELIMITER ;
