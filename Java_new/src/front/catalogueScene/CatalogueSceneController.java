package src.front.catalogueScene;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import src.pizza.PizzaService;

public class CatalogueSceneController {

    @FXML
    private HBox Catalogue;

    @FXML
    private HBox Catalogue1;

    @FXML
    private HBox Catalogue11;

    @FXML
    private HBox Catalogue111;

    @FXML
    private HBox Catalogue1111;

    @FXML
    private HBox Catalogue11111;

    @FXML
    private Button addPizzaButton;

    @FXML
    private Button addPizzaButton1;

    @FXML
    private Button addPizzaButton11;

    @FXML
    private Button addPizzaButton111;

    @FXML
    private Button addPizzaButton1111;

    @FXML
    private Button addPizzaButton11111;

    @FXML
    private Button deletePizzaButton;

    @FXML
    private Button deletePizzaButton1;

    @FXML
    private Button deletePizzaButton11;

    @FXML
    private Button deletePizzaButton111;

    @FXML
    private Button deletePizzaButton1111;

    @FXML
    private Button deletePizzaButton11111;

    @FXML
    private Button orderPizzaButton;

    @FXML
    private TextFlow pizzaDescription;

    @FXML
    private TextFlow pizzaDescription1;

    @FXML
    private TextFlow pizzaDescription11;

    @FXML
    private TextFlow pizzaDescription111;

    @FXML
    private TextFlow pizzaDescription1111;

    @FXML
    private TextFlow pizzaDescription11111;

    @FXML
    private ImageView pizzaImage;

    @FXML
    private ImageView pizzaImage1;

    @FXML
    private ImageView pizzaImage11;

    @FXML
    private ImageView pizzaImage111;

    @FXML
    private ImageView pizzaImage1111;

    @FXML
    private ImageView pizzaImage11111;

    @FXML
    private TextFlow pizzaTitle;

    @FXML
    private TextFlow pizzaTitle1;

    @FXML
    private TextFlow pizzaTitle11;

    @FXML
    private TextFlow pizzaTitle111;

    @FXML
    private TextFlow pizzaTitle1111;

    @FXML
    private TextFlow pizzaTitle11111;

    @FXML
    private TextFlow recapPizza; 
}