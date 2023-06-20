DELIMITER //
CREATE PROCEDURE GetBestCustomers()
BEGIN
    SELECT customer_id, COUNT(*) AS total_deliveries
    FROM Delivery
    GROUP BY customer_id
    HAVING COUNT(*) = (SELECT MAX(delivery_count) FROM (SELECT COUNT(*) AS delivery_count FROM Delivery GROUP BY customer_id) AS temp);
END
DELIMITER;