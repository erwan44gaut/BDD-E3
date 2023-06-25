DELIMITER //

CREATE PROCEDURE GetMostPopularIngredient()
BEGIN
    SELECT I.ingredient_id, I.ingredient_name, COUNT(*) AS ingredient_count
    FROM Has_Ingredient HI
    JOIN Pizza_Order PO ON HI.pizza_id = PO.pizza_id
    JOIN Ingredient I ON HI.ingredient_id = I.ingredient_id
    WHERE PO.order_status = 'COMPLETED'
    GROUP BY I.ingredient_id, I.ingredient_name
    ORDER BY ingredient_count DESC
    LIMIT 1;
END //

DELIMITER ;
