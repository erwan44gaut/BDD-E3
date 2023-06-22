package src.stats;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import src.util.DatabaseConnection;

public class StatsService {
    public static ResultSet getBestIngredients()
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetBestIngredients()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static ResultSet getBestPizzas()
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetBestPizzas()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static ResultSet GetBestCustomer()
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetBestCustomer()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static ResultSet getBestDeliveryPersons()
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetBestDeliveryPersons()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static ResultSet getWorstDeliveryPersons()
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetWorstDeliveryPersons()}");
        return DatabaseConnection.queryStatement(statement);
    }
}
