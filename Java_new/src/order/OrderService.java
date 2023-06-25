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

    public static ResultSet getOrdersWaitingForDelivery()
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetWaitingForDeliveryOrdersDetails()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static ResultSet getOrdersForCustomer(int id)
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetOrdersDetailsForCustomer(?)}");
        DatabaseConnection.setStatement(statement, 1, id);
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
        System.out.println("Cancelling order n°" + order_id);
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL CancelOrder(?)}");
        DatabaseConnection.setStatement(statement, 1, order_id);
        return DatabaseConnection.updateStatement(statement);
    }

    public static int updateStatus(int order_id, String new_status)
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL UpdateOrderStatus(?, ?)}");
        DatabaseConnection.setStatement(statement, 1, order_id);
        DatabaseConnection.setStatement(statement, 2, new_status);        
        return DatabaseConnection.updateStatement(statement);
    }

    public static ResultSet getOrderCountForCustomer(int customer_id)
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetOrderCountByCustomerId(?)}");
        DatabaseConnection.setStatement(statement, 1, customer_id);       
        return DatabaseConnection.queryStatement(statement);
    }

    public static void unitTest() {
        // // Test getOrder(int id)
        System.out.println("#########################\nGET ORDER\n#########################\n");
        ResultSet orderResult = OrderService.getOrder(18);
        ResultSetUtil.printResultSet(orderResult);

        // Test placeOrder(int customer_id, int pizza_id, int pizza_size)
        int placeOrderResult = OrderService.placeOrder(3, 5, "LARGE");
        System.out.println("#########################\nPLACE ORDER\n#########################\n" + placeOrderResult);

        // Test getOrders()
        System.out.println("#########################\nGET ORDERS\n#########################\n");
        ResultSet ordersResult = OrderService.getOrders();
        ResultSetUtil.printResultSet(ordersResult);

        // Test cancelOrder(int order_id)
        int cancelOrderResult = OrderService.cancelOrder(9);
        System.out.println("#########################\nCANCEL ORDER\n#########################\n" + cancelOrderResult);

        // Test updateStatus(int order_id, String new_status)
        int updateStatusResult = OrderService.updateStatus(15, "ACCEPTED");
        System.out.println("#########################\nUPDATE STATUS\n#########################\n" + updateStatusResult);
    }
}