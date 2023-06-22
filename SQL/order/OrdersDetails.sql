
DELIMITER //
CREATE PROCEDURE GetOrdersDetails()
BEGIN
    SELECT 
		   Pizza_Order.order_id,
		   Customer.customer_name, 
		   Pizza.pizza_name,
           Pizza_order.pizza_size,
           CalculateAdjustedPrice(Pizza.pizza_id, Pizza_Order.pizza_size) AS adjusted_price,
           Pizza_Order.order_status,
           Pizza_Order.order_datetime
    FROM Pizza_Order
    JOIN Customer ON Pizza_Order.customer_id = Customer.customer_id
    JOIN Pizza ON Pizza_Order.Pizza_id = Pizza.pizza_id
    ORDER BY Pizza_Order.order_id;
END //
DELIMITER ;