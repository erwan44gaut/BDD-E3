package src;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.order.OrderService;
import src.pizza.Pizza;
import src.pizza.PizzaService;

public class OrderPizzaController implements Initializable {

    Pizza pizza;
    String currentSize;

    public OrderPizzaController(Pizza pizza){
        this.pizza = pizza;
        this.currentSize = "MEDIUM";
    }

    @FXML
    private ImageView image;

    @FXML
    private Text id;

    @FXML
    private Button order;

    @FXML
    private TableColumn<Object[], Float> price;

    @FXML
    private TableColumn<Object[], Button> select;

    @FXML
    private Text select_name;

    @FXML
    private Text select_price;

    @FXML
    private Text select_size;

    @FXML
    private TableColumn<Object[],String> size;

    @FXML
    private TableView<Object[]> table;

    @FXML
    void orderAction(ActionEvent event) {
        if(currentSize.equals("LARGE") ||currentSize.equals("MEDIUM") || currentSize.equals("SMALL"))
        {
            OrderService.placeOrder(1, pizza.getPizzaId(), currentSize);

            Scene scene = order.getScene();

            Stage stage = (Stage) scene.getWindow();

            stage.close();

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        image.setImage(pizza.getImage());
        select_name.setText("Name : "+pizza.getName());
        id.setText("ID : "+pizza.getPizzaId());
        select_size.setText("SIZE : "+currentSize);
        select_price.setText("PRICE : "+PizzaService.getAdjustedPrice(pizza.getPizzaId(), currentSize));
        size.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[0]));
        price.setCellValueFactory(cellData -> new SimpleObjectProperty<>((Float) cellData.getValue()[1]));
        select.setCellValueFactory(cellData -> new SimpleObjectProperty<>(new Button((String) cellData.getValue()[2])));
        select.setCellFactory(column -> {
            return new TableCell<Object[], Button>() {
                private final Button selectButton = new Button("SELECT");

                {
                    selectButton.setOnAction(event -> {
                        Object[] rowData = getTableRow().getItem();
                        currentSize = rowData[0].toString(); // Récupère la valeur de la colonne "size"
                        select_size.setText("SIZE : "+currentSize);
                        select_price.setText("PRICE : "+rowData[1].toString());
                    });
                }

                @Override
                protected void updateItem(Button button, boolean empty) {
                    super.updateItem(button, empty);

                    if (button == null || empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(selectButton);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

        // Autres configurations des colonnes si nécessaire

        // Remplir les données du tableau
        // Remplir les données du tableau
        table.setItems(FXCollections.observableArrayList(
                new Object[]{"SMALL", PizzaService.getAdjustedPrice(pizza.getPizzaId(),"SMALL"), "SELECT"},
                new Object[]{"MEDIUM", PizzaService.getAdjustedPrice(pizza.getPizzaId(),"MEDIUM"), "SELECT"},
                new Object[]{"LARGE", PizzaService.getAdjustedPrice(pizza.getPizzaId(),"LARGE"), "SELECT"}
        ));
    }
}