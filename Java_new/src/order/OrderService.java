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
        // return DatabaseConnection.query(String.format("SELECT Customer.customer_name, Pizza.pizza_name, Pizza.pizza_base_price, Pizza_Order.order_status, Pizza_Order.order_datetime FROM Pizza_Order, Pizza, Customer WHERE Pizza_Order.order_id = %d AND Pizza_Order.Pizza_id = Pizza.pizza_id AND Pizza_Order.customer_id = Customer.customer_id;", id));
    }

    public static ResultSet getOrders()
    {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetOrdersDetails()}");
        return DatabaseConnection.queryStatement(statement);
    }
}