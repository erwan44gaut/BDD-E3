package src;

import java.io.IOException;
import java.net.URL;

import src.front.start.StartController;
import src.util.Config;
import src.util.DatabaseConnection;

import javafx.application.Application;
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
            URL sceneUrl = getClass().getClassLoader().getResource(Config.getProperty("path.startScene"));
            FXMLLoader loader = new FXMLLoader(sceneUrl);
            loader.setController(new StartController());
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Pizzeria");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        // Setup
        Config.load();
        DatabaseConnection.connect();

        // VehicleService.unitTest();

        launch(args);
        DatabaseConnection.disconnect();
    }
}