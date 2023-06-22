package src;

import java.io.IOException;
import java.sql.ResultSet;
import src.util.ResultSetUtil;
import src.vehicle.VehicleService;
import src.delivery.DeliveryService;
import src.deliveryPerson.DeliveryPersonService;
import src.order.OrderService;
import src.customer.CustomerService;
import src.util.Config;
import src.util.DatabaseConnection;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PizzeriaApp extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Test.fxml"));
            loader.setController(new PizzasController());
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(IOException e) {}
    }

    public static void main(String[] args)
    {
        // Setup
        Config.load();
        DatabaseConnection.connect();

        // DeliveryService.unitTest();


        launch(args);
        DatabaseConnection.disconnect();
    }
}