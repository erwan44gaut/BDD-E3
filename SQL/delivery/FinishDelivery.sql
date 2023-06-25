DELIMITER //
CREATE PROCEDURE FinishDelivery(IN delivery_id INT)
BEGIN    
    DECLARE order_datetime DATETIME;
    DECLARE elapsed_minutes INT;
    DECLARE order_id INT;
    
	UPDATE Delivery SET delivery_datetime = NOW() WHERE Delivery.delivery_id = delivery_id;
    SELECT Delivery.order_id INTO order_id FROM Delivery WHERE Delivery.delivery_id = delivery_id;

    -- Calculate the elapsed time
    SELECT Pizza_Order.order_datetime INTO order_datetime FROM Pizza_Order WHERE Pizza_Order.order_id = order_id LIMIT 1;
    SET elapsed_minutes = TIMESTAMPDIFF(MINUTE, NOW(), order_datetime);
    
    -- Update status 
    IF elapsed_minutes < 30 THEN
        UPDATE Delivery SET Delivery.delivery_status = 'COMPLETED' WHERE Delivery.delivery_id = delivery_id;
    ELSE
        UPDATE Delivery SET Delivery.delivery_status = 'LATE' WHERE Delivery.delivery_id = delivery_id;
    END IF;
    
	UPDATE Pizza_Order
	SET Pizza_Order.order_status = 'COMPLETED'
	WHERE Pizza_Order.order_id = order_id;
END // 

DELIMITER ;