DELIMITER //

CREATE PROCEDURE GetMostOrderedPizza()
BEGIN
    SELECT pizza_id, COUNT(*) as order_count
    FROM Pizza_Order
    GROUP BY pizza_id
    ORDER BY order_count DESC
    LIMIT 1;
END //

DELIMITER ;
