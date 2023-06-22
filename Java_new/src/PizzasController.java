package src;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import src.pizza.Pizza;
import src.pizza.PizzaService;

public class PizzasController implements Initializable{


    @FXML
    private TableColumn<Pizza, String> name;

    @FXML
    private TableColumn<Pizza, Float> price;

    @FXML
    private TableColumn<Pizza, Image> image;

    @FXML
    private TableColumn<Pizza, String> ingredients;

    @FXML
    private TableColumn<Pizza, Button> order;

    @FXML
    private TableColumn<Pizza, Button> delete;

    @FXML
    private TableColumn<Pizza, Button> modify;


    @FXML
    private TableView<Pizza> table;

    ObservableList<Pizza> pizzas = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setFixedCellSize(60.0);
        name.setCellValueFactory(new PropertyValueFactory<Pizza,String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Pizza,Float>("price"));
        image.setCellValueFactory(new PropertyValueFactory<Pizza,Image>("image"));
        image.setCellFactory(column -> {
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
        ingredients.setCellValueFactory(new PropertyValueFactory<Pizza, String>("ingredients"));
        ingredients.setCellFactory(column -> {
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


        order.setCellValueFactory(new PropertyValueFactory<Pizza, Button>("order"));
        order.setCellFactory(column -> {
            return new TableCell<Pizza, Button>() {
                @Override
                protected void updateItem(Button button, boolean empty) {
                    super.updateItem(button, empty);

                    if (button == null || empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

        delete.setCellValueFactory(new PropertyValueFactory<Pizza, Button>("delete"));
        delete.setCellFactory(column -> {
            return new TableCell<Pizza, Button>() {
                private final Button deleteButton = new Button("DELETE");

                {
                    deleteButton.setOnAction(event -> {
                        Pizza pizza = getTableView().getItems().get(getIndex());
                        PizzaService.deletePizza(pizza.getPizzaId());
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

        modify.setCellValueFactory(new PropertyValueFactory<Pizza, Button>("modify"));
        modify.setCellFactory(column -> {
            return new TableCell<Pizza, Button>() {
                @Override
                protected void updateItem(Button button, boolean empty) {
                    super.updateItem(button, empty);

                    if (button == null || empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

        refreshTable();
    }

    void refreshTable(){
        table.getItems().clear();
        try {
            ResultSet pizzaSet = PizzaService.getPizzas();
            while (pizzaSet.next()) {
                Pizza pizza = Pizza.createPizzaFromResultSet(pizzaSet);
                pizzas.add(pizza);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setItems(pizzas);
    }
}
