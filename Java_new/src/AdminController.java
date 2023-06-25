package src;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import front.OrderPizzaScene.OrderPizzaController;
import front.editPizzaScene.EditPizzaController;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;
import src.customer.Customer;
import src.customer.CustomerService;
import src.delivery.Delivery;
import src.delivery.DeliveryService;
import src.deliveryPerson.DeliveryPerson;
import src.deliveryPerson.DeliveryPersonService;
import src.divers.DiversService;
import src.ingredient.Ingredient;
import src.ingredient.IngredientService;
import src.order.OrderService;
import src.order.PizzaOrder;
import src.pizza.Pizza;
import src.pizza.PizzaService;
import src.stats.StatsService;
import src.vehicle.Vehicle;
import src.vehicle.VehicleService;

public class AdminController implements Initializable {
    //#region FXMl Variables
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
    private TableColumn<PizzaOrder, Timestamp> order_orderDate;
    @FXML
    private TableColumn<PizzaOrder, Button> order_cancel;
    @FXML
    private TableColumn<PizzaOrder, Button> order_updateStatus;
    @FXML
    private TableColumn<PizzaOrder, Button> order_assignDelivery;
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
    private Text stat_AverageDeliveryTime;

    @FXML
    private Text stat_AverageOrderPrice;

    @FXML
    private Text stat_BestCustomer;

    @FXML
    private Text stat_BestDeliveryPerson;

    @FXML
    private Text stat_BestIngredient;

    @FXML
    private Text stat_BestPizza;

    @FXML
    private Text stat_BestPizza1;

    @FXML
    private Text stat_MonthlyRevenue;

    @FXML
    private Text stat_SalesByPizzaSize;

    @FXML
    private Text stat_TotalRevenue;

    @FXML
    private Text stat_WorstDeliveryPerson;

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
    private TableColumn<DeliveryPerson, Button> deliveryPerson_editNameButton;

    @FXML
    private TableColumn<DeliveryPerson, Button> deliveryPerson_editVehicleButton;

    @FXML
    private Button deliveryPerson_refreshButton;

    @FXML
    private TableView<DeliveryPerson> deliveryPerson_table;

    ObservableList<DeliveryPerson> deliveryPersons = FXCollections.observableArrayList();

    // ----------------------------------------------- VEHICLE FXML  ----------------------------------------------------//
    @FXML
    private Button vehicle_addButton;

    @FXML
    private TableColumn<Vehicle, Button> vehicle_deleteButton;

    @FXML
    private TableColumn<Vehicle, Button> vehicle_editButton;

    @FXML
    private TableColumn<Vehicle, Integer> vehicle_id;

    @FXML
    private Button vehicle_refreshButton;

    @FXML
    private TableColumn<Vehicle, String> vehicle_type;

    @FXML
    private TableView<Vehicle> vehicle_table;

    @FXML
    private TableColumn<Vehicle, String> vehicle_model;

    ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
    //#endregion

    // ----------------------------------------------- DELIVERY FXML  ----------------------------------------------------//

    //#region deliveries FXML
    @FXML
    private TableColumn<Delivery, Integer> delivery_deliveryId;
    @FXML
    private TableColumn<Delivery, Integer> delivery_orderId;
    @FXML
    private TableColumn<Delivery, Integer> delivery_deliveryPersonId;
    @FXML
    private TableColumn<Delivery, String> delivery_deliveryPersonName;
    @FXML
    private TableColumn<Delivery, Integer> delivery_vehicleId;
    @FXML
    private TableColumn<Delivery, String> delivery_vehicleType;
    @FXML
    private TableColumn<Delivery, String> delivery_deliveryStatus;
    @FXML
    private TableColumn<Delivery, Timestamp> delivery_deliveryDate;
    @FXML
    private TableColumn<Delivery, Button> delivery_cancel;
    @FXML
    private TableColumn<Delivery, Button> delivery_updateStatus;

    @FXML
    private Button delivery_refreshButton;

    @FXML
    private TableView<Delivery> delivery_table;

    ObservableList<Delivery> deliveries = FXCollections.observableArrayList();
    //#endregion

    // ----------------------------------------------- INGREDIENT FXML  ----------------------------------------------------//

    //#region deliveries FXML
    @FXML
    private TableColumn<Ingredient, Integer> ingredient_ingredientId;
    @FXML
    private TableColumn<Ingredient, String> ingredient_ingredientName;
    
    @FXML
    private TableColumn<Ingredient, Button> ingredient_deleteButton;

    @FXML
    private Button ingredient_addButton;

    @FXML
    private Button ingredient_refreshButton;

    @FXML
    private TableView<Ingredient> ingredient_table;

    ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
    //#endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ----------------------------------------- PIZZAS --------------------------------------------------------//
        //#region pizzas
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
                        loader.setController(new OrderPizzaController(pizza,1));
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
        //#endregion

        // ---------------------------------------------- ORDERS --------------------------------------------------------//
        //#region orders

        order_table.setFixedCellSize(60.0);
        order_orderId.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Integer>("orderId"));
        order_customerName.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("customerName"));
        order_pizzaName.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("pizzaName"));
        order_pizzaSize.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("pizzaSize"));
        order_totalPrice.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Float>("totalPrice"));
        order_orderStatus.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("orderStatus"));
        order_deliveryStatus.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("deliveryStatus"));
        order_orderDate.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Timestamp>("orderDate"));
        order_cancel.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Button>("cancel"));
        order_updateStatus.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Button>("updateStatus"));
        order_assignDelivery.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Button>("assignDelivery"));

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
                        PizzaOrder order = getTableView().getItems().get(getIndex());
                        List<String> possibleUpdates = order.possibleUpdates();
                        if (possibleUpdates.size() == 0)
                        {
                            setGraphic(null);
                        }
                        else 
                        {
                            setGraphic(btn);
                            setAlignment(Pos.CENTER);
                            btn.setOnAction(event -> {
                                Dialog<String> dialog = new Dialog<>();
                                dialog.setTitle("Update Status");
    
                                ComboBox<String> statusComboBox = new ComboBox<>();
                                statusComboBox.getItems().addAll(possibleUpdates);
                                statusComboBox.getSelectionModel().select(possibleUpdates.get(0));
    
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
                }
            };
        });

        order_cancel.setCellFactory(column -> {
            return new TableCell<PizzaOrder, Button>() {
                @Override
                protected void updateItem(Button button, boolean empty) {
                    super.updateItem(button, empty);
                    if (button == null || empty) {
                        setGraphic(null);
                    } else {
                        PizzaOrder order = getTableView().getItems().get(getIndex());
                        if (!order.getOrderStatus().equals("ACCEPTED")) {
                            setGraphic(null);
                            return;
                        }

                        setGraphic(button);
                        setAlignment(Pos.CENTER);
                        button.setOnAction(event -> {
                            int orderId = order.getOrderId();
                            OrderService.cancelOrder(orderId);
                            refreshTable();
                        });
                    }
                }
            };
        });
        order_assignDelivery.setCellFactory(column -> {
            return new TableCell<PizzaOrder, Button>() {
                final Button btn = new Button("Assign delivery person");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        PizzaOrder order = getTableView().getItems().get(getIndex());
                        if (order.canAssignDelivery())
                        {
                            setGraphic(btn);
                            setAlignment(Pos.CENTER);

                            btn.setOnAction(event -> {
                                Dialog<DeliveryPerson> dialog = new Dialog<>();
                                dialog.setTitle("Select the delivery person's ID");
    
                                ComboBox<DeliveryPerson> deliveryComboBox = new ComboBox<>();
                                deliveryComboBox.setConverter(deliveryPersonConverter);
                                ResultSet deliveryPersons = DeliveryPersonService.getDeliveryPersons();
                                try {
                                    while (deliveryPersons.next()) {

                                        deliveryComboBox.getItems().add(DeliveryPerson.createDeliveryPersonFromResultSet(deliveryPersons));
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                deliveryComboBox.getSelectionModel().selectFirst();
    
                                ButtonType confirmButtonType = new ButtonType("Assign", ButtonBar.ButtonData.OK_DONE);
                                ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                                dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);
                                dialog.getDialogPane().setContent(deliveryComboBox);
    
                                dialog.setResultConverter(dialogButton -> {
                                    if (dialogButton == confirmButtonType) {
                                        return deliveryComboBox.getSelectionModel().getSelectedItem();
                                    }
                                    return null;
                                });
    
                                Optional<DeliveryPerson> result = dialog.showAndWait();
    
                                result.ifPresent(selectedDeliveryPerson -> {
                                    System.out.println("Assigned order to delivery person: " + selectedDeliveryPerson.getDeliveryPersonName());
                                    DeliveryService.addDelivery(selectedDeliveryPerson.getDeliveryPersonId(), order.getOrderId());
                                    refreshTable();
                                });
                            });
                        }
                        else
                        {
                            setGraphic(null);
                        }
                    }
                }
            };
        });

        //#endregion

        // ---------------------------------------------- CUSTOMERS --------------------------------------------------------//
        //#region CUSTOMERS
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
                        Customer customer = getTableView().getItems().get(getIndex());

                        if (customer.getCustomerId() == 1)
                        {
                            setGraphic(null);
                        }
                        else 
                        {
                            setGraphic(btn);
                            setAlignment(Pos.CENTER);
    
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
                        Customer customer = getTableView().getItems().get(getIndex());
                        if (customer.getCustomerId() == 1)
                        {
                            setGraphic(null);
                        }
                        else 
                        {
                            setGraphic(btn);
                            setAlignment(Pos.CENTER);
    
                            btn.setOnAction(event -> {
                                System.out.println("Deleting customer n°" + customer.getCustomerId());
                                CustomerService.deleteCustomer(customer.getCustomerId());
                                refreshTable();
                            });
                        }
                    }
                }
            };
        });
        //#endregion

        // ---------------------------------------------- VEHICLE --------------------------------------------------------//
        //#region Vehicle
        vehicle_table.setFixedCellSize(60.0);
        vehicle_id.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("vehicleId"));
        vehicle_type.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleType"));
        vehicle_model.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleModel"));
        vehicle_editButton.setCellValueFactory(new PropertyValueFactory<Vehicle, Button>("editName"));
        vehicle_refreshButton.setOnAction(event -> refreshTable());

        vehicle_addButton.setOnAction(event -> {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Create new vehicle");

            ComboBox<String> typeComboBox = new ComboBox<>();
            typeComboBox.getItems().addAll("CAR", "MOTORBIKE");
            typeComboBox.setValue("CAR");

            TextField modelField = new TextField();
            modelField.setPromptText("Enter vehicle model");

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.addRow(0, new Label("Type:"), typeComboBox);
            gridPane.addRow(1, new Label("Model:"), modelField);

            dialog.getDialogPane().setContent(gridPane);

            ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, cancelButtonType);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == applyButtonType) {
                    String type = typeComboBox.getValue();
                    String model = modelField.getText();
                    return new Pair<>(type, model);
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();

            result.ifPresent(newVehicle -> {
                String type = newVehicle.getKey();
                String model = newVehicle.getValue();
                VehicleService.addVehicle(type, model);
                refreshTable();
            });
        });
             
        vehicle_editButton.setCellFactory(column -> {
            return new TableCell<Vehicle, Button>() {
                final Button btn = new Button("Edit");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                        setAlignment(Pos.CENTER);
                        Vehicle vehicle = getTableView().getItems().get(getIndex());   
                            btn.setOnAction(event -> {
                            Dialog<Pair<String, String>> dialog = new Dialog<>();
                            dialog.setTitle("Edit Vehicle Details");

                            TextField modelField = new TextField(vehicle.getVehicleModel());
                            modelField.setPromptText("Enter new model");

                            ComboBox<String> typeComboBox = new ComboBox<>();
                            typeComboBox.getItems().addAll("CAR", "MOTORBIKE");
                            typeComboBox.setValue(vehicle.getVehicleType());

                            GridPane gridPane = new GridPane();
                            gridPane.setHgap(10);
                            gridPane.setVgap(10);
                            gridPane.addRow(0, new Label("Select type:"), typeComboBox);
                            gridPane.addRow(1, new Label("Enter new model:"), modelField);
                            dialog.getDialogPane().setContent(gridPane);

                            ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
                            ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                            dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, cancelButtonType);

                            dialog.setResultConverter(dialogButton -> {
                                if (dialogButton == applyButtonType) {
                                    return new Pair<>(typeComboBox.getValue(), modelField.getText());
                                }
                                return null;
                            });

                            Optional<Pair<String, String>> result = dialog.showAndWait();

                            result.ifPresent(newDetails -> {
                                VehicleService.updateVehicleField(vehicle.getVehicleId(), "vehicle_type", newDetails.getKey());
                                VehicleService.updateVehicleField(vehicle.getVehicleId(), "vehicle_model", newDetails.getValue());
                                refreshTable();
                            });
                        });
                    }
                }
            };
        });
              

        
        vehicle_deleteButton.setCellFactory(column -> {
            return new TableCell<Vehicle, Button>() {
                final Button btn = new Button("Delete");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                        setAlignment(Pos.CENTER);
                        Vehicle vehicle = getTableView().getItems().get(getIndex());

                        btn.setOnAction(event -> {
                            System.out.println("Deleting vehicle n°" + vehicle.getVehicleId());
                            VehicleService.deleteVehicle(vehicle.getVehicleId());
                            refreshTable();
                        });
                    }
                }
            };
        });

        //#endregion
        
        // ---------------------------------------------- INGREDIENTS --------------------------------------------------------//
        //#region Ingredients
        ingredient_table.setFixedCellSize(60.0);
        ingredient_deleteButton.setCellValueFactory(new PropertyValueFactory<Ingredient, Button>("deleteButton"));
        ingredient_ingredientId.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("ingredientId"));
        ingredient_ingredientName.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("ingredientName"));
        ingredient_refreshButton.setOnAction(event -> refreshTable());

        ingredient_addButton.setOnAction(event -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Create new ingredient");

            TextField nameField = new TextField("");
            nameField.setPromptText("Enter ingredient name");

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
                System.out.println("Created new ingredient: " + newName);
                IngredientService.addIngredient(newName);
                refreshTable();
            });
        });

        ingredient_deleteButton.setCellFactory(column -> {
            return new TableCell<Ingredient, Button>() {
                final Button btn = new Button("Delete");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                        setAlignment(Pos.CENTER);
                        Ingredient ingredient = getTableView().getItems().get(getIndex());

                        btn.setOnAction(event -> {
                            System.out.println("Deleting vehicle n°" + ingredient.getIngredientId());
                            IngredientService.deleteIngredient(ingredient.getIngredientId());
                            refreshTable();
                        });
                    }
                }
            };
        });


        //#endregion
        
        // ---------------------------------------------- DELIVERY PERSON --------------------------------------------------------//
        //#region DeliveryPerson
        deliveryPerson_table.setFixedCellSize(60.0);
        deliveryPerson_id.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, Integer>("deliveryPersonId"));
        deliveryPerson_name.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, String>("deliveryPersonName"));
        deliveryPerson_vehicle.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, String>("deliveryPersonVehicleDescription"));
        deliveryPerson_editNameButton.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, Button>("editNameButton"));
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


        deliveryPerson_editVehicleButton.setCellFactory(column -> {
            return new TableCell<DeliveryPerson, Button>() {
                final Button btn = new Button("Change vehicle");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        DeliveryPerson deliveryPerson = getTableView().getItems().get(getIndex());
                        setGraphic(btn);
                        setAlignment(Pos.CENTER);
                        btn.setOnAction(event -> {
                            Dialog<Vehicle> dialog = new Dialog<>();
                            dialog.setTitle("Change vehicle");
                            
                            ComboBox<Vehicle> vehicleComboBox = new ComboBox<>();
                            ResultSet vehicleSet = VehicleService.getVehicles();
                            List<Vehicle> vehicles = Vehicle.vehiclesFromResultSet(vehicleSet);
                            vehicleComboBox.setConverter(vehicleConverter);
                            vehicleComboBox.getItems().addAll(vehicles);

                            if (vehicles.size() != 0)
                            {
                                vehicleComboBox.getSelectionModel().select(vehicles.get(0));
                            }

                            ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
                            ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                            dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, cancelButtonType);
                            dialog.getDialogPane().setContent(vehicleComboBox);

                            dialog.setResultConverter(dialogButton -> {
                                if (dialogButton == applyButtonType) {
                                    return vehicleComboBox.getSelectionModel().getSelectedItem();
                                }
                                return null;
                            });

                            Optional<Vehicle> result = dialog.showAndWait();

                            result.ifPresent(selectedVehicle -> {
                                // UPDATE vehicle
                                System.out.println(selectedVehicle.getVehicleId());
                                DeliveryPersonService.updateDeliveryPersonVehicle(deliveryPerson.getDeliveryPersonId(), selectedVehicle.getVehicleId());
                                refreshTable();
                            });
                        });
                    }
                }
            };
        });


        deliveryPerson_editNameButton.setCellFactory(column -> {
            return new TableCell<DeliveryPerson, Button>() {
                final Button btn = new Button("Edit name");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        DeliveryPerson deliveryPerson = getTableView().getItems().get(getIndex());
                        if (deliveryPerson.getDeliveryPersonId() == 1)
                        {
                            setGraphic(null);
                        }
                        else 
                        {
                            setGraphic(btn);
                            setAlignment(Pos.CENTER);

                            btn.setOnAction(event -> {
                                Dialog<String> dialog = new Dialog<>();
                                dialog.setTitle("Edit Delivery Person Name");

                                TextField nameField = new TextField(deliveryPerson.getDeliveryPersonName());
                                nameField.setPromptText("Enter new name");

                                dialog.getDialogPane().setContent(new VBox(nameField));

                                ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
                                ButtonType cancelButtonType = new ButtonType("Cancel",
                                        ButtonBar.ButtonData.CANCEL_CLOSE);
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
                                    DeliveryPersonService.updateDeliveryPersonName(deliveryPerson.getDeliveryPersonId(), newName);
                                    refreshTable();
                                });
                            });
                        }
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
                        DeliveryPerson deliveryPerson = getTableView().getItems().get(getIndex());
                        if (deliveryPerson.getDeliveryPersonId() == 1)
                        {
                            setGraphic(null);
                        }
                        else 
                        {
                            setGraphic(btn);
                            setAlignment(Pos.CENTER);

                            btn.setOnAction(event -> {
                                System.out
                                        .println("Deleting delivery person n°" + deliveryPerson.getDeliveryPersonId());
                                DeliveryPersonService.deleteDeliveryPerson(deliveryPerson.getDeliveryPersonId());
                                refreshTable();
                            });
                        }
                    }
                }
            };
        });
        //#endregion

        // ---------------------------------------------- DELIVERY --------------------------------------------------------//
        //#region Delivery
        delivery_table.setFixedCellSize(60.0);
        delivery_deliveryId.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("deliveryId"));
        delivery_orderId.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("orderId"));
        delivery_deliveryPersonId.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("deliveryPersonId"));
        delivery_deliveryPersonName.setCellValueFactory(new PropertyValueFactory<Delivery, String>("deliveryPersonName"));
        delivery_vehicleId.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("vehicleId"));
        delivery_vehicleType.setCellValueFactory(new PropertyValueFactory<Delivery, String>("vehicleType"));
        delivery_deliveryStatus.setCellValueFactory(new PropertyValueFactory<Delivery, String>("deliveryStatus"));
        delivery_deliveryDate.setCellValueFactory(new PropertyValueFactory<Delivery, Timestamp>("deliveryDate"));
        delivery_cancel.setCellValueFactory(new PropertyValueFactory<Delivery, Button>("cancel"));
        delivery_updateStatus.setCellValueFactory(new PropertyValueFactory<Delivery, Button>("updateStatus"));
        delivery_refreshButton.setOnAction(event -> refreshTable());

        delivery_cancel.setCellFactory(column -> {
            return new TableCell<Delivery, Button>() {
                
                private final Button deleteButton = new Button("CANCEL");

                {
                    deleteButton.setOnAction(event -> {
                        Delivery delivery = getTableView().getItems().get(getIndex());
                        if (delivery.isCancellable())
                        {
                            DeliveryService.cancelDelivery(delivery.getDeliveryId());
                            System.out.println("Delete delivery '"+delivery.getDeliveryId()+"'");
                            refreshTable();
                        }
                    });
                }

                @Override
                protected void updateItem(Button button, boolean empty) {
                    super.updateItem(button, empty);

                    if (button == null || empty) {
                        setGraphic(null);
                    } else {
                        Delivery delivery = getTableView().getItems().get(getIndex());
                        if (delivery.isCancellable())
                        {
                            setGraphic(deleteButton);
                            setAlignment(Pos.CENTER);
                        }
                        else
                        {
                            setGraphic(null);
                        }
                    }
                }
            };
        });

        delivery_updateStatus.setCellFactory(column -> {
            return new TableCell<Delivery, Button>() {
                final Button btn = new Button("Update Status");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty ) {
                        setGraphic(null);
                    } else {
                        Delivery delivery = getTableView().getItems().get(getIndex());
                        String[] possibleUpdates = delivery.possibleUpdates();
                        if (possibleUpdates.length == 0)
                        {
                            setGraphic(null);
                        }
                        else
                        {
                            setGraphic(btn);
                            setAlignment(Pos.CENTER);
                            btn.setOnAction(event -> {
                                Dialog<String> dialog = new Dialog<>();
                                dialog.setTitle("Update Status");
    
                                ComboBox<String> statusComboBox = new ComboBox<>();
                                statusComboBox.getItems().addAll(possibleUpdates);
                                statusComboBox.getSelectionModel().select(possibleUpdates[0]);
    
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
                                    DeliveryService.updateDeliveryStatus(delivery.getDeliveryId(), selectedStatus);
                                    refreshTable();
                                });
                            });
                        }
                    }
                }
            };
        });
        //#endregion

        refreshTable();
    }
    //#endregion

    

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
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        order_table.setItems(orders);

        // ---------------------------------------------- DELIVERY --------------------------------------------------------//

        deliveries.clear();
        try 
        {
            ResultSet ordersSet = DeliveryService.getDeliveries();
            while (ordersSet.next()) {
                Delivery order = Delivery.createDeliveryFromResultSet(ordersSet);
                deliveries.add(order);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        delivery_table.setItems(deliveries);

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

        deliveryPersons.clear();
        try 
        {
            ResultSet deliveryPersonsSet = DeliveryPersonService.getDeliveryPersons();
            while (deliveryPersonsSet.next()) {
                DeliveryPerson deliveryPerson = DeliveryPerson.createDeliveryPersonFromResultSet(deliveryPersonsSet);
                deliveryPersons.add(deliveryPerson);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        deliveryPerson_table.setItems(deliveryPersons);

    // ---------------------------------------------- VEHICLE --------------------------------------------------------//

        vehicles.clear();
        try 
        {
            ResultSet vehiclesSet = VehicleService.getVehicles();
            while (vehiclesSet.next()) {
                Vehicle vehicle = Vehicle.createVehicleFromResultSet(vehiclesSet);
                vehicles.add(vehicle);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        vehicle_table.setItems(vehicles);
    }

    // ---------------------------------------------- STATS --------------------------------------------------------//
    public void refreshStats() {

        //#region Best Customer
        try {
            ResultSet rs = StatsService.GetBestCustomer();
            if (rs.next()) {
                int customerId = rs.getInt(1);
                String customerName = rs.getString(2);
                int totalOrders = rs.getInt(3);
                stat_BestCustomer.setText(String.format("The best customer is %s (id:%d) with a total of %d completed order(s).", customerName, customerId, totalOrders));
            }
            else stat_BestCustomer.setText("No Best Customer Stats");
        } catch (SQLException e) {
            stat_BestCustomer.setText("ERROR : Best Customer Stats");
        }
        //#endregion

        //#region Worst Delivery Person
        try {
            ResultSet rs = StatsService.GetWorstDeliveryPerson();
            if (rs.next()) {
                int deliveryPersonId = rs.getInt(1);
                String deliveryPersonName = rs.getString(2);
                String vehicleType = rs.getString(3);
                String vehicleModel = rs.getString(4);
                int totalLate = rs.getInt(5);
                stat_WorstDeliveryPerson.setText(String.format("The worst delivery person is %s (id:%d) with %d late delivery(ies), driving a '%s' (%s).", deliveryPersonName,deliveryPersonId,totalLate,vehicleModel,vehicleType));
            }
            else stat_WorstDeliveryPerson.setText("No Late Deliveries for the moment");
        } catch (SQLException e) {
            stat_WorstDeliveryPerson.setText("ERROR : Worst Delivery Person");
        }
        //#endregion

        //#region Best Delivery Person
        try {
            ResultSet rs = StatsService.GetBestDeliveryPerson();
            if (rs.next()) {
                int deliveryPersonId = rs.getInt(1);
                String deliveryPersonName = rs.getString(2);
                String vehicleType = rs.getString(3);
                String vehicleModel = rs.getString(4);
                int totalLate = rs.getInt(5);
                stat_BestDeliveryPerson.setText(String.format("The best delivery person is %s (id:%d) with %d completed delivery(ies), driving a '%s' (%s).", deliveryPersonName,deliveryPersonId,totalLate,vehicleModel,vehicleType));
            }
            else stat_BestDeliveryPerson.setText("No Complete Deliveries for the moment");
        } catch (SQLException e) {
            stat_BestDeliveryPerson.setText("ERROR : Best Delivery Person");
        }
        //#endregion

        //#region Best Pizza
        try {
            ResultSet rs = StatsService.GetMostOrderedPizza();
            if (rs.next()) {
                int pizzaId = rs.getInt(1);
                String pizzaName = rs.getString(2);
                int totalOrders = rs.getInt(3);
                stat_BestPizza.setText(String.format("The best pizza is %s (id:%d) with a total of %d completedorder(s).", pizzaName, pizzaId, totalOrders));
            }
            else stat_BestPizza.setText("No Best Pizza Stats");
        } catch (SQLException e) {
            stat_BestPizza.setText("ERROR : Best Pizza Stats");
        }
        //#endregion

        //#region Best Ingredient
        try {
            ResultSet rs = StatsService.GetMostPopularIngredient();
            if (rs.next()) {
                int ingredientId = rs.getInt(1);
                String ingredientName = rs.getString(2);
                int totalOrders = rs.getInt(3);
                stat_BestIngredient.setText(String.format("The best ingredient is %s (id:%d) present in pizzas with a total of %d completed order(s).", ingredientName, ingredientId, totalOrders));
            }
            else stat_BestIngredient.setText("No ingredient present in all pizzas");
        } catch (SQLException e) {
            stat_BestIngredient.setText("ERROR : Best Ingredient Stats");
        }
        //#endregion

        //#region Monthly Revenue
        try {
            ResultSet rs = DiversService.getMonthlyRevenue();
            if (rs.next()) {
                float monthlyRevenue = rs.getFloat(1);
                stat_MonthlyRevenue.setText(String.format("The turnover for this month is  : %,.2f $.", monthlyRevenue));
            }
            else stat_MonthlyRevenue.setText("No pizza sales this month.");
        } catch (SQLException e) {
            stat_MonthlyRevenue.setText("ERROR : Monthly Revenue");
        }
        //#endregion

        //#region Total Revenue
        try {
            ResultSet rs = DiversService.getTotalRevenue();
            if (rs.next()) {
                float totalRevenue = rs.getFloat(1);
                stat_TotalRevenue.setText(String.format("The total turnover is  : %,.2f $.", totalRevenue));
            }
            else stat_TotalRevenue.setText("No pizza sales.");
        } catch (SQLException e) {
            stat_TotalRevenue.setText("ERROR : Total Revenue");
        }
        //#endregion

        //#region Total Revenue
        try {
            ResultSet rs = DiversService.getAverageOrderPrice();
            if (rs.next()) {
                float averagePrice = rs.getFloat(1);
                stat_AverageOrderPrice.setText(String.format("The average order price is  : %,.2f $.", averagePrice));
            }
            else stat_AverageOrderPrice.setText("No pizza sales.");
        } catch (SQLException e) {
            stat_AverageOrderPrice.setText("ERROR : Average order price");
        }
        //#endregion

        //#region Sales by pizza side
        try {
            ResultSet rs = DiversService.getSalesByPizzaSize();
            String text = "Repartition of sales by pizza size :";
            while (rs.next()) {
                String pizzaSize = rs.getString(1);
                int salesCount = rs.getInt(2);
                float percentSales = rs.getFloat(3);
                text += String.format("\n- '%s' : %d pizza(s) sold -> %,.2f %% of total pizzas sold.",pizzaSize,salesCount,percentSales);
            }
            stat_SalesByPizzaSize.setText(text);
        } catch (SQLException e) {
            stat_SalesByPizzaSize.setText("ERROR : Sales by pizza size");
        }
        //#endregion

        //#region Average Delivery Time
        try {
            ResultSet rs = DiversService.getAverageDeliveryTime();
            if (rs.next()) {
                int time = rs.getInt(1);
                if(time==0)stat_AverageDeliveryTime.setText("No delivery completed for the moment");
                else stat_AverageDeliveryTime.setText(String.format("The delivery time average is %d minute(s).",time));
            }
            else stat_AverageDeliveryTime.setText("No delivery completed for the moment");
        } catch (SQLException e) {
            stat_AverageDeliveryTime.setText("ERROR : Average delivery time");
        }
        //#endregion
    }

    StringConverter<Vehicle> vehicleConverter = new StringConverter<Vehicle>() {
        @Override
        public String toString(Vehicle vehicle) {
            return vehicle.getVehicleType() + " - " + vehicle.getVehicleModel();
        }

        @Override
        public Vehicle fromString(String string) {
            // Conversion inverse non nécessaire pour ce cas
            return null;
        }
    };

    StringConverter<DeliveryPerson> deliveryPersonConverter = new StringConverter<DeliveryPerson>() {
        @Override
        public String toString(DeliveryPerson deliveryPerson) {
            return deliveryPerson.getDeliveryPersonName();
        }

        @Override
        public DeliveryPerson fromString(String string) {
            // Conversion inverse non nécessaire pour ce cas
            return null;
        }
    };
}
