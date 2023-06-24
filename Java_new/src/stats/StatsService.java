package src.stats;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import src.util.DatabaseConnection;

public class StatsService {
    public static ResultSet GetMostPopularIngredient()
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetMostPopularIngredient()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static ResultSet GetMostOrderedPizza()
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetMostOrderedPizza()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static ResultSet GetBestCustomer()
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetBestCustomer()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static ResultSet GetBestDeliveryPerson()
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetBestDeliveryPerson()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static ResultSet GetWorstDeliveryPerson()
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetWorstDeliveryPerson()}");
        return DatabaseConnection.queryStatement(statement);
    }
}
