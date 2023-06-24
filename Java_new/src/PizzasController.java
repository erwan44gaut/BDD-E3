package src;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import front.OrderPizzaScene.OrderPizzaController;
import front.editPizzaScene.EditPizzaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Pair;
import src.customer.CustomerService;
import src.pizza.Pizza;
import src.pizza.PizzaService;

public class PizzasController implements Initializable{


    @FXML
    private TableColumn<Pizza, String> pizza_name;

    @FXML
    private TableColumn<Pizza, Float> pizza_price;

    @FXML
    private TableColumn<Pizza, Image> pizza_image;

    @FXML
    private TableColumn<Pizza, String> pizza_ingredients;

    @FXML
    private TableColumn<Pizza, Button> pizza_order;

    @FXML
    private TableColumn<Pizza, Button> pizza_delete;

    @FXML
    private TableColumn<Pizza, Button> pizza_modify;


    @FXML
    private TableView<Pizza> pizza_table;

    @FXML
    private Button pizza_addButton;

    @FXML
    private Button pizza_refreshButton;

    ObservableList<Pizza> pizzas = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pizza_table.setFixedCellSize(60.0);
        pizza_name.setCellValueFactory(new PropertyValueFactory<Pizza,String>("name"));
        pizza_price.setCellValueFactory(new PropertyValueFactory<Pizza,Float>("price"));
        pizza_image.setCellValueFactory(new PropertyValueFactory<Pizza,Image>("image"));
        pizza_image.setCellFactory(column -> {
            return new TableCell<>() {
                private final ImageView imageView = new ImageView();
                {
                    
                }
                @Override
                protected void updateItem(Image image, boolean empty) {
                    super.updateItem(image, empty);
                    if (empty || image == null) {
                        setGraphic(null);
                    } else {
                        imageView.setImage(image);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        setAlignment(Pos.CENTER);
                        setGraphic(imageView);
                    }
                }
            };
        });
        pizza_ingredients.setCellValueFactory(new PropertyValueFactory<Pizza, String>("ingredients"));
        pizza_ingredients.setCellFactory(column -> {
            return new TableCell<Pizza, String>() {
                private final TextFlow textFlow = new TextFlow();

                {
                    textFlow.setMaxWidth(Double.MAX_VALUE);
                    textFlow.getStyleClass().add("ingredients-textflow");
                    setGraphic(textFlow);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        textFlow.getChildren().clear();
                    } else {
                        Text text = new Text(item);
                        textFlow.getChildren().setAll(text);
                    }
                }
            };
        });


        pizza_order.setCellValueFactory(new PropertyValueFactory<Pizza, Button>("order"));
        pizza_order.setCellFactory(column -> {
            return new TableCell<Pizza, Button>() {
                private final Button orderButton = new Button("ORDER");

                {
                    orderButton.setOnAction(event -> {
                        Pizza pizza = getTableView().getItems().get(getIndex());
                        System.out.println("ORDER");
                        Stage stage = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderPizza.fxml"));
                        loader.setController(new OrderPizzaController(pizza));
                        Parent root;
                        try {
                            root = loader.load();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setOnCloseRequest(e -> {
                            refreshTable();
                            });
                            System.out.println("Order menu of pizza '"+pizza.getName()+"'");
                            stage.show();
                        } catch (IOException e) {
                        }
                    });
                }
                @Override
                protected void updateItem(Button button, boolean empty) {
                    super.updateItem(button, empty);

                    if (button == null || empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(orderButton);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

        pizza_delete.setCellValueFactory(new PropertyValueFactory<Pizza, Button>("delete"));
        pizza_delete.setCellFactory(column -> {
            return new TableCell<Pizza, Button>() {
                private final Button deleteButton = new Button("DELETE");

                {
                    deleteButton.setOnAction(event -> {
                        Pizza pizza = getTableView().getItems().get(getIndex());
                        PizzaService.deletePizza(pizza.getPizzaId());
                        System.out.println("Delete pizza '"+pizza.getName()+"'");
                        refreshTable();
                    });
                }

                @Override
                protected void updateItem(Button button, boolean empty) {
                    super.updateItem(button, empty);

                    if (button == null || empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

        pizza_modify.setCellValueFactory(new PropertyValueFactory<Pizza, Button>("modify"));
        pizza_modify.setCellFactory(column -> {
            return new TableCell<Pizza, Button>() {
                private final Button editButton = new Button("EDIT");

                {
                    editButton.setOnAction(event -> {
                        Pizza pizza = getTableView().getItems().get(getIndex());
                        Stage stage = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditPizza.fxml"));
                        loader.setController(new EditPizzaController(pizza));
                        Parent root;
                        try {
                            root = loader.load();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setOnCloseRequest(e -> {
                            refreshTable();;
                            });
                            stage.show();
                            System.out.println("Edit menu of pizza '"+pizza.getName()+"'");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                @Override
                protected void updateItem(Button button, boolean empty) {
                    super.updateItem(button, empty);

                    if (button == null || empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(editButton);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

        
        pizza_addButton.setOnAction(event -> {
            Dialog<Pair<String, Float>> dialog = new Dialog<>();
            dialog.setTitle("Create new pizza");

            TextField nameField = new TextField();
            nameField.setPromptText("Enter pizza name");

            TextField priceField = new TextField();
            priceField.setPromptText("Enter pizza price");

            dialog.getDialogPane().setContent(new VBox(nameField, priceField));

            ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, cancelButtonType);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == applyButtonType) {
                    String name = nameField.getText();
                    if(name != ""){
                        try{
                            float price = Float.parseFloat(priceField.getText());
                            return new Pair<>(name, price);
                        }catch(Exception e){
                            System.out.println("Wrong Price");
                        }
                    }
                    else{
                        System.out.println("No name in the text field");
                    } 
                }
                return null;
            });

            Optional<Pair<String, Float>> result = dialog.showAndWait();

            result.ifPresent(pizzaData -> {
                String name = pizzaData.getKey();
                float price = pizzaData.getValue();
                int res = PizzaService.addPizza(name, price);
                if(res==0)System.out.println("The pizza '" + name + "' already exists");
                else System.out.println("Created new pizza: " + name + ", Price: " + price);
                refreshTable();
            });
        });

        pizza_refreshButton.setOnAction(event -> refreshTable());

        refreshTable();
    }

    void refreshTable(){
        pizza_table.getItems().clear();
        try {
            ResultSet pizzaSet = PizzaService.getPizzas();
            while (pizzaSet.next()) {
                Pizza pizza = Pizza.createPizzaFromResultSet(pizzaSet);
                pizzas.add(pizza);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pizza_table.setItems(pizzas);
    }

}
