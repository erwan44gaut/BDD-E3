DELIMITER //
CREATE PROCEDURE GetBestIngredients()
BEGIN
SELECT ingredient_id, COUNT(*) AS total_sales
    FROM Has_Ingredient AS h
    JOIN Pizza_Order ON h.pizza_id = Pizza_Order.pizza_id
    JOIN Delivery ON Pizza_Order.order_id = Delivery.order_id
    WHERE h.pizza_id IS NOT NULL AND ingredient_id IS NOT NULL
    GROUP BY ingredient_id
    HAVING COUNT(*) = (SELECT MAX(sales_count) 
                        FROM (SELECT COUNT(*) AS sales_count 
                                FROM Has_Ingredient
                                JOIN Pizza_Order ON Has_Ingredient.pizza_id = Pizza_Order.pizza_id 
                                JOIN Delivery ON Pizza_Order.order_id = Delivery.order_id 
                                WHERE Has_Ingredient.pizza_id IS NOT NULL AND ingredient_id IS NOT NULL
                                AND (Delivery.delivery_status="COMPLETE" OR Delivery.delivery_status="LATE")
                                GROUP BY ingredient_id) AS temp);
END//