package src.front.statsScene;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import src.customer.CustomerService;
import src.stats.StatsService;

public class StatsSceneController {
    @FXML
    private Text NameBestCustomerTextField;

    @FXML
    private Text bestDeliveryPersonTextField;

    @FXML
    private Text bestIngredientTextField;

    @FXML
    private Text bestPizzaTextField;

    @FXML
    private Text idBestCustomerTextField;

    @FXML
    private Text totalCommandeBestClientTextField;

    @FXML
    private Text totalCommandeBestDeliveryPersonTextField;

    @FXML
    private Text totalCommandeBestPizzaTextField;

    @FXML
    private Text totalCommandeWorstDeliveryPersonTextField;

    @FXML
    private Text worstDeliveryPersonTextField;

    public void initialize() {
        int customerId = 1;
        
        String bestDeliveryPerson = "Jane Smith";
        String bestIngredient = "Mozzarella";
        String bestPizza = "Margherita";
        int totalCommandeBestClient = 10;
        int totalCommandeBestDeliveryPerson = 20;
        int totalCommandeBestPizza = 15;
        int totalCommandeWorstDeliveryPerson = 5;
        String worstDeliveryPerson = "Sam Johnson";

        try {
            ResultSet rs = StatsService.GetBestCustomer();
            if (rs.next()) {
                customerId = rs.getInt(1);
                idBestCustomerTextField.setText(String.valueOf(customerId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (customerId != -1) { // Only execute the second query if the first one was successful
            try {
                ResultSet rs = CustomerService.getCustomer(customerId);
                if (rs.next()) {
                    NameBestCustomerTextField.setText(rs.getString("customer_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        bestDeliveryPersonTextField.setText(bestDeliveryPerson);
        bestIngredientTextField.setText(bestIngredient);
        bestPizzaTextField.setText(bestPizza);
        totalCommandeBestClientTextField.setText(String.valueOf(totalCommandeBestClient));
        totalCommandeBestDeliveryPersonTextField.setText(String.valueOf(totalCommandeBestDeliveryPerson));
        totalCommandeBestPizzaTextField.setText(String.valueOf(totalCommandeBestPizza));
        totalCommandeWorstDeliveryPersonTextField.setText(String.valueOf(totalCommandeWorstDeliveryPerson));
        worstDeliveryPersonTextField.setText(worstDeliveryPerson);
    }
}