DELIMITER //

CREATE FUNCTION CalculateAdjustedPrice(pizza_id INT, pizza_size ENUM('SMALL', 'MEDIUM', 'LARGE')) RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE basePrice DECIMAL(10,2);
    DECLARE adjustedPrice DECIMAL(10,2);

    -- Retrieve the base price and pizza size from the Pizza table based on the pizza ID
    SELECT pizza_base_price INTO basePrice FROM Pizza WHERE pizza_id = pizza_id LIMIT 1;

    -- Calculate the adjusted price based on the retrieved base price and pizza size
    CASE
        WHEN pizza_size = 'large' THEN SET adjustedPrice = basePrice * 1.3;
        WHEN pizza_size = 'small' THEN SET adjustedPrice = basePrice * 0.7;
        ELSE SET adjustedPrice = basePrice;
    END CASE;

    RETURN adjustedPrice;
END //

DELIMITER ;
