package src.ingredient;

import java.sql.ResultSet;

import src.util.DatabaseConnection;

public class IngredientService {
    
    public static ResultSet getIngredients()
    {
        return DatabaseConnection.query("SELECT * FROM Ingredient;");
    }

    public static boolean addIngredient(String ingredientName)
    {
        String sqlQuery = String.format("INSERT INTO Ingredient (ingredient_name) VALUES (\"%s\")", ingredientName);
        boolean queryResult = DatabaseConnection.execute(sqlQuery);
        return queryResult;
    }

    public static boolean deleteIngredient(int ingredientId)
    {
        String sqlQuery = String.format("DELETE FROM Ingredient WHERE id=%d", ingredientId) +
        String.format("DELETE FROM Has_Ingredient WHERE ingredient_id=%d", ingredientId);
        boolean queryResult = DatabaseConnection.execute(sqlQuery);
        return queryResult;
    }

    public static boolean updateIngredientField(int ingredientId, String field, String value) 
    {
        String sqlQuery = String.format("UPDATE Ingredient SET %s = '%s' WHERE id = %d", field, value, ingredientId);
        boolean queryResult = DatabaseConnection.execute(sqlQuery);
        return queryResult;
    }
}
