package src;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;


import front.OrderPizzaScene.OrderPizzaController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import src.customer.CustomerService;
import src.order.OrderService;
import src.order.PizzaOrder;
import src.pizza.Pizza;
import src.pizza.PizzaService;

public class ClientController implements Initializable{

    int clientId;

    public ClientController(int id){
        this.clientId = id;
    }

// --------------------------------------------------- ORDERS FXML  -----------------------------------------------------------------//

    @FXML
    private TableColumn<PizzaOrder, Integer> order_orderId;
    @FXML
    private TableColumn<PizzaOrder, String> order_pizzaName;
    @FXML
    private TableColumn<PizzaOrder, String> order_pizzaSize;
    @FXML
    private TableColumn<PizzaOrder, Float> order_totalPrice;
    @FXML
    private TableColumn<PizzaOrder, String> order_orderStatus;
    @FXML
    private TableColumn<PizzaOrder, String> order_deliveryStatus;
    @FXML
    private TableColumn<PizzaOrder, Timestamp> order_orderDate;
    @FXML
    private TableColumn<PizzaOrder, Button> order_cancel;
    @FXML
    private Button order_refreshButton;

    @FXML
    private TableView<PizzaOrder> order_table;

    ObservableList<PizzaOrder> orders = FXCollections.observableArrayList();

// --------------------------------------------------- PIZZAS FXML  -----------------------------------------------------------------//
    @FXML
    private TableColumn<Pizza, String> pizza_name;

    @FXML
    private TableColumn<Pizza, String> pizza_price;

    @FXML
    private TableColumn<Pizza, Image> pizza_image;

    @FXML
    private TableColumn<Pizza, String> pizza_ingredients;

    @FXML
    private TableColumn<Pizza, Button> pizza_order;

    @FXML
    private TableView<Pizza> pizza_table;

    @FXML
    private Button pizza_refreshButton;

    ObservableList<Pizza> pizzas = FXCollections.observableArrayList();

// --------------------------------------------------- PROFILE FXML  -----------------------------------------------------------------//
    @FXML
    private Text profile_balance;

    @FXML
    private Button profile_edit;

    @FXML
    private Text profile_id;

    @FXML
    private Text profile_name;

    @FXML
    private Button profile_recharge;

    @FXML
    private Button profile_refresh;

    @FXML
    void profile_editAction(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Edit Customer Name");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter new name");

        dialog.getDialogPane().setContent(new VBox(nameField));

        ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, cancelButtonType);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyButtonType) {
                return nameField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(newName -> {
            System.out.println("Updating name to: " + newName);
            CustomerService.updateCustomerField(clientId, "customer_name", newName);
            refreshProfile();
        });
    }

    @FXML
    void profile_rechargeAction(ActionEvent event) {
        Dialog<Float> dialog = new Dialog<>();
        dialog.setTitle("Recharge balance");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount to recharge");

        dialog.getDialogPane().setContent(new VBox(amountField));

        ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, cancelButtonType);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyButtonType) {
                String amountText = amountField.getText();
                try {
                    Float amount = Float.parseFloat(amountText);
                    return amount;
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });

        Optional<Float> result = dialog.showAndWait();

        result.ifPresent(amount -> {
            System.out.println("Recharging balance with amount: " + amount);
            CustomerService.rechargeBalance(clientId, amount);
            refreshProfile();
        });
    }

    @FXML
    void profile_refreshAction(ActionEvent event) {
        refreshTable();
    }

    @FXML
    void pizza_refreshAction(ActionEvent event) {
        refreshTable();
    }

    @FXML
    void order_refreshAction(ActionEvent event) {
        refreshTable();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
        // ----------------------------------------- PIZZAS --------------------------------------------------------//
        pizza_table.setFixedCellSize(60.0);
        pizza_name.setCellValueFactory(new PropertyValueFactory<Pizza,String>("name"));
        pizza_price.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrice() + " $"));
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
                private final Button pizza_orderButton = new Button("ORDER");

                {
                    pizza_orderButton.setOnAction(event -> {
                        Pizza pizza = getTableView().getItems().get(getIndex());
                        System.out.println("ORDER");
                        Stage stage = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../front/OrderPizzaScene/OrderPizza.fxml"));
                        loader.setController(new OrderPizzaController(pizza,clientId));
                        Parent root;
                        try {
                            root = loader.load();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setOnHiding(e -> {
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
                        setGraphic(pizza_orderButton);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

        pizza_refreshButton.setOnAction(event -> refreshTable());

        order_table.setFixedCellSize(60.0);
        order_orderId.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Integer>("orderId"));
        order_pizzaName.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("pizzaName"));
        order_pizzaSize.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("pizzaSize"));
        order_totalPrice.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Float>("totalPrice"));
        order_orderStatus.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("orderStatus"));
        order_deliveryStatus.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("deliveryStatus"));
        order_orderDate.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Timestamp>("orderDate"));
        order_cancel.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Button>("cancel"));
        order_refreshButton.setOnAction(event -> refreshTable());

        order_cancel.setCellFactory(column -> 
        {
            return new TableCell<PizzaOrder, Button>() 
            {
                @Override
                protected void updateItem(Button button, boolean empty) 
                {   
                    super.updateItem(button, empty);
                    if (button == null || empty) 
                    {
                        setGraphic(null);
                    } 
                    else 
                    {
                        PizzaOrder order = getTableView().getItems().get(getIndex());
                        if (!order.getOrderStatus().equals("ACCEPTED"))
                        {
                            setGraphic(null);
                            return;
                        }

                        setGraphic(button);
                        setAlignment(Pos.CENTER);
                        button.setOnAction(event -> 
                        {
                            int orderId = order.getOrderId();
                            OrderService.cancelOrder(orderId);
                            refreshTable();
                        });
                    }
                }
            };
        });
        refreshTable();
        profile_id.setText("Id : "+clientId);
    }

    private void refreshTable(){
        // ---------------------------------------------- PIZZA --------------------------------------------------------//
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
        // ---------------------------------------------- ORDERS --------------------------------------------------------//
        orders.clear();
        try 
        {
            ResultSet ordersSet = OrderService.getOrdersForCustomer(clientId);
            while (ordersSet.next()) {
                PizzaOrder order = PizzaOrder.createOrderFromResultSet(ordersSet);
                orders.add(order);
            }
        } 
        catch (Exception e) 
        {
           System.out.println("ERROR LOADING ORDERS");
        }
        order_table.setItems(orders);
        refreshProfile();
    }

    void refreshProfile(){
        try{
            ResultSet customerSet = CustomerService.getCustomer(clientId);
            if(customerSet.next()){
                String customerName = customerSet.getString("customer_name");
                Float customerBalance = customerSet.getFloat("customer_balance");
                profile_name.setText("Name : "+customerName);
                profile_balance.setText("Balance : "+customerBalance+" $");
            }
            else{
                System.out.println("aaaaa");
            }
        }catch(Exception e){
            System.out.println("ERROR refreshing profile");
        }
    }
}