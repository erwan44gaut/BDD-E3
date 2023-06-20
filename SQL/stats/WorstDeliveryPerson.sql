DELIMITER //
CREATE PROCEDURE GetWorstDeliveryPersons()
BEGIN
    -- Declare variables
    DECLARE min_difference INT;
    
    -- Find the minimum difference between passed deliveries and failed deliveries
    SELECT MIN(passed_deliveries - failed_deliveries) INTO min_difference
    FROM (
        SELECT delivery_person_id, 
               SUM(CASE WHEN state = 'PASSED' THEN 1 ELSE 0 END) AS passed_deliveries,
               SUM(CASE WHEN state = 'FAILED' THEN 1 ELSE 0 END) AS failed_deliveries
        FROM Delivery
        GROUP BY delivery_person_id
    ) AS temp;
    
    -- Retrieve delivery persons with the smallest difference
    SELECT delivery_person_id, passed_deliveries - failed_deliveries AS difference
    FROM (
        SELECT delivery_person_id, 
               SUM(CASE WHEN state = 'PASSED' THEN 1 ELSE 0 END) AS passed_deliveries,
               SUM(CASE WHEN state = 'FAILED' THEN 1 ELSE 0 END) AS failed_orders
        FROM orders
        GROUP BY delivery_person_id
    ) AS temp
    WHERE total_orders - failed_orders = min_difference;
END;
DELIMITER;
