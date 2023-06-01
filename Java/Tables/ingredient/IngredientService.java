package Tables.ingredient;

import java.sql.ResultSet;
import src.DatabaseConnection;

public class IngredientService {
    
    public static ResultSet getIngredients()
    {
        return DatabaseConnection.query("SELECT * FROM Ingredient;");
    }
}
