package src.order;
import src.util.DatabaseConnection;
import src.util.ResultSetUtil;

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

    public static int placeOrder(int customer_id, int pizza_id, String pizza_size)
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL PlaceOrder(?, ?, ?)}");
        DatabaseConnection.setStatement(statement, 1, customer_id);
        DatabaseConnection.setStatement(statement, 2, pizza_id);
        DatabaseConnection.setStatement(statement, 3, pizza_size);
        return DatabaseConnection.updateStatement(statement);
    }

    public static int cancelOrder(int order_id)
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL CancelOrder(?)}");
        DatabaseConnection.setStatement(statement, 1, order_id);
        return DatabaseConnection.updateStatement(statement);
    }

    public static int updateStatus(int order_id, String new_status)
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL UpdateOrderStatus(?, ?)}");
        DatabaseConnection.setStatement(statement, 1, new_status);        
        DatabaseConnection.setStatement(statement, 2, order_id);
        return DatabaseConnection.updateStatement(statement);
    }

    public static void unitTest() {
        // Test getOrder(int id)
        System.out.println("#########################\nGET ORDER\n#########################\n");
        ResultSet orderResult = OrderService.getOrder(1);
        ResultSetUtil.printResultSet(orderResult);

        // Test getOrders()
        System.out.println("#########################\nGET ORDERS\n#########################\n");
        ResultSet ordersResult = OrderService.getOrders();
        ResultSetUtil.printResultSet(ordersResult);

        // Test placeOrder(int customer_id, int pizza_id, int pizza_size)
        int placeOrderResult = OrderService.placeOrder(1, 1, "LARGE");
        System.out.println("#########################\nPLACE ORDER\n#########################\n" + placeOrderResult);

        // Test cancelOrder(int order_id)
        int cancelOrderResult = OrderService.cancelOrder(1);
        System.out.println("#########################\nCANCEL ORDER\n#########################\n" + cancelOrderResult);

        // Test updateStatus(int order_id, String new_status)
        int updateStatusResult = OrderService.updateStatus(1, "Completed");
        System.out.println("#########################\nUPDATE STATUS\n#########################\n" + updateStatusResult);
    }
}