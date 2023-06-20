DELIMITER //

CREATE PROCEDURE FinishDelivery(IN delivery_id INT)
BEGIN    
    DECLARE order_datetime DATETIME;
    DECLARE elapsed_minutes INT;
    DECLARE order_id INT;
    
	UPDATE Delivery SET delivery_datetime = NOW() WHERE Delivery.delivery_id = delivery_id;
    SELECT Pizza_Order.order_id INTO order_id FROM Pizza_Order, Delivery WHERE Pizza_Order.order_id = Delivery.order_id;

    -- Calculate the elapsed time
    SELECT Pizza_Order.order_datetime INTO order_datetime FROM Pizza_Order WHERE Pizza_Order.order_id = order_id;    
    SET elapsed_minutes = TIMESTAMPDIFF(MINUTE, NOW(), order_datetime);
    
    -- Update status 
    IF elapsed_minutes < 30 THEN
        UPDATE Delivery SET Delivery.delivery_status = 'COMPLETE' WHERE Delivery.delivery_id = delivery_id;
    ELSE
        UPDATE Delivery SET Delivery.delivery_status = 'LATE' WHERE Delivery.delivery_id = delivery_id;
    END IF;    
END // 

DELIMITER ;