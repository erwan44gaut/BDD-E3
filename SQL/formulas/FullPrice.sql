DELIMITER //
CREATE PROCEDURE CalculatePrice(IN pizza_id INT)
BEGIN
    DECLARE basePrice DECIMAL(10,2);
    DECLARE pizzaSize VARCHAR(50);
    
    -- Create a temporary table to store the result set
    CREATE TEMPORARY TABLE temp_result (
        adjusted_price DECIMAL(10,2)
    );
    
    -- Retrieve the base price and pizza size from the Pizza table based on the pizza ID
    SELECT pizza_base_price, pizza_size INTO basePrice, pizzaSize FROM Pizza WHERE pizza_id = pizza_id;
    
    -- Calculate the adjusted price based on the retrieved base price and pizza size
    CASE
        WHEN pizzaSize = 'large' THEN INSERT INTO temp_result VALUES (basePrice * 1.3);
        WHEN pizzaSize = 'small' THEN INSERT INTO temp_result VALUES (basePrice * 0.7);
        ELSE INSERT INTO temp_result VALUES (basePrice);
    END CASE;
    
    -- Select from the temporary table to return the result set
    SELECT * FROM temp_result;
    
    -- Drop the temporary table
    DROP TEMPORARY TABLE IF EXISTS temp_result;
END //
DELIMITER ;
