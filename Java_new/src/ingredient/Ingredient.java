package src.ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;

import src.util.DatabaseConnection;

public class Ingredient {
    public static String getName(int ingredientId) throws SQLException {
        String sqlQuery = String.format("SELECT ingredient_name FROM Ingredient WHERE ingredient_id = %d",ingredientId);
        ResultSet resultSet = DatabaseConnection.query(sqlQuery);
        if (resultSet.next()) {
            return resultSet.getString("ingredient_name");
        } else {
            return "";
        }
    }
}
