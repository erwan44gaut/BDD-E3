package src;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import src.customer.CustomerService;
import src.delivery.Delivery;
import src.delivery.DeliveryService;
import src.order.OrderService;
import src.order.PizzaOrder;
import src.pizza.Pizza;
import src.pizza.PizzaService;

public class DeliveryController implements Initializable {
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
    private TableColumn<Delivery, Date> delivery_deliveryDate;
    @FXML
    private TableColumn<Delivery, Button> delivery_delete;
    @FXML
    private TableColumn<Delivery, Button> delivery_updateStatus;

    @FXML
    private Button delivery_refreshButton;

    @FXML
    private TableView<Delivery> delivery_table;

    ObservableList<Delivery> orders = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        delivery_table.setFixedCellSize(60.0);
        delivery_deliveryId.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("deliveryId"));
        delivery_orderId.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("orderId"));
        delivery_deliveryPersonId.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("deliveryPersonId"));
        delivery_deliveryPersonName.setCellValueFactory(new PropertyValueFactory<Delivery, String>("deliveryPersonName"));
        delivery_vehicleId.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("vehicleId"));
        delivery_vehicleType.setCellValueFactory(new PropertyValueFactory<Delivery, String>("vehicleType"));
        delivery_deliveryStatus.setCellValueFactory(new PropertyValueFactory<Delivery, String>("deliveryStatus"));
        delivery_deliveryDate.setCellValueFactory(new PropertyValueFactory<Delivery, Date>("deliveryDate"));
        delivery_delete.setCellValueFactory(new PropertyValueFactory<Delivery, Button>("delete"));
        delivery_updateStatus.setCellValueFactory(new PropertyValueFactory<Delivery, Button>("updateStatus"));
        delivery_refreshButton.setOnAction(event -> refreshTable());

        delivery_delete.setCellFactory(column -> {
            return new TableCell<Delivery, Button>() {
                private final Button deleteButton = new Button("DELETE");

                {
                    deleteButton.setOnAction(event -> {
                        Delivery delivery = getTableView().getItems().get(getIndex());
                        DeliveryService.deleteDelivery(delivery.getDeliveryId());
                        System.out.println("Delete delivery '"+delivery.getDeliveryId()+"'");
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

        delivery_updateStatus.setCellFactory(column -> {
            return new TableCell<Delivery, Button>() {
                final Button btn = new Button("Update Status");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                        setAlignment(Pos.CENTER);
                        Delivery delivery = getTableView().getItems().get(getIndex());
                        String orderStatus = delivery.getDeliveryStatus();
                        btn.setOnAction(event -> {
                            Dialog<String> dialog = new Dialog<>();
                            dialog.setTitle("Update Status");

                            ComboBox<String> statusComboBox = new ComboBox<>();
                            statusComboBox.getItems().addAll("ACCEPTED", "IN_PROGRESS", "COMPLETE", "LATE"); // TODO
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
                                DeliveryService.updateDeliveryStatus(delivery.getDeliveryId(), selectedStatus);
                                refreshTable();
                            });
                        });
                    }
                }
            };
        });
        refreshTable();
    }

    void refreshTable() 
    {
        orders.clear();
        try 
        {
            ResultSet ordersSet = DeliveryService.getDeliveries();
            while (ordersSet.next()) {
                Delivery order = Delivery.createDeliveryFromResultSet(ordersSet);
                orders.add(order);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        delivery_table.setItems(orders);
    }
}
