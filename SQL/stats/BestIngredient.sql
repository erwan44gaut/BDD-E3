DELIMITER //

CREATE PROCEDURE GetMostPopularIngredient()
BEGIN
    SELECT ingredient_id, COUNT(*) as ingredient_count
    FROM Has_Ingredient
    GROUP BY ingredient_id
    ORDER BY ingredient_count DESC
    LIMIT 1;
END //

DELIMITER ;
