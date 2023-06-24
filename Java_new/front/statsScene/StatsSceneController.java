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
    private Text bestIngredientTextField;

    @FXML
    private Text bestPizzaTextField;

    @FXML
    private Text idBestCustomerTextField;

    @FXML
    private Text nameBestCustomerTextField;

    @FXML
    private Text nameBestDeliveryPersonTextField;

    @FXML
    private Text nameWorstDeliveryPersonTextField;

    @FXML
    private Text totalCommandeBestDeliveryPersonTextField;

    @FXML
    private Text totalCommandeBestPizzaTextField;

    @FXML
    private Text totalCommandeWorstDeliveryPersonTextField;

    @FXML
    private Text vehiculeBestDeliveryPersonTextField;

    @FXML
    private Text vehiculeWorstDeliveryPersonTextField;

    @FXML
    private Text numberBestIngredientTextField;

    @FXML
    private Button refreshButton;

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
                idBestCustomerTextField.setText(String.valueOf(customerId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (customerId != -1) {
            try {
                ResultSet rs = CustomerService.getCustomer(customerId);
                if (rs.next()) {
                    nameBestCustomerTextField.setText(rs.getString("customer_name"));
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
                vehiculeWorstDeliveryPersonTextField.setText(rs.getString(2));
                totalCommandeWorstDeliveryPersonTextField.setText(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (worstDeliveryPersonId != -1) {
            try {
                ResultSet rs = DeliveryPersonService.getDeliveryPersonById(worstDeliveryPersonId);
                if (rs.next()) {
                    nameWorstDeliveryPersonTextField.setText(rs.getString("delivery_person_name"));
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
                vehiculeBestDeliveryPersonTextField.setText(rs.getString(2));
                totalCommandeBestDeliveryPersonTextField.setText(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (worstDeliveryPersonId != -1) {
            try {
                ResultSet rs = DeliveryPersonService.getDeliveryPersonById(bestDeliveryPersonId);
                if (rs.next()) {
                    nameBestDeliveryPersonTextField.setText(rs.getString("delivery_person_name"));
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
                totalCommandeBestPizzaTextField.setText(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (worstDeliveryPersonId != -1) {
            try {
                ResultSet rs = PizzaService.getPizzaById(mostOrderedPizza);
                if (rs.next()) {
                    bestPizzaTextField.setText(rs.getString("pizza_name"));
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
                numberBestIngredientTextField.setText(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (worstDeliveryPersonId != -1) {
            try {
                ResultSet rs = IngredientService.getIngredientById(mostPopularIngredient);
                if (rs.next()) {
                    bestIngredientTextField.setText(rs.getString("ingredient_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //#endregion
    }
}