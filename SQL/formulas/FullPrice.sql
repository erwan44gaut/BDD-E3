DELIMITER //

CREATE FUNCTION CalculateAdjustedPrice(pizza_id INT) RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE basePrice DECIMAL(10,2);
    DECLARE pizzaSize VARCHAR(50);
    DECLARE adjustedPrice DECIMAL(10,2);

    -- Retrieve the base price and pizza size from the Pizza table based on the pizza ID
    SELECT pizza_base_price, pizza_size INTO basePrice, pizzaSize FROM Pizza WHERE pizza_id = pizza_id LIMIT 1;

    -- Calculate the adjusted price based on the retrieved base price and pizza size
    CASE
        WHEN pizzaSize = 'large' THEN SET adjustedPrice = basePrice * 1.3;
        WHEN pizzaSize = 'small' THEN SET adjustedPrice = basePrice * 0.7;
        ELSE SET adjustedPrice = basePrice;
    END CASE;

    RETURN adjustedPrice;
END //

DELIMITER ;
