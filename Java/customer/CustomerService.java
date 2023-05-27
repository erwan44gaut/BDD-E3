package customer;
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
}