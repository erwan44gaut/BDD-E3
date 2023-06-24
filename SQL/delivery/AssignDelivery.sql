DELIMITER //

CREATE PROCEDURE AssignDelivery(IN delivery_person_id INT, order_id INT)
BEGIN
	DECLARE delivery_id INT;
	DECLARE order_status ENUM('ACCEPTED', 'IN_PREPARATION', 'IN_DELIVERY', 'COMPLETED', 'CANCELED');
    
    SELECT Delivery.delivery_id INTO delivery_id FROM Delivery WHERE Delivery.order_id = order_id;
	SELECT Pizza_Order.order_status INTO order_status FROM Pizza_Order WHERE Pizza_Order.order_id = order_id;

    IF order_status = "COMPLETED" OR order_status = "CANCELED" OR order_status = "IN_DELIVERY" THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Cannot assign delivery to a finished order or one that already has an assigned delivery';
    END IF;

    IF delivery_id IS NOT NULL THEN	
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Cannot assign delivery if there is already one existing for the same order';
	END IF;
    
	INSERT INTO Delivery (delivery_person_id, order_id) VALUES (delivery_person_id, order_id);
	CALL UpdateOrderStatus(order_id, "IN_DELIVERY");
END //

DELIMITER ;
