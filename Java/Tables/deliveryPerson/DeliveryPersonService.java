package Tables.deliveryPerson;

import java.sql.ResultSet;
import src.DatabaseConnection;

public class DeliveryPersonService {

    public static ResultSet getDeliveryPersons()
    {
        return DatabaseConnection.query("SELECT * FROM Delivery_Person;");
    }
}
