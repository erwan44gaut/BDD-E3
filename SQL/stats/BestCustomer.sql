DELIMITER //

CREATE PROCEDURE GetBestCustomer()
BEGIN
    SELECT customer_id, COUNT(*) as num_completed_orders
    FROM Pizza_Order
    WHERE order_status = 'COMPLETED'
    GROUP BY customer_id
    ORDER BY num_completed_orders DESC
    LIMIT 1;
END //

DELIMITER ;
