package src.ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;

import src.pizza.Pizza;
import src.util.DatabaseConnection;

public class Ingredient {

    int id;
    String name;

    public Ingredient(int id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public int getId(){
        return id;
    }

    public static String getName(int ingredientId) throws SQLException {
        String sqlQuery = String.format("SELECT ingredient_name FROM Ingredient WHERE ingredient_id = %d",ingredientId);
        ResultSet resultSet = DatabaseConnection.query(sqlQuery);
        if (resultSet.next()) {
            return resultSet.getString("ingredient_name");
        } else {
            return "";
        }
    }

    public static Ingredient createIngredientFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("ingredient_id");
        String name = resultSet.getString("ingredient_name");
        return new Ingredient(id, name);
    }
}
