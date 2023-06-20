
DELIMITER //

CREATE PROCEDURE PlaceOrder(
    IN customer_id INT,
    IN pizza_id INT,
    IN pizza_size ENUM('SMALL', 'MEDIUM', 'LARGE')
)
BEGIN
    DECLARE pizza_price DECIMAL(10, 2);
    DECLARE total_price DECIMAL(10, 2);
    DECLARE customer_balance DECIMAL(10, 2);
    
    -- Calculate pizza price
    SELECT pizza_base_price INTO pizza_price FROM Pizza WHERE Pizza.pizza_id = pizza_id LIMIT 1;
    SELECT CalculateAdjustedPrice(pizza_id, pizza_size) INTO total_price LIMIT 1;
    
    -- Check balance
    SELECT customer_balance INTO customer_balance FROM Customer WHERE Customer.customer_id = customer_id;
    
    IF customer_balance >= total_price THEN
        -- Place order
        INSERT INTO Pizza_Order (order_status, order_datetime, pizza_id, pizza_size, customer_id)
        VALUES ('ACCEPTED', NOW(), pizza_id, pizza_size, customer_id);
        
        -- Update balance
        UPDATE Customer SET Customer.customer_balance = customer_balance - total_price WHERE Customer.customer_id = customer_id;
    END IF;
END //

DELIMITER ;