package src.customer;
import src.util.DatabaseConnection;
import java.sql.ResultSet;

public class CustomerService
{
    public static ResultSet getCustomer(String id)
    {
        return DatabaseConnection.query(String.format("SELECT * FROM Customer WHERE Customer.id = %s", id));
    }

    public static ResultSet getCustomers()
    {
        return DatabaseConnection.query("SELECT * FROM Customer;");
    }

    public static boolean addCustomer(String customerName)
    {
        String sqlQuery = String.format("INSERT INTO Customer (customer_name) VALUES (\"%s\")", customerName);
        boolean queryResult = DatabaseConnection.execute(sqlQuery);
        return queryResult;
    }

    public static boolean deleteCustomer(int customerId)
    {
        String sqlQuery = String.format("DELETE FROM Customer WHERE id=%d", customerId);
        boolean queryResult = DatabaseConnection.execute(sqlQuery);
        return queryResult;
    }

    public static boolean updateCustomerField(int customerId, String field, String value) 
    {
        String sqlQuery = String.format("UPDATE Customer SET %s = '%s' WHERE id = %d", field, value, customerId);
        boolean queryResult = DatabaseConnection.execute(sqlQuery);
        return queryResult;
    }
}