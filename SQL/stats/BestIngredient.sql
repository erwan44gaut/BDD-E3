DELIMITER //

CREATE PROCEDURE GetMostPopularIngredient()
BEGIN
   SELECT HI.ingredient_id, I.ingredient_name, COUNT(*) AS ingredient_count
   FROM Has_Ingredient HI
   JOIN Ingredient I ON HI.ingredient_id = I.ingredient_id
   GROUP BY HI.ingredient_id, I.ingredient_name
   ORDER BY ingredient_count DESC
   LIMIT 1;
END //

DELIMITER ;