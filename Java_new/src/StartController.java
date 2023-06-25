package src;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import front.OrderPizzaScene.OrderPizzaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import src.customer.Customer;
import src.customer.CustomerService;
import src.deliveryPerson.DeliveryPerson;
import src.deliveryPerson.DeliveryPersonService;
import src.ingredient.Ingredient;
import src.pizza.PizzaService;

public class StartController implements Initializable{

    @FXML
    private TabPane pane;

    @FXML
    private Button admin_start;

    @FXML
    private ComboBox<Customer> customer_select;

    @FXML
    private Button customer_start;

    @FXML
    private ComboBox<DeliveryPerson> deliveryPerson_select;

    @FXML
    private Button deliveryPerson_start;

    @FXML
    void admin_startAction(ActionEvent event) {
                    Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
            loader.setController(new AdminController());
            Parent root;
            try {
                root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                System.out.println("Admin Interface");
                stage.show();
                closeWindow();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ERROR : CAN NOT GO TO ADMIN INTERFACE");
            }
    }

    @FXML
    void customer_startAction(ActionEvent event) {
        Customer customer = customer_select.getValue();
        if(customer!=null){
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientScene.fxml"));
            loader.setController(new ClientController(customer.getCustomerId()));
            Parent root;
            try {
                root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                System.out.println("Customer Interface of '"+customer.getCustomerName()+"'");
                stage.show();
                closeWindow();
            } catch (IOException e) {
                System.out.println("ERROR : CAN NOT GO TO CUSTOMER INTERFACE");
            }
        }
        else{
            System.out.println("NO CUSTOMER SELECTED");
        }
    }

    @FXML
    void deliveryPerson_startAction(ActionEvent event) {
        closeWindow();
    }

    ObservableList<Customer> customerList = FXCollections.observableArrayList();
    ObservableList<DeliveryPerson> deliveryPersonList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customer_select.setConverter(customerConverter);
        deliveryPerson_select.setConverter(deliveryPersonConverter);
        refreshCustomer();
        refreshDeliveryPerson();
    }

    StringConverter<Customer> customerConverter = new StringConverter<Customer>() {
        @Override
        public String toString(Customer customer) {
            return customer.getCustomerName();
        }

        @Override
        public Customer fromString(String string) {
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

    void refreshCustomer(){
        customer_select.getItems().clear();
        try {
            ResultSet customerSet =CustomerService.getCustomers();
            while (customerSet.next()) {
                Customer customer = Customer.createCustomerFromResultSet(customerSet);
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customer_select.setItems(customerList);
    }

    void refreshDeliveryPerson(){
        deliveryPerson_select.getItems().clear();
        try {
            ResultSet deliveryPersonSet =DeliveryPersonService.getDeliveryPersons();
            while (deliveryPersonSet.next()) {
                DeliveryPerson deliveryPerson = DeliveryPerson.createDeliveryPersonFromResultSet(deliveryPersonSet);
                deliveryPersonList.add(deliveryPerson);
            }
        } catch (SQLException e) {
            System.out.println("ERROR REFRESH DELIVERY PERSON");
        }
        deliveryPerson_select.setItems(deliveryPersonList);
    }

    void closeWindow(){
        Scene scene = pane.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

}
