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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import src.customer.CustomerService;
import src.customer.Customer;

public class CustomerController implements Initializable {

    @FXML
    private TableColumn<Customer, Integer> customerId;
    @FXML
    private TableColumn<Customer, String> customerName;
    @FXML
    private TableColumn<Customer, Float> customerBalance;
    @FXML
    private TableColumn<Customer, Button> rechargeBalance;
    @FXML
    private TableColumn<Customer, Button> editName;
    @FXML
    private TableColumn<Customer, Button> delete;

    @FXML
    private Button addButton;

    @FXML
    private Button refreshButton;
    @FXML
    private TableView<Customer> table;

    ObservableList<Customer> customers = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        table.setFixedCellSize(60.0);
        customerId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerBalance.setCellValueFactory(new PropertyValueFactory<Customer, Float>("customerBalance"));
        rechargeBalance.setCellValueFactory(new PropertyValueFactory<Customer, Button>("rechargeBalance"));
        editName.setCellValueFactory(new PropertyValueFactory<Customer, Button>("editName"));
        refreshButton.setOnAction(event -> refreshTable());

        addButton.setOnAction(event -> {
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

        editName.setCellFactory(column -> {
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

        rechargeBalance.setCellFactory(column -> {
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

                
        delete.setCellFactory(column -> {
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

        refreshTable();
    }

    void refreshTable() 
    {
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
        table.setItems(customers);
    }
}
