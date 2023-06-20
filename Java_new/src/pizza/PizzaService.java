package src.pizza;

import java.sql.ResultSet;

import src.util.DatabaseConnection;

public class PizzaService {
    
    public static ResultSet getPizzas()
    {
        return DatabaseConnection.query("SELECT * FROM Pizza;");
    }

    public static boolean addPizza(String pizzaName, String pizzaSize, float pizzaBasePrice)
    {
        String sqlQuery = String.format("INSERT INTO Pizza (pizza_name,pizza_size,pizza_base_price) VALUES (\"%s\",\"%s\",%f)", pizzaName,pizzaSize,pizzaBasePrice);
        boolean queryResult = DatabaseConnection.execute(sqlQuery);
        return queryResult;
    }

    public static boolean deletePizza(int pizzaId)
    {
        String sqlQuery = String.format("DELETE FROM Pizza WHERE id=%d", pizzaId)+
        String.format("DELETE FROM Has_Ingredient WHERE pizza_id=%d", pizzaId);
        boolean queryResult = DatabaseConnection.execute(sqlQuery);
        return queryResult;
    }

    public static boolean updatePizzaField(int pizzaId, String field, String value) 
    {
        String sqlQuery = String.format("UPDATE Pizza SET %s = '%s' WHERE id = %d", field, value, pizzaId);
        boolean queryResult = DatabaseConnection.execute(sqlQuery);
        return queryResult;
    }

    public static boolean addIngredientInPizza(int pizzaId, int ingredientId){
        String sqlQuery = String.format("INSERT INTO Has_Ingredient (pizza_id,ingredient_id) VALUES (%d,%d)", pizzaId, ingredientId);
        boolean queryResult = DatabaseConnection.execute(sqlQuery);
        return queryResult;
    }

    public static boolean deleteIngredientInPizza(int pizzaId, int ingredientId){
        String sqlQuery = String.format("DELETE FROM Has_Ingredient WHERE (pizza_id=%d and ingredient_id=%d", pizzaId,ingredientId);
        boolean queryResult = DatabaseConnection.execute(sqlQuery);
        return queryResult;
    }
}