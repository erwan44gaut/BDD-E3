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
import javafx.scene.control.cell.PropertyValueFactory;
import src.order.OrderService;
import src.order.PizzaOrder;

public class OrderController implements Initializable {

    @FXML
    private TableColumn<PizzaOrder, Integer> orderId;
    @FXML
    private TableColumn<PizzaOrder, String> customerName;
    @FXML
    private TableColumn<PizzaOrder, String> pizzaName;
    @FXML
    private TableColumn<PizzaOrder, String> pizzaSize;
    @FXML
    private TableColumn<PizzaOrder, Float> totalPrice;
    @FXML
    private TableColumn<PizzaOrder, String> status;
    @FXML
    private TableColumn<PizzaOrder, Date> orderDate;
    @FXML
    private TableColumn<PizzaOrder, Button> cancel;
    @FXML
    private TableColumn<PizzaOrder, Button> updateStatus;
    @FXML
    private Button refreshButton;

    @FXML
    private TableView<PizzaOrder> table;

    ObservableList<PizzaOrder> orders = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        table.setFixedCellSize(60.0);
        orderId.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Integer>("orderId"));
        customerName.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("customerName"));
        pizzaName.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("pizzaName"));
        pizzaSize.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("pizzaSize"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Float>("totalPrice"));
        status.setCellValueFactory(new PropertyValueFactory<PizzaOrder, String>("status"));
        orderDate.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Date>("orderDate"));
        cancel.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Button>("cancel"));
        updateStatus.setCellValueFactory(new PropertyValueFactory<PizzaOrder, Button>("updateStatus"));
        refreshButton.setOnAction(event -> refreshTable());
        
        updateStatus.setCellFactory(column -> {
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
                        String orderStatus = order.getStatus();
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


        cancel.setCellFactory(column -> 
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
                        if (!order.getStatus().equals("ACCEPTED"))
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
        table.setItems(orders);
    }
}
