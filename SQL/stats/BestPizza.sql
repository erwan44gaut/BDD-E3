DELIMITER //
CREATE PROCEDURE GetBestPizzas()
BEGIN
    SELECT pizza_id, COUNT(*) AS total_sales
    FROM Pizza_Order AS o
    JOIN Delivery ON o.order_id = Delivery.order_id
    GROUP BY pizza_id
    HAVING COUNT(*) = (SELECT MAX(sales_count) FROM (SELECT COUNT(*) AS sales_count FROM Pizza_Order AS o JOIN Delivery ON o.order_id = Delivery.order_id GROUP BY pizza_id) AS temp);
END;
DELIMITER;