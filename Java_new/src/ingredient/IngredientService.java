package src.ingredient;

import java.sql.ResultSet;

import src.util.DatabaseConnection;

public class IngredientService {
    
    public static ResultSet getIngredients()
    {
        return DatabaseConnection.query("SELECT * FROM Ingredient;");
    }
}
