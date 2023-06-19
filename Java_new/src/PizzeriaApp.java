package src;

import java.sql.ResultSet;
import src.util.ResultSetUtil;
import src.order.OrderService;
import src.util.Config;
import src.util.DatabaseConnection;

public class PizzeriaApp
{
    public static void main(String[] args)
    {
        // Setup
        Config.load();
        DatabaseConnection.connect();

        ResultSet orders = OrderService.getOrders();
        ResultSetUtil.printResultSet(orders);
        
        DatabaseConnection.disconnect();
    }
}