package src;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import src.delivery.Delivery;
import src.delivery.DeliveryService;
import src.deliveryPerson.DeliveryPersonService;
import src.order.OrderService;
import src.order.PizzaOrder;
import src.vehicle.Vehicle;
import src.vehicle.VehicleService;

public class DeliveryPersonController implements Initializable{

    int deliveryPersonId;

    public DeliveryPersonController(int id){
        this.deliveryPersonId = id;
    }

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
    private TableColumn<PizzaOrder, String> order_orderStatus;
    @FXML
    private TableColumn<PizzaOrder, Timestamp> order_orderDate;
    @FXML
    private TableColumn<PizzaOrder, Button> order_assignDelivery;
    @FXML
    private Button order_refreshButton;

    @FXML
    private TableView<PizzaOrder> order_table;

    ObservableList<PizzaOrder> orders = FXCollections.observableArrayList();

    // --------------------------------------------------- DELIVERY FXML  -----------------------------------------------------------------//
    @FXML
    private TableColumn<Delivery, Integer> delivery_deliveryId;
    @FXML
    private TableColumn<Delivery, Integer> delivery_orderId;
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

// --------------------------------------------------- PROFILE FXML  -----------------------------------------------------------------//
    @FXML
    private Text profile_balance;

    @FXML
    private Button profile_editName;

    @FXML
    private Button profile_editVehicle;

    @FXML
    private Text profile_id;

    @FXML
    private Text profile_name;

    @FXML
    private Text profile_vehicle;
    

    @FXML
    private Button profile_refresh;

    @FXML
    void profile_editNameAction(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Edit name");

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
            DeliveryPersonService.updateDeliveryPersonName(deliveryPersonId, newName);
            refreshProfile();
        });
    }

    @FXML
    void profile_editVehicleAction(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Edit Vehicle");

        final ComboBox<Vehicle> comboBox = new ComboBox<>();
        {
            comboBox.setConverter(vehicleConverter);
            comboBox.setPromptText("Change vehicle");
            comboBox.setOnAction(e -> {
                Vehicle selectedVehicle = comboBox.getValue();
                DeliveryPersonService.updateDeliveryPersonVehicle(deliveryPersonId, selectedVehicle.getVehicleId());
                refreshTable();
            });

            
            ObservableList<Vehicle> vehiclesList = FXCollections.observableArrayList();
            try {
                ResultSet vehicles = VehicleService.getAllVehicleModelUnassigned();
                while (vehicles.next()) {
                    vehiclesList.add(Vehicle.createVehicleFromResultSet(vehicles));
                }
                comboBox.setItems(vehiclesList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        
        dialog.getDialogPane().setContent(new VBox(comboBox));


        ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, cancelButtonType);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyButtonType) {
                return "";
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(vehicleField -> {
            Vehicle selectedVehicle = comboBox.getValue();
            DeliveryPersonService.updateDeliveryPersonVehicle(deliveryPersonId, selectedVehicle.getVehicleId());
            System.out.println("Updating vehicle to: " + vehicleField);
            refreshTable();
            refreshProfile();
        });
    }

    @FXML
    void profile_refreshAction(ActionEvent event) {
        refreshTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        order_table.setFixedCellSize(60.0);
        order_orderId.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Integer>("orderId"));
        order_customerName.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("customerName"));
        order_pizzaName.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("pizzaName"));
        order_pizzaSize.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("pizzaSize"));
        order_orderStatus.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("orderStatus"));
        order_orderDate.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Timestamp>("orderDate"));
        order_assignDelivery.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Button>("assignDelivery"));

        order_refreshButton.setOnAction(event -> refreshTable());
        order_assignDelivery.setCellFactory(column -> {
            return new TableCell<PizzaOrder, Button>() {
                final Button btn = new Button("Take this delivery");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        PizzaOrder order = getTableView().getItems().get(getIndex());
                        setGraphic(btn);
                        setAlignment(Pos.CENTER);
                        
                        btn.setOnAction(event -> {
                            DeliveryService.addDelivery(deliveryPersonId, order.getOrderId());
                            refreshTable();
                            refreshProfile();
                        });
                    }
                }
            };
        });
        
        
        delivery_table.setFixedCellSize(60.0);
        delivery_deliveryId.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("deliveryId"));
        delivery_orderId.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("orderId"));
        delivery_deliveryStatus.setCellValueFactory(new PropertyValueFactory<Delivery, String>("deliveryStatus"));
        delivery_deliveryDate.setCellValueFactory(new PropertyValueFactory<Delivery, Timestamp>("deliveryDate"));
        delivery_cancel.setCellValueFactory(new PropertyValueFactory<Delivery, Button>("cancel"));
                
        delivery_updateStatus.setCellFactory(column -> {
            return new TableCell<Delivery, Button>() {
                final Button startButton = new Button("Start delivery");
                {
                    startButton.setOnAction(event -> {
                        Delivery delivery = getTableView().getItems().get(getIndex());
                        if (delivery.isCancellable()) {
                            DeliveryService.updateDeliveryStatus(delivery.getDeliveryId(), "IN_PROGRESS");
                            System.out.println("Starting delivery '" + delivery.getDeliveryId() + "'");
                            refreshTable();
                            refreshProfile();
                        }
                    });
                }
                
                final Button finishButton = new Button("Finish delivery");
                {
                    finishButton.setOnAction(event -> {
                        Delivery delivery = getTableView().getItems().get(getIndex());
                        DeliveryService.finishDelivery(delivery.getDeliveryId());
                        System.out.println("Finished delivery '"+delivery.getDeliveryId()+"'");
                        refreshTable();
                        refreshProfile();
                    });
                }

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty ) {
                        setGraphic(null);
                    } else {
                        Delivery delivery = getTableView().getItems().get(getIndex());
                        if (delivery.getDeliveryStatus().equals("ACCEPTED"))
                        {
                            setGraphic(startButton);
                            setAlignment(Pos.CENTER);
                        }
                        else if (delivery.getDeliveryStatus().equals("IN_PROGRESS"))
                        {
                            setGraphic(finishButton);
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
                            refreshProfile();
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


        delivery_refreshButton.setOnAction(event -> refreshTable());
        
        //TODO: update name button
        //TODO: update car button

        refreshTable();
        refreshProfile();
        profile_id.setText("Id : "+deliveryPersonId);
    }

    private void refreshTable(){
        
        // ---------------------------------------------- ORDERS --------------------------------------------------------//
        orders.clear();
        try 
        {
            ResultSet ordersSet = OrderService.getOrdersWaitingForDelivery();
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

        // ---------------------------------------------- DELIVERY  --------------------------------------------------------//

        deliveries.clear();
        try 
        {
            ResultSet deliveriesSet = DeliveryService.getDeliveriesOfDeliveryPerson(deliveryPersonId);
            while (deliveriesSet.next()) {
                Delivery delivery = Delivery.createDeliveryFromResultSet(deliveriesSet);
                deliveries.add(delivery);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        delivery_table.setItems(deliveries);

    }

    void refreshProfile() {
        try {
            ResultSet deliveryPersonSet = DeliveryPersonService.getDeliveryPersonById(deliveryPersonId);
            if (deliveryPersonSet.next()) {
                String deliveryPersonName = deliveryPersonSet.getString("delivery_person_name");
                int vehicleId = deliveryPersonSet.getInt("vehicle_id");
                ResultSet vehicleSet = VehicleService.getVehicleById(vehicleId);
                String deliveryPersonVehicle = "ERROR";
                if (vehicleSet.next()) {
                    deliveryPersonVehicle = vehicleSet.getString("vehicle_model");
                }
                profile_name.setText("Name : " + deliveryPersonName);
                profile_vehicle.setText("Vehicle : " + deliveryPersonVehicle);
            } else {
                System.out.println("ERROR retrieving profile");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR refreshing profile");
        }
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
}