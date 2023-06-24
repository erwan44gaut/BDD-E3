package front.statsScene;

import java.beans.EventHandler;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import src.customer.CustomerService;
import src.deliveryPerson.DeliveryPersonService;
import src.ingredient.IngredientService;
import src.pizza.PizzaService;
import src.stats.StatsService;

public class StatsSceneController {
    @FXML
    private Text stat_bestIngredientTextField;

    @FXML
    private Text stat_bestPizzaTextField;

    @FXML
    private Text stat_idBestCustomerTextField;

    @FXML
    private Text stat_nameBestCustomerTextField;

    @FXML
    private Text stat_nameBestDeliveryPersonTextField;

    @FXML
    private Text stat_nameWorstDeliveryPersonTextField;

    @FXML
    private Text stat_totalCommandeBestDeliveryPersonTextField;

    @FXML
    private Text stat_totalCommandeBestPizzaTextField;

    @FXML
    private Text stat_totalCommandeWorstDeliveryPersonTextField;

    @FXML
    private Text stat_vehiculeBestDeliveryPersonTextField;

    @FXML
    private Text stat_vehiculeWorstDeliveryPersonTextField;

    @FXML
    private Text stat_numberBestIngredientTextField;

    @FXML
    private Button stat_refreshButton;

    @FXML
    void onClick(ActionEvent event) {
        initialize();
        System.out.println("Refreshed");
    }

    public void initialize() {
        int customerId = -1;
        int worstDeliveryPersonId = -1;
        int bestDeliveryPersonId = -1;
        int mostOrderedPizza = -1;
        int mostPopularIngredient = -1;

        //#region Best delivery person
        try {
            ResultSet rs = StatsService.GetBestCustomer();
            if (rs.next()) {
                customerId = rs.getInt(1);
                stat_idBestCustomerTextField.setText(String.valueOf(customerId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (customerId != -1) {
            try {
                ResultSet rs = CustomerService.getCustomer(customerId);
                if (rs.next()) {
                    stat_nameBestCustomerTextField.setText(rs.getString("customer_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //#endregion

        //#region Worst delivery person
        try {
            ResultSet rs = StatsService.GetWorstDeliveryPerson();
            if (rs.next()) {
                worstDeliveryPersonId = rs.getInt(1);
                stat_vehiculeWorstDeliveryPersonTextField.setText(rs.getString(2));
                stat_totalCommandeWorstDeliveryPersonTextField.setText(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (worstDeliveryPersonId != -1) {
            try {
                ResultSet rs = DeliveryPersonService.getDeliveryPersonById(worstDeliveryPersonId);
                if (rs.next()) {
                    stat_nameWorstDeliveryPersonTextField.setText(rs.getString("delivery_person_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //#endregion

        //#region Best delivery person
        try {
            ResultSet rs = StatsService.GetBestDeliveryPerson();
            if (rs.next()) {
                bestDeliveryPersonId = rs.getInt(1);
                stat_vehiculeBestDeliveryPersonTextField.setText(rs.getString(2));
                stat_totalCommandeBestDeliveryPersonTextField.setText(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (worstDeliveryPersonId != -1) {
            try {
                ResultSet rs = DeliveryPersonService.getDeliveryPersonById(bestDeliveryPersonId);
                if (rs.next()) {
                    stat_nameBestDeliveryPersonTextField.setText(rs.getString("delivery_person_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //#endregion

        //#region Most ordered pizza
        try {
            ResultSet rs = StatsService.GetMostOrderedPizza();
            if (rs.next()) {
                mostOrderedPizza = rs.getInt(1);
                stat_totalCommandeBestPizzaTextField.setText(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (worstDeliveryPersonId != -1) {
            try {
                ResultSet rs = PizzaService.getPizzaById(mostOrderedPizza);
                if (rs.next()) {
                    stat_bestPizzaTextField.setText(rs.getString("pizza_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //#endregion

        //#region Most ordered pizza ingredient
        try {
            ResultSet rs = StatsService.GetMostPopularIngredient();
            if (rs.next()) {
                mostPopularIngredient = rs.getInt(1);
                stat_numberBestIngredientTextField.setText(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (worstDeliveryPersonId != -1) {
            try {
                ResultSet rs = IngredientService.getIngredientById(mostPopularIngredient);
                if (rs.next()) {
                    stat_bestIngredientTextField.setText(rs.getString("ingredient_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //#endregion
    }
}