package src.delivery;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import src.util.DatabaseConnection;
import src.util.ResultSetUtil;

public class DeliveryService {

    public static ResultSet getDeliveries() 
    {
        return DatabaseConnection.query("SELECT * FROM Delivery;");
    }

    public static ResultSet getDeliveryById(int deliveryId) 
    {
        String sqlQuery = String.format("SELECT * FROM Delivery WHERE delivery_id = %d", deliveryId);
        return DatabaseConnection.query(sqlQuery);
    }

    public static int addDelivery(String deliveryStatus, String deliveryDatetime, int deliveryPersonId, int vehicleId, int orderId) 
    {
        String sqlQuery = String.format("INSERT INTO Delivery (delivery_status, delivery_datetime, delivery_person_id, vehicle_id, order_id) VALUES (\"%s\", \"%s\", %d, %d, %d)", deliveryStatus, deliveryDatetime, deliveryPersonId, vehicleId, orderId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static int updateDeliveryStatus(int deliveryId, String newStatus) 
    {
        String sqlQuery = String.format("UPDATE Delivery SET delivery_status = \"%s\" WHERE delivery_id = %d", newStatus, deliveryId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static int deleteDelivery(int deliveryId) 
    {
        String sqlQuery = String.format("DELETE FROM Delivery WHERE delivery_id = %d", deliveryId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static void unitTest() 
    {
        // Test getDeliveryById(int deliveryId)
        System.out.println("#########################\nGET DELIVERY BY ID\n#########################\n");
        ResultSet deliveryResult = DeliveryService.getDeliveryById(1);
        ResultSetUtil.printResultSet(deliveryResult);

        // Test addDelivery(String deliveryStatus, String deliveryDatetime, int deliveryPersonId, int vehicleId, int orderId)
        String deliveryStatus = "LATE";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Créer un objet SimpleDateFormat avec le format de date souhaité
        Date currentDate = new Date(); // Obtenir la date actuelle
        String formattedDate = dateFormat.format(currentDate); // Formater la date en utilisant le format spécifié
        String deliveryDatetime = formattedDate;
        int deliveryPersonId = 1;
        int vehicleId = 2;
        int orderId = 3;
        System.out.println("#########################\nADD DELIVERY\n#########################\n");
        int addResult = DeliveryService.addDelivery(deliveryStatus, deliveryDatetime, deliveryPersonId, vehicleId, orderId);
        System.out.println("Add Delivery result: " + addResult);

        // Test updateDeliveryStatus(int deliveryId, String newStatus)
        int updateDeliveryId = 1;
        String newStatus = "LATE";
        System.out.println("#########################\nUPDATE DELIVERY STATUS\n#########################\n");
        int updateResult = DeliveryService.updateDeliveryStatus(updateDeliveryId, newStatus);
        System.out.println("Update Delivery Status result: " + updateResult);

        // Test getDeliveries()
        System.out.println("#########################\nGET DELIVERIES\n#########################\n");
        ResultSet deliveriesResult = DeliveryService.getDeliveries();
        ResultSetUtil.printResultSet(deliveriesResult);

        // Test deleteDelivery(int deliveryId)
        int deleteDeliveryId = 4; // Fournissez un ID de livraison valide pour le test
        System.out.println("#########################\nDELETE DELIVERY\n#########################\n");
        int deleteResult = DeliveryService.deleteDelivery(deleteDeliveryId);
        System.out.println("Delete Delivery result: " + deleteResult);
    }
}
