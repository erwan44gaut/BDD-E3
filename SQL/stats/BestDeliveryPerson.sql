DELIMITER //
CREATE PROCEDURE GetBestDeliveryPersons()
BEGIN
    -- Declare variables
    DECLARE max_difference INT;
    
    -- Find the maximum difference between passed deliveries and failed deliveries
    SELECT MAX(passed_deliveries - failed_deliveries) INTO max_difference
    FROM (
        SELECT delivery_person_id, 
               SUM(CASE WHEN state = 'PASSED' THEN 1 ELSE 0 END) AS passed_deliveries,
               SUM(CASE WHEN state = 'FAILED' THEN 1 ELSE 0 END) AS failed_deliveries
        FROM Delivery
        GROUP BY delivery_person_id
    ) AS temp;
    
    -- Retrieve delivery persons with the largest difference
    SELECT delivery_person_id, passed_deliveries - failed_deliveries AS difference
    FROM (
        SELECT delivery_person_id, 
               SUM(CASE WHEN state = 'PASSED' THEN 1 ELSE 0 END) AS passed_deliveries,
               SUM(CASE WHEN state = 'FAILED' THEN 1 ELSE 0 END) AS failed_orders
        FROM orders
        GROUP BY delivery_person_id
    ) AS temp
    WHERE total_orders - failed_orders = max_difference;
END;
DELIMITER;
