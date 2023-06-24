package front.deliveryPerson;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.DeserializationConfig;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import src.deliveryPerson.DeliveryPerson;
import src.deliveryPerson.DeliveryPersonService;

public class DeliveryPersonControler implements Initializable {
    @FXML
    private Button addButton;

    @FXML
    private TableColumn<DeliveryPerson, Button> delete;

    @FXML
    private TableColumn<DeliveryPerson, Integer> deliveryPersonId;

    @FXML
    private TableColumn<DeliveryPerson, String> deliveryPersonName;

    @FXML
    private TableColumn<DeliveryPerson, String> deliveryPersonVehicle;

    @FXML
    private TableColumn<DeliveryPerson, Button> editName;

    @FXML
    private Button refreshButton;

    @FXML
    private TableView<DeliveryPerson> table;

    ObservableList<DeliveryPerson> deliveryPersons = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        table.setFixedCellSize(60.0);
        deliveryPersonId.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, Integer>("delivery_person_id"));
        deliveryPersonName.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, String>("delivery_person_name"));
        editName.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, Button>("editName"));
        refreshButton.setOnAction(event -> refreshTable());

        addButton.setOnAction(event -> {
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

        editName.setCellFactory(column -> {
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
                
        delete.setCellFactory(column -> {
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

    void refreshTable() 
    {
        deliveryPersons.clear();
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
        table.setItems(deliveryPersons);
    }
}
