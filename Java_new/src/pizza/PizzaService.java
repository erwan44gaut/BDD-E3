package src.pizza;

import java.sql.ResultSet;
import java.sql.SQLException;

import src.util.DatabaseConnection;

public class PizzaService {
    
    public static ResultSet getPizzas()
    {
        return DatabaseConnection.query("SELECT * FROM Pizza;");
    }

    public static boolean addPizza(String pizzaName, float pizzaBasePrice)
    {
        String sqlQuery = String.format("INSERT INTO Pizza (pizza_name,pizza_base_price) VALUES (\"%s\",%f)", pizzaName,pizzaBasePrice);
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

    public static double getAdjustedPrice(int pizzaId, String pizzaSize){
        double adjustedPrice = 0.0;
        String sqlQuery = String.format("SELECT CalculateAdjustedPrice(%d,%s) AS adjustedPrice",pizzaId,pizzaSize);
        try{
            ResultSet resultSet = DatabaseConnection.query(sqlQuery);
            if (resultSet.next()) {
                adjustedPrice = resultSet.getDouble("adjustedPrice");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return adjustedPrice;
    }
}