DELIMITER //

CREATE PROCEDURE PlaceOrder(
    IN p_customer_id INT,
    IN p_pizza_id INT,
    IN p_pizza_size ENUM('SMALL', 'MEDIUM', 'LARGE')
)
BEGIN
    DECLARE v_pizza_price DECIMAL(10, 2);
    DECLARE v_total_price DECIMAL(10, 2);
    DECLARE v_customer_balance DECIMAL(10, 2);
    DECLARE v_order_count INT;
    
    -- Calculate pizza price
    SELECT pizza_base_price INTO v_pizza_price FROM Pizza WHERE pizza_id = p_pizza_id LIMIT 1;
    SELECT CalculateAdjustedPrice(p_pizza_id, p_pizza_size) INTO v_total_price LIMIT 1;
    
    -- Check balance and order count
    SELECT customer_balance INTO v_customer_balance FROM Customer WHERE customer_id = p_customer_id;
    SELECT COUNT(*) INTO v_order_count FROM Pizza_Order WHERE customer_id = p_customer_id;
    
    IF (v_customer_balance >= v_total_price or (v_order_count+1) % 10 <> 0) THEN
        -- Place order
        INSERT INTO Pizza_Order (order_status, order_datetime, pizza_id, pizza_size, customer_id)
        VALUES ('ACCEPTED', NOW(), p_pizza_id, p_pizza_size, p_customer_id);
        
        -- Update balance if order count is not divisible by 10
        IF (v_order_count+1) % 10 <> 0 THEN
            UPDATE Customer SET customer_balance = v_customer_balance - v_total_price WHERE customer_id = p_customer_id;
        END IF;
    ELSE
        SELECT -1;
    END IF;
END //

DELIMITER ;