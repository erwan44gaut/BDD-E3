package src;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import front.OrderPizzaScene.OrderPizzaController;
import front.editPizzaScene.EditPizzaController;
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
import javafx.scene.control.ComboBox;
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
import src.customer.Customer;
import src.customer.CustomerService;
import src.deliveryPerson.DeliveryPerson;
import src.deliveryPerson.DeliveryPersonService;
import src.ingredient.IngredientService;
import src.order.OrderService;
import src.order.PizzaOrder;
import src.pizza.Pizza;
import src.pizza.PizzaService;
import src.stats.StatsService;

public class AdminController implements Initializable{

// --------------------------------------------------- PIZZAS FXML  -----------------------------------------------------------------//
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

// --------------------------------------------------- ORDERS FXML  -----------------------------------------------------------------//

@FXML
    private TableColumn<PizzaOrder, Integer> order_orderId;
    @FXML
    private TableColumn<PizzaOrder, String> order_customerName;
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
    private TableColumn<PizzaOrder, Date> order_orderDate;
    @FXML
    private TableColumn<PizzaOrder, Button> order_cancel;
    @FXML
    private TableColumn<PizzaOrder, Button> order_updateStatus;
    @FXML
    private Button order_refreshButton;

    @FXML
    private TableView<PizzaOrder> order_table;

    ObservableList<PizzaOrder> orders = FXCollections.observableArrayList();

// --------------------------------------------------- CUSTOMERS FXML  --------------------------------------------------------------//

    @FXML
    private TableColumn<Customer, Integer> customer_customerId;
    @FXML
    private TableColumn<Customer, String> customer_customerName;
    @FXML
    private TableColumn<Customer, Float> customer_customerBalance;
    @FXML
    private TableColumn<Customer, Button> customer_rechargeBalance;
    @FXML
    private TableColumn<Customer, Button> customer_editName;
    @FXML
    private TableColumn<Customer, Button> customer_delete;

    @FXML
    private Button customer_addButton;

    @FXML
    private Button customer_refreshButton;
    @FXML
    private TableView<Customer> customer_table;

    ObservableList<Customer> customers = FXCollections.observableArrayList();

    // --------------------------------------------------- STATS FXML  -----------------------------------------------------------------//

    @FXML
    private Text stat_bestIngredientTextField;

    @FXML
    private Text stat_bestPizzaTextField;

    @FXML
    private Text stat_idBestCustomerTextField;

    @FXML
    private Text stat_nameBestCustomerTextField;

    @FXML
    private Text stat_nameBestDeliveryPersonTextField;

    @FXML
    private Text stat_nameWorstDeliveryPersonTextField;

    @FXML
    private Text stat_totalCommandeBestDeliveryPersonTextField;

    @FXML
    private Text stat_totalCommandeBestPizzaTextField;

    @FXML
    private Text stat_totalCommandeWorstDeliveryPersonTextField;

    @FXML
    private Text stat_vehiculeBestDeliveryPersonTextField;

    @FXML
    private Text stat_vehiculeWorstDeliveryPersonTextField;

    @FXML
    private Text stat_numberBestIngredientTextField;

    @FXML
    private Button stat_refreshButton;

    @FXML
    void stat_onClick(ActionEvent event) {
        refreshStats();
        System.out.println("Refreshed");
    }

    // ----------------------------------------------- DELIVERY PERSON FXML  ----------------------------------------------------//
    
    @FXML
    private Button deliveryPerson_addButton;

    @FXML
    private TableColumn<DeliveryPerson, Button> deliveryPerson_delete;

    @FXML
    private TableColumn<DeliveryPerson, Integer> deliveryPerson_id;

    @FXML
    private TableColumn<DeliveryPerson, String> deliveryPerson_name;

    @FXML
    private TableColumn<DeliveryPerson, String> deliveryPerson_vehicle;

    @FXML
    private TableColumn<DeliveryPerson, Button> deliveryPerson_editName;

    @FXML
    private Button deliveryPerson_refreshButton;

    @FXML
    private TableView<DeliveryPerson> deliveryPerson_table;

    ObservableList<DeliveryPerson> deliveryPersons = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ----------------------------------------- PIZZAS --------------------------------------------------------//
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
                private final Button pizza_orderButton = new Button("ORDER");

                {
                    pizza_orderButton.setOnAction(event -> {
                        Pizza pizza = getTableView().getItems().get(getIndex());
                        System.out.println("ORDER");
                        Stage stage = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../front/OrderPizzaScene/OrderPizza.fxml"));
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
                        setGraphic(pizza_orderButton);
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
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../front/editPizzaScene/EditPizza.fxml"));
                        loader.setController(new EditPizzaController(pizza));
                        Parent root;
                        try {
                            root = loader.load();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setOnCloseRequest(e -> {
                            refreshTable();
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

        // ---------------------------------------------- ORDERS --------------------------------------------------------//
        order_table.setFixedCellSize(60.0);
        order_orderId.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Integer>("orderId"));
        order_customerName.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("customerName"));
        order_pizzaName.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("pizzaName"));
        order_pizzaSize.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("pizzaSize"));
        order_totalPrice.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Float>("totalPrice"));
        order_orderStatus.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("orderStatus"));
        order_deliveryStatus.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("deliveryStatus"));
        order_orderDate.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Date>("orderDate"));
        order_cancel.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Button>("cancel"));
        order_updateStatus.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Button>("updateStatus"));
        order_refreshButton.setOnAction(event -> refreshTable());
        
        order_updateStatus.setCellFactory(column -> {
            return new TableCell<PizzaOrder, Button>() {
                final Button btn = new Button("Update Status");
                
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                        setAlignment(Pos.CENTER);
                        PizzaOrder order = getTableView().getItems().get(getIndex());
                        String orderStatus = order.getOrderStatus();
                        btn.setOnAction(event -> {
                            Dialog<String> dialog = new Dialog<>();
                            dialog.setTitle("Update Status");
                            
                            ComboBox<String> statusComboBox = new ComboBox<>();
                            statusComboBox.getItems().addAll("ACCEPTED", "IN_PREPARATION", "IN_DELIVERY", "COMPLETED", "CANCELED");
                            statusComboBox.getSelectionModel().select(orderStatus);
                            
                            ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
                            ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                            dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, cancelButtonType);
                            dialog.getDialogPane().setContent(statusComboBox);

                            dialog.setResultConverter(dialogButton -> {
                                if (dialogButton == applyButtonType) {
                                    return statusComboBox.getSelectionModel().getSelectedItem();
                                }
                                return null;
                            });

                            Optional<String> result = dialog.showAndWait();

                            result.ifPresent(selectedStatus -> {
                                System.out.println("Updating status: " + selectedStatus);
                                OrderService.updateStatus(order.getOrderId(), selectedStatus);
                                refreshTable();
                            });
                        });
                    }
                }
            };
        });


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
        // ---------------------------------------------- CUSTOMERS --------------------------------------------------------//
        customer_table.setFixedCellSize(60.0);
        customer_customerId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        customer_customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customer_customerBalance.setCellValueFactory(new PropertyValueFactory<Customer, Float>("customerBalance"));
        customer_rechargeBalance.setCellValueFactory(new PropertyValueFactory<Customer, Button>("rechargeBalance"));
        customer_editName.setCellValueFactory(new PropertyValueFactory<Customer, Button>("editName"));
        customer_refreshButton.setOnAction(event -> refreshTable());

        customer_addButton.setOnAction(event -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Create new customer");

            TextField nameField = new TextField("");
            nameField.setPromptText("Enter customer name");

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
                System.out.println("Created new customer: " + newName);
                CustomerService.addCustomer(newName);
                refreshTable();
            });
        });

        customer_editName.setCellFactory(column -> {
            return new TableCell<Customer, Button>() {
                final Button btn = new Button("Edit name");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                        setAlignment(Pos.CENTER);
                        Customer customer = getTableView().getItems().get(getIndex());

                        btn.setOnAction(event -> {
                            Dialog<String> dialog = new Dialog<>();
                            dialog.setTitle("Edit Customer Name");

                            TextField nameField = new TextField(customer.getCustomerName());
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
                                CustomerService.updateCustomerField(customer.getCustomerId(), "customer_name", newName);
                                refreshTable();
                            });
                        });
                    }
                }
            };
        });

        customer_rechargeBalance.setCellFactory(column -> {
            return new TableCell<Customer, Button>() {
                final Button btn = new Button("Recharge balance");
                
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                        setAlignment(Pos.CENTER);
                        Customer customer = getTableView().getItems().get(getIndex());

                        btn.setOnAction(event -> {
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
                                CustomerService.rechargeBalance(customer.getCustomerId(), amount);
                                refreshTable();
                            });
                        });
                    }
                }
            };
        });

                
        customer_delete.setCellFactory(column -> {
            return new TableCell<Customer, Button>() {
                final Button btn = new Button("Delete");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                        setAlignment(Pos.CENTER);
                        Customer customer = getTableView().getItems().get(getIndex());

                        btn.setOnAction(event -> {
                            System.out.println("Deleting customer n°" + customer.getCustomerId());
                            CustomerService.deleteCustomer(customer.getCustomerId());
                            refreshTable();
                        });
                    }
                }
            };
        });

        // ---------------------------------------------- DELIVERY PERSON --------------------------------------------------------//

        deliveryPerson_table.setFixedCellSize(60.0);
        deliveryPerson_id.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, Integer>("delivery_person_id"));
        deliveryPerson_name.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, String>("delivery_person_name"));
        deliveryPerson_editName.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, Button>("editName"));
        deliveryPerson_refreshButton.setOnAction(event -> refreshTable());

        deliveryPerson_addButton.setOnAction(event -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Create new delivery person");

            TextField nameField = new TextField("");
            nameField.setPromptText("Enter delivery person name");

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
                System.out.println("Created new delivery person: " + newName);
                DeliveryPersonService.addDeliveryPerson(newName);
                refreshTable();
            });
        });

        deliveryPerson_editName.setCellFactory(column -> {
            return new TableCell<DeliveryPerson, Button>() {
                final Button btn = new Button("Edit name");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                        setAlignment(Pos.CENTER);
                        DeliveryPerson customer = getTableView().getItems().get(getIndex());

                        btn.setOnAction(event -> {
                            Dialog<String> dialog = new Dialog<>();
                            dialog.setTitle("Edit Delivery Person Name");

                            TextField nameField = new TextField(customer.getDeliveryPersonName());
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
                                DeliveryPersonService.updateDeliveryPersonField(customer.getDeliveryPersonId(), "delivery_person_name", newName);
                                refreshTable();
                            });
                        });
                    }
                }
            };
        });
                
        deliveryPerson_delete.setCellFactory(column -> {
            return new TableCell<DeliveryPerson, Button>() {
                final Button btn = new Button("Delete");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                        setAlignment(Pos.CENTER);
                        DeliveryPerson deliveryPerson = getTableView().getItems().get(getIndex());

                        btn.setOnAction(event -> {
                            System.out.println("Deleting delivery person n°" + deliveryPerson.getDeliveryPersonId());
                            DeliveryPersonService.deleteDeliveryPerson(deliveryPerson.getDeliveryPersonId());
                            refreshTable();
                        });
                    }
                }
            };
        });


        refreshTable();
    }

    void refreshTable(){
        refreshStats();
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
            ResultSet ordersSet = OrderService.getOrders();
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
        // ---------------------------------------------- CUSTOMERS --------------------------------------------------------//
        customers.clear();
        try 
        {
            ResultSet customersSet = CustomerService.getCustomers();
            while (customersSet.next()) {
                Customer order = Customer.createCustomerFromResultSet(customersSet);
                customers.add(order);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        customer_table.setItems(customers);

        // ---------------------------------------------- DELIVERY PERSON --------------------------------------------------------//

        /*deliveryPersons.clear();
        try 
        {
            ResultSet deliveryPersonsSet = DeliveryPersonService.getDeliveryPersons();
            while (deliveryPersonsSet.next()) {
                DeliveryPerson order = DeliveryPerson.createDeliveryPersonFromResultSet(deliveryPersonsSet);
                deliveryPersons.add(order);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        deliveryPerson_table.setItems(deliveryPersons);*/
    }

    public void refreshStats() {
        int customerId = -1;
        int worstDeliveryPersonId = -1;
        int bestDeliveryPersonId = -1;
        int mostOrderedPizza = -1;
        int mostPopularIngredient = -1;

        //#region Best delivery person
        try {
            ResultSet rs = StatsService.GetBestCustomer();
            if (rs.next()) {
                customerId = rs.getInt(1);
                stat_idBestCustomerTextField.setText(String.valueOf(customerId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (customerId != -1) {
            try {
                ResultSet rs = CustomerService.getCustomer(customerId);
                if (rs.next()) {
                    stat_nameBestCustomerTextField.setText(rs.getString("customer_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //#endregion

        //#region Worst delivery person
        try {
            ResultSet rs = StatsService.GetWorstDeliveryPerson();
            if (rs.next()) {
                worstDeliveryPersonId = rs.getInt(1);
                stat_vehiculeWorstDeliveryPersonTextField.setText(rs.getString(2));
                stat_totalCommandeWorstDeliveryPersonTextField.setText(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (worstDeliveryPersonId != -1) {
            try {
                ResultSet rs = DeliveryPersonService.getDeliveryPersonById(worstDeliveryPersonId);
                if (rs.next()) {
                    stat_nameWorstDeliveryPersonTextField.setText(rs.getString("delivery_person_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //#endregion

        //#region Best delivery person
        try {
            ResultSet rs = StatsService.GetBestDeliveryPerson();
            if (rs.next()) {
                bestDeliveryPersonId = rs.getInt(1);
                stat_vehiculeBestDeliveryPersonTextField.setText(rs.getString(2));
                stat_totalCommandeBestDeliveryPersonTextField.setText(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (worstDeliveryPersonId != -1) {
            try {
                ResultSet rs = DeliveryPersonService.getDeliveryPersonById(bestDeliveryPersonId);
                if (rs.next()) {
                    stat_nameBestDeliveryPersonTextField.setText(rs.getString("delivery_person_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //#endregion

        //#region Most ordered pizza
        try {
            ResultSet rs = StatsService.GetMostOrderedPizza();
            if (rs.next()) {
                mostOrderedPizza = rs.getInt(1);
                stat_totalCommandeBestPizzaTextField.setText(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (worstDeliveryPersonId != -1) {
            try {
                ResultSet rs = PizzaService.getPizzaById(mostOrderedPizza);
                if (rs.next()) {
                    stat_bestPizzaTextField.setText(rs.getString("pizza_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //#endregion

        //#region Most ordered pizza ingredient
        try {
            ResultSet rs = StatsService.GetMostPopularIngredient();
            if (rs.next()) {
                mostPopularIngredient = rs.getInt(1);
                stat_numberBestIngredientTextField.setText(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (worstDeliveryPersonId != -1) {
            try {
                ResultSet rs = IngredientService.getIngredientById(mostPopularIngredient);
                if (rs.next()) {
                    stat_bestIngredientTextField.setText(rs.getString("ingredient_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //#endregion
    }

}
