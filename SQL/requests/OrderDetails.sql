DELIMITER //
CREATE PROCEDURE GetOrderDetails(IN orderId INT)
BEGIN
    SELECT Customer.customer_name, Pizza.pizza_name,
           CalculateAdjustedPrice(Pizza.pizza_id) AS adjusted_price,
           Pizza_Order.order_status, Pizza_Order.order_datetime
    FROM Pizza_Order
    JOIN Customer ON Pizza_Order.customer_id = Customer.customer_id
    JOIN Pizza ON Pizza_Order.Pizza_id = Pizza.pizza_id
    WHERE Pizza_Order.order_id = orderId;
END //
DELIMITER ;
