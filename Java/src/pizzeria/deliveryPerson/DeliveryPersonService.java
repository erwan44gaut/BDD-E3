package src.pizzeria.deliveryPerson;

import java.sql.ResultSet;
import src.util.DatabaseConnection;
import src.util.ResultSetUtil;

public class DeliveryPersonService {

    public static ResultSet getDeliveryPersons()
    {
        return DatabaseConnection.query("SELECT * FROM Delivery_Person;");
    }

    public static ResultSet getDeliveryPersonById(int deliveryPersonId)
    {
        String sqlQuery = String.format("SELECT * FROM Delivery_Person WHERE delivery_person_id = %d", deliveryPersonId);
        return DatabaseConnection.query(sqlQuery);
    }

    public static int addDeliveryPerson(String deliveryPersonName)
    {
        String sqlQuery = String.format("INSERT INTO Delivery_Person (delivery_person_name) VALUES (\"%s\")", deliveryPersonName);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static int deleteDeliveryPerson(int deliveryPersonId)
    {
        if (deliveryPersonId == 1) return -1; // Prevent deleting the admin
        String sqlQuery = String.format("DELETE FROM Delivery_Person WHERE delivery_person_id=%d", deliveryPersonId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static int updateDeliveryPersonVehicle(int deliveryPersonId, int vehicle_id) 
    {
        String sqlQuery = String.format("UPDATE Delivery_Person SET vehicle_id = '%d' WHERE delivery_person_id = %d", vehicle_id, deliveryPersonId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static int updateDeliveryPersonName(int deliveryPersonId, String name) 
    {
        if (deliveryPersonId == 1) return -1; // Prevent editing the admin
        String sqlQuery = String.format("UPDATE Delivery_Person SET delivery_person_name = '%s' WHERE delivery_person_id = %d", name, deliveryPersonId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static void unitTest() 
    {
        // Test getDeliveryPersonById(int deliveryPersonId)
        System.out.println("#########################\nGET DELIVERY PERSON\n#########################\n");
        ResultSet deliveryPersonResult = DeliveryPersonService.getDeliveryPersonById(1);
        ResultSetUtil.printResultSet(deliveryPersonResult);

        // Test addDeliveryPerson(String deliveryPersonName)
        int addResult = DeliveryPersonService.addDeliveryPerson("John Doe");
        System.out.println("#########################\nADD DELIVERY PERSON\n#########################\n" + addResult);

        // Test getDeliveryPersons()
        System.out.println("#########################\nGET DELIVERY PERSONS\n#########################\n");
        ResultSet deliveryPersonsResult = DeliveryPersonService.getDeliveryPersons();
        ResultSetUtil.printResultSet(deliveryPersonsResult);

        // Test deleteDeliveryPerson(int deliveryPersonId)
        int deleteResult = DeliveryPersonService.deleteDeliveryPerson(11);
        System.out.println("#########################\nDELETE DELIVERY PERSON\n#########################\n" + deleteResult);
    }
}
