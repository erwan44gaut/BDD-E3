
DELIMITER //
CREATE PROCEDURE GetWaitingForDeliveryOrdersDetails()
BEGIN
    SELECT 
		   Pizza_Order.order_id,
		   Customer.customer_name, 
		   Pizza.pizza_name,
           Pizza_order.pizza_size,
           CalculateAdjustedPrice(Pizza.pizza_id, Pizza_Order.pizza_size) AS adjusted_price,
           Pizza_Order.order_status,
           Delivery.delivery_status,
           Pizza_Order.order_datetime
    FROM Pizza_Order
    LEFT JOIN Customer ON Pizza_Order.customer_id = Customer.customer_id
    LEFT JOIN Pizza ON Pizza_Order.Pizza_id = Pizza.pizza_id
    LEFT JOIN Delivery ON Pizza_Order.order_id = Delivery.order_id
    WHERE Delivery.delivery_id IS NULL AND (Pizza_Order.order_status = "ACCEPTED" OR Pizza_Order.order_status = "IN_PREPARATION")
    ORDER BY Pizza_Order.order_id;
END //
DELIMITER ;