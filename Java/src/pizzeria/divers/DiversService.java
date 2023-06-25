package src.pizzeria.divers;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import src.util.DatabaseConnection;

public class DiversService {
    public static ResultSet getMonthlyRevenue(){
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL CalculateMonthlyRevenue()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static ResultSet getTotalRevenue(){
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL CalculateTotalRevenue()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static ResultSet getAverageOrderPrice(){
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL CalculateAverageOrderPrice()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static ResultSet getSalesByPizzaSize(){
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GenerateSalesDistributionByPizzaSize()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static ResultSet getAverageDeliveryTime(){
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL CalculateAverageDeliveryTime()}");
        return DatabaseConnection.queryStatement(statement);
    }
}