package src;
import src.customer.CustomerService;
import src.util.Config;
import src.util.DatabaseConnection;

public class PizzeriaApp
{
    public static void main(String[] args)
    {
        // Setup
        Config.load();
        DatabaseConnection.connect();

        CustomerService.unitTest();

        DatabaseConnection.disconnect();
    }
}