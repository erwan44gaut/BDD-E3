package Tables.customer;
import java.sql.ResultSet;
import src.DatabaseConnection;

public class CustomerService
{
    public static ResultSet getCustomers()
    {
        return DatabaseConnection.query("SELECT * FROM Customer;");
    }

    public static boolean addCustomer(String customerName)
    {
        String sqlQuery = String.format("INSERT INTO Customer (name) VALUES (\"%s\")", customerName);
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