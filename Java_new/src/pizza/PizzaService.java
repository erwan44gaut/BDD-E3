package src.pizza;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import src.util.DatabaseConnection;

public class PizzaService {
    
    public static ResultSet getPizzas()
    {
        return DatabaseConnection.query("SELECT * FROM Pizza;");
    }

    public static ResultSet getPizzaById(int pizzaId)
    {
        String sqlQuery = String.format("SELECT * FROM Pizza WHERE pizza_id = %d", pizzaId);
        return DatabaseConnection.query(sqlQuery);
    }

    public static int addPizza(String pizzaName, float pizzaBasePrice)
    {
        String sqlQuery = String.format(Locale.US,"INSERT IGNORE INTO Pizza (pizza_name,pizza_base_price) VALUE ('%s',%f);", pizzaName,pizzaBasePrice);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static int deletePizza(int pizzaId)
    {
        String sqlQuery = String.format("DELETE FROM Pizza WHERE pizza_id=%d;", pizzaId);
        String sqlQuery2 = String.format("DELETE FROM Has_Ingredient WHERE pizza_id=%d;", pizzaId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        int queryResult2 = DatabaseConnection.executeUpdate(sqlQuery2);
        return queryResult+queryResult2;
    }

    public static int updatePizzaField(int pizzaId, String field, String value) 
    {
        String sqlQuery = String.format("UPDATE Pizza SET %s = '%s' WHERE pizza_id = %d", field, value, pizzaId);

        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static int addIngredientInPizza(int pizzaId, int ingredientId){
        String sqlQuery = String.format("INSERT INTO Has_Ingredient (pizza_id,ingredient_id) VALUES (%d,%d);", pizzaId, ingredientId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static int deleteIngredientInPizza(int pizzaId, int ingredientId){
        String sqlQuery = String.format("DELETE FROM Has_Ingredient WHERE (pizza_id=%d and ingredient_id=%d);", pizzaId,ingredientId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static ResultSet getPizzaIngredient(int pizzaId){
        return DatabaseConnection.query(String.format("SELECT HI.ingredient_id, I.ingredient_name FROM Has_Ingredient HI JOIN Ingredient I ON HI.ingredient_id = I.ingredient_id WHERE HI.pizza_id =%d",pizzaId));
    }

    public static ResultSet getPizzaNoIngredient(int pizzaId){
        String sqlQuery = String.format("SELECT i.ingredient_id,ingredient_name FROM Ingredient i LEFT JOIN Has_Ingredient h ON i.ingredient_id = h.ingredient_id AND h.pizza_id = %d WHERE h.pizza_id IS NULL;",pizzaId);
        return DatabaseConnection.query(sqlQuery);
    }

    public static float getAdjustedPrice(int pizzaId, String pizzaSize){
        float adjustedPrice = 0f;
        String sqlQuery = String.format("SELECT CalculateAdjustedPrice(%d,'%s') AS adjustedPrice;",pizzaId,pizzaSize);
        try{
            ResultSet resultSet = DatabaseConnection.query(sqlQuery);
            if (resultSet.next()) {
                adjustedPrice = resultSet.getFloat("adjustedPrice");
            }
        }catch (SQLException e) {
            System.out.println("ERROR : function adjustedPrice");
        }
        return adjustedPrice;
    }
}