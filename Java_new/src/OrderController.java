package src;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import src.delivery.DeliveryService;
import src.deliveryPerson.DeliveryPersonService;
import src.order.OrderService;
import src.order.PizzaOrder;

public class OrderController implements Initializable {

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
    private TableColumn<PizzaOrder, Button> order_assignDelivery;
    @FXML
    private Button order_refreshButton;

    @FXML
    private TableView<PizzaOrder> order_table;

    ObservableList<PizzaOrder> orders = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
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
                        setGraphic(btn);
                        setAlignment(Pos.CENTER);
                        PizzaOrder order = getTableView().getItems().get(getIndex());
                        String orderStatus = order.getOrderStatus();
                        btn.setOnAction(event -> {
                            Dialog<String> dialog = new Dialog<>();
                            dialog.setTitle("Update Status");

                            ComboBox<String> statusComboBox = new ComboBox<>();
                            statusComboBox.getItems().addAll("ACCEPTED", "IN_PREPARATION", "IN_DELIVERY", "COMPLETED",
                                    "CANCELED");
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
                        setGraphic(btn);
                        setAlignment(Pos.CENTER);
                        PizzaOrder order = getTableView().getItems().get(getIndex());

                        btn.setOnAction(event -> {
                            Dialog<String> dialog = new Dialog<>();
                            dialog.setTitle("Select the delivery person's ID");

                            ComboBox<String> deliveryComboBox = new ComboBox<>();
                            ResultSet deliveryPersons = DeliveryPersonService.getDeliveryPersons();
                            try {
                                while (deliveryPersons.next()) {
                                    String deliveryPersonId = Integer.toString(deliveryPersons.getInt("delivery_person_id"));
                                    deliveryComboBox.getItems().add(deliveryPersonId);
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

                            Optional<String> result = dialog.showAndWait();

                            result.ifPresent(selectedDeliveryPerson -> {
                                System.out.println("Assigned order to delivery person: " + selectedDeliveryPerson);
                                DeliveryService.addDelivery(Integer.parseInt(selectedDeliveryPerson), order.getOrderId());
                                refreshTable();
                            });
                        });
                    }
                }
            };
        });
        refreshTable();

        refreshTable();
    }

    void refreshTable() 
    {
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
    }
}
