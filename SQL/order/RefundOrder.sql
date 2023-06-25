DELIMITER //

CREATE PROCEDURE RefundOrder(in order_id INT)
BEGIN
	DECLARE total_price DECIMAL(10, 2);
	DECLARE pizza_id INT;
    DECLARE pizza_size ENUM("SMALL", "MEDIUM", "LARGE");
    DECLARE customer_id INT;
    
    SELECT Pizza_Order.pizza_id INTO pizza_id FROM Pizza_Order WHERE Pizza_Order.order_id = order_id;
    SELECT Pizza_Order.pizza_size INTO pizza_size FROM Pizza_Order WHERE Pizza_Order.order_id = order_id;
    SELECT CalculateAdjustedPrice(pizza_id, pizza_size) INTO total_price;
    SELECT Pizza_Order.customer_id  INTO customer_id FROM Pizza_Order WHERE Pizza_order.order_id = order_id;
    
    UPDATE Customer SET Customer.customer_balance = Customer.customer_balance + total_price WHERE Customer.customer_id = customer_id;
END; //

DELIMITER ;