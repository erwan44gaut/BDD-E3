package src.pizzeria.ingredient;

import java.sql.ResultSet;

import src.util.DatabaseConnection;

public class IngredientService {
    
    public static ResultSet getIngredients()
    {
        return DatabaseConnection.query("SELECT * FROM Ingredient;");
    }

    public static ResultSet getIngredientById(int ingredientId)
    {
        String sqlQuery = String.format("SELECT * FROM Ingredient WHERE ingredient_id = %d", ingredientId);
        return DatabaseConnection.query(sqlQuery);
    }

    public static int addIngredient(String ingredientName)
    {
        String sqlQuery = String.format("INSERT INTO Ingredient (ingredient_name) VALUES (\"%s\")", ingredientName);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static int deleteIngredient(int ingredientId)
    {
        String sqlQuery = String.format("DELETE FROM Ingredient WHERE ingredient_id=%d;", ingredientId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static int updateIngredientField(int ingredientId, String field, String value) 
    {
        String sqlQuery = String.format("UPDATE Ingredient SET %s = '%s' WHERE id = %d", field, value, ingredientId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }
}