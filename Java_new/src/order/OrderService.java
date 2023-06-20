package src.order;
import src.util.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.ResultSet;

public class OrderService
{
    public static ResultSet getOrder(int id)
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetOrderDetails(?)}");
        DatabaseConnection.setStatement(statement, 1, id);
        return DatabaseConnection.queryStatement(statement);
    }

    public static ResultSet getOrders()
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetOrdersDetails()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static boolean placeOrder(int customer_id, int pizza_id, int pizza_size)
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL PlaceOrder(?, ?, ?)}");
        DatabaseConnection.setStatement(statement, 1, customer_id);
        DatabaseConnection.setStatement(statement, 2, pizza_id);
        DatabaseConnection.setStatement(statement, 3, pizza_size);
        return DatabaseConnection.executeStatement(statement);
    }

    public static int cancelOrder(int order_id)
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL UpdateOrder(?)}");
        DatabaseConnection.setStatement(statement, 1, order_id);
        return DatabaseConnection.updateStatement(statement);
    }

    public static int updateStatus(int order_id, String new_status)
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL UpdateOrderStatus(?)}");
        DatabaseConnection.setStatement(statement, 1, new_status);
        return DatabaseConnection.updateStatement(statement);
    }
}