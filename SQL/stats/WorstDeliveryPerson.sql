DELIMITER //
CREATE PROCEDURE GetBestDeliveryPersons()
BEGIN
    DECLARE min_difference INT;
    
    SELECT MIN(passed_deliveries - failed_deliveries) INTO max_difference
    FROM (
        SELECT delivery_person_id, 
               SUM(CASE WHEN delivery_status = 'PASSED' THEN 1 ELSE 0 END) AS passed_deliveries,
               SUM(CASE WHEN (delivery_status = 'LATE' OR delivery_status = 'CANCELED') THEN 1 ELSE 0 END) AS failed_orders
        FROM Delivery
        GROUP BY delivery_person_id
    ) AS temp;
    
    SELECT delivery_person_id, passed_deliveries - failed_deliveries AS difference
    FROM (
        SELECT delivery_person_id, 
               SUM(CASE WHEN delivery_status = 'PASSED' THEN 1 ELSE 0 END) AS passed_deliveries,
               SUM(CASE WHEN (delivery_status = 'LATE' OR delivery_status = 'CANCELED') THEN 1 ELSE 0 END) AS failed_orders
        FROM orders
        GROUP BY delivery_person_id
    ) AS temp
    WHERE passed_deliveries - failed_deliveries = min_difference;
END//
