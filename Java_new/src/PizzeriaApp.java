package src;

import java.sql.ResultSet;
import src.util.ResultSetUtil;
import src.vehicle.VehicleService;
import src.delivery.DeliveryService;
import src.deliveryPerson.DeliveryPersonService;
import src.order.OrderService;
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

        OrderService.unitTest();

        DatabaseConnection.disconnect();
    }
}