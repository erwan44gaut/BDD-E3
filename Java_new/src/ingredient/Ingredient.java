package src.ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Button;

public class Ingredient {

    private int ingredientId;
    private String name;
    private Button deleteButton;

    public Ingredient(int ingredientId, String name){
        this.ingredientId = ingredientId;
        this.name = name;
        this.deleteButton = new Button("DELETE");
    }

    public String getName() {
        return name;
    }

    public int getIngredientId() {
        return ingredientId;
    }
    
    public Button getDeleteButton() {
        return deleteButton;
    }

    public static Ingredient createIngredientFromResultSet(ResultSet resultSet) throws SQLException {
        int ingredientId = resultSet.getInt("ingredient_id");
        String name = resultSet.getString("ingredient_name");
        return new Ingredient(ingredientId, name);
    }
}
