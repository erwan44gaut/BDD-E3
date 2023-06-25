package src.pizzeria.pizza;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class Pizza {
    private Integer pizzaId;
    private String name;
    private Float price;
    private Image image;
    private String ingredients;
    private Button order;
    private Button delete;
    private Button modify;

    public Pizza(int pizzaId, String name, float price) {
        this.pizzaId = pizzaId;
        this.name = name;
        this.price = price;
        this.image = loadImage("image/pizza.png");
        this.ingredients = loadIngredients(pizzaId);
        this.order = new Button("ORDER");
        this.delete = new Button("DELETE");
        this.modify = new Button("MODIFY");
    }

    public int getPizzaId() {
        return pizzaId;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public Image getImage(){
        return image;
    }

    public String getIngredients(){
        return ingredients;
    }

    public Button getOrder(){
        return order;
    }

    public Button getDelete(){
        return delete;
    }

    public Button getModify(){
        return modify;
    }
    
    private Image loadImage(String imageUrl) {
        try {
            return new Image(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Pizza createPizzaFromResultSet(ResultSet resultSet) throws SQLException {
        int pizzaId = resultSet.getInt("pizza_id");
        String name = resultSet.getString("pizza_name");
        float price = resultSet.getFloat("pizza_base_price");

        return new Pizza(pizzaId, name, price);
    }

    public static String loadIngredients(int pizzaId){  
        ResultSet IngredientSet = PizzaService.getPizzaIngredient(pizzaId);
        String ingredients = "";
        try{
            while(IngredientSet.next()){
                ingredients += IngredientSet.getString("ingredient_name")+ " | ";
            }
        }catch(Exception e){

        }
        return ingredients;
    }
}