DELIMITER //
CREATE PROCEDURE GetBestCustomers()
BEGIN
    SELECT customer_id, COUNT(*) AS total_sales
    FROM Pizza_Order AS o
    JOIN Delivery ON o.order_id = Delivery.order_id
    WHERE customer_id IS NOT NULL
    GROUP BY customer_id
    HAVING COUNT(*) = (SELECT MAX(sales_count)
                        FROM (SELECT COUNT(*) AS sales_count 
                            FROM Pizza_Order AS o
                            JOIN Delivery ON o.order_id = Delivery.order_id 
                            WHERE customer_id IS NOT NULL
                            AND (Delivery.delivery_status="COMPLETE" OR Delivery.delivery_status="LATE")
                            GROUP BY customer_id) AS temp);
END//