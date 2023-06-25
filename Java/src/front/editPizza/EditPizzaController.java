package src.front.editPizza;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import src.pizzeria.ingredient.Ingredient;
import src.pizzeria.pizza.Pizza;
import src.pizzeria.pizza.PizzaService;


public class EditPizzaController implements Initializable{

    Pizza pizza;
    
    public EditPizzaController(Pizza pizza)
    {
        this.pizza = pizza;
    }
    @FXML
    private Button changeName;

    @FXML
    private Button changePrice;

    @FXML
    private Text id;

    @FXML
    private Text name;

    @FXML
    private TextField newName;

    @FXML
    private TextField newPrice;

    @FXML
    private Text price;

    @FXML
    private Button addButton;

    @FXML
    private ComboBox<Ingredient> addIngredient;

    @FXML
    private Button removeButton;

    @FXML
    private ComboBox<Ingredient> removeIngredient;

    @FXML
    private Text ingredients;

    @FXML
    void changeNameAction(ActionEvent event) {
        String newNameText = newName.getText();
        if(newNameText!=""){
            PizzaService.updatePizzaField(pizza.getPizzaId(), "pizza_name", newNameText);
            name.setText("NAME : "+newNameText);
            System.out.println("Changing the name of the pizza to '"+newNameText+"'");
        }
    }

    @FXML
    void changePriceAction(ActionEvent event) {
        String newPriceText = newPrice.getText();
        try{
            Float.parseFloat(newPriceText);
            PizzaService.updatePizzaField(pizza.getPizzaId(), "pizza_base_price", newPriceText);
            price.setText("BASE PRICE : "+newPriceText+ " $");
            System.out.println("Change the price of the pizza to "+newPriceText+ " $.");
        }catch(Exception e){
            System.out.println("ERROR: INVALID INPUT");
        }
    }

    @FXML
    void addIngredientAction(ActionEvent event){
        try{
            Ingredient selectedIngredient = addIngredient.getValue();
            PizzaService.addIngredientInPizza(pizza.getPizzaId(), selectedIngredient.getIngredientId());
            System.out.println("Add ingredient : "+selectedIngredient.getIngredientName());
            refreshAddIngredient();
            refreshRemoveIngredient();
            refreshIngredients();
        } catch (Exception e) {
            System.out.println("ERROR: COULD NOT ADD INGREDIENT");
        }
    }

    @FXML
    void removeIngredientAction(ActionEvent event){
        try{
            Ingredient selectedIngredient = removeIngredient.getValue();
            PizzaService.deleteIngredientInPizza(pizza.getPizzaId(), selectedIngredient.getIngredientId());
            System.out.println("Remove ingredient : "+selectedIngredient.getIngredientName());
            refreshAddIngredient();
            refreshRemoveIngredient();
            refreshIngredients();
        }catch(Exception e){
            System.out.println("ERROR: COULD NOT REMOVE INGREDIENT");
        }
    }

    ObservableList<Ingredient> addList = FXCollections.observableArrayList();
    ObservableList<Ingredient> removeList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        id.setText("ID : " + this.pizza.getPizzaId());
        name.setText("NAME : "+pizza.getName());
        price.setText("BASE PRICE : "+pizza.getPrice() + " $");
        ingredients.setText("Ingredients : "+pizza.getIngredients());

        // Configurer le ComboBox pour les ingrédients à ajouter
        addIngredient.setConverter(ingredientConverter);

        // Configurer le ComboBox pour les ingrédients à supprimer
        removeIngredient.setConverter(ingredientConverter);

        refreshAddIngredient();
        refreshRemoveIngredient();
    }

    
    StringConverter<Ingredient> ingredientConverter = new StringConverter<Ingredient>() {
        @Override
        public String toString(Ingredient ingredient) {
            return ingredient.getIngredientName();
        }

        @Override
        public Ingredient fromString(String string) {
            // Conversion inverse non nécessaire pour ce cas
            return null;
        }
    };

    void refreshAddIngredient(){
        addIngredient.getItems().clear();
        try {
            ResultSet addSet = PizzaService.getPizzaNoIngredient(pizza.getPizzaId());
            while (addSet.next()) {
                Ingredient ingredient = Ingredient.createIngredientFromResultSet(addSet);
                addList.add(ingredient);
                //System.out.println(ingredient.getName()+" : "+ingredient.getIngredientId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addIngredient.setItems(addList);
    }

    void refreshRemoveIngredient(){
        removeIngredient.getItems().clear();
        try {
            ResultSet removeSet = PizzaService.getPizzaIngredient(pizza.getPizzaId());
            while (removeSet.next()) {
                Ingredient ingredient = Ingredient.createIngredientFromResultSet(removeSet);
                removeList.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        removeIngredient.setItems(removeList);
    }

    void refreshIngredients(){
        String ingredientText = Pizza.loadIngredients(pizza.getPizzaId());
        ingredients.setText(ingredientText);
    }
}