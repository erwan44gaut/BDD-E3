package src.front.orderPizza;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

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
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.pizzeria.order.OrderService;
import src.pizzeria.pizza.Pizza;
import src.pizzeria.pizza.PizzaService;


public class OrderPizzaController implements Initializable {

    Pizza pizza;
    int clientId;
    String currentSize;
    int countOrder;

    public OrderPizzaController(Pizza pizza, int clientId){
        this.pizza = pizza;
        this.currentSize = "MEDIUM";
        this.clientId = clientId;
        this.countOrder = 0;
        try{
            ResultSet countOrderSet = OrderService.getOrderCountForCustomer(clientId);
            if(countOrderSet.next())this.countOrder =  countOrderSet.getInt(1);
            else this.countOrder = 0;
        }catch(Exception e){
            System.out.println("ERROR COUNTING PIZZAS");
            this.countOrder = 0;
        }
        
    }

    @FXML
    private ImageView image;

    @FXML
    private Text id;

    @FXML
    private Button order;

    @FXML
    private TableColumn<Object[], String> price;

    @FXML
    private TableColumn<Object[], Button> select;

    @FXML
    private Text select_name;

    @FXML
    private Text select_price;

    @FXML
    private Text select_size;

    @FXML
    private Text freePizza;

    @FXML
    private Text notEnoughMoney;

    @FXML
    private TableColumn<Object[],String> size;

    @FXML
    private TableView<Object[]> table;

    @FXML
    void orderAction(ActionEvent event) {
        if(currentSize.equals("LARGE") ||currentSize.equals("MEDIUM") || currentSize.equals("SMALL"))
        {
            if(OrderService.placeOrder(clientId, pizza.getPizzaId(), currentSize)!=-1){

                Scene scene = order.getScene();

                Stage stage = (Stage) scene.getWindow();

                stage.close();
            }
            else{
                notEnoughMoney.setText("NOT ENOUGH MONEY IN YOUR BALANCE ! RECHARGE PLEASE !");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if((countOrder+1)%10 == 0)freePizza.setText("FREE PIZZA AVAILABLE");
        else freePizza.setText((10 -(countOrder+1)%10)+" orders left before FREE PIZZA");
        image.setImage(pizza.getImage());
        select_name.setText("Name : "+pizza.getName());
        id.setText("ID : "+pizza.getPizzaId());
        select_size.setText("SIZE : "+currentSize);
        if(countOrder==0 || ((countOrder+1)%10 != 0))select_price.setText("PRICE : "+PizzaService.getAdjustedPrice(pizza.getPizzaId(), currentSize) +" $");
        else select_price.setText("PRICE : 0 $ (FREE PIZZA)");
        size.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[0]));
        price.setCellValueFactory(cellData ->  new SimpleStringProperty( cellData.getValue()[1] + " $"));
        select.setCellValueFactory(cellData -> new SimpleObjectProperty<>(new Button((String) cellData.getValue()[2])));
        select.setCellFactory(column -> {
            return new TableCell<Object[], Button>() {
                private final Button selectButton = new Button("SELECT");

                {
                    selectButton.setOnAction(event -> {
                        Object[] rowData = getTableRow().getItem();
                        currentSize = rowData[0].toString(); // Récupère la valeur de la colonne "size"
                        select_size.setText("SIZE : "+currentSize);
                        if((countOrder+1)%10 != 0)select_price.setText("PRICE : "+rowData[1].toString()+ " $");
                        else select_price.setText("PRICE : 0 $ (FREE PIZZA)");
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