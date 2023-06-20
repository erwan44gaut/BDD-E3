CREATE PROCEDURE FinishDelivery(IN delivery_id INT)
BEGIN
    UPDATE Delivery SET delivery_datetime = NOW() WHERE delivery_id = delivery_id;
    
    DECLARE order_time DATETIME;
    DECLARE elapsed_minutes INT;
    
    -- Calculate the elapsed time
    SELECT order_datetime INTO order_time FROM Pizza_Order WHERE order_id = delivery_id;
    SET elapsed_minutes = TIMESTAMPDIFF(MINUTE, order_time, NOW());
    
    -- Update status 
    IF elapsed_minutes < 30 THEN
        UPDATE Delivery SET delivery_status = 'COMPLETE' WHERE delivery_id = delivery_id;
        SELECT 'Delivery finished successfully.' AS message;
    ELSE
        UPDATE Delivery SET delivery_status = 'LATE' WHERE delivery_id = delivery_id;
        SELECT 'Delivery finished late.' AS message;
    END IF;    
END;
