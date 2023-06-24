package src.delivery;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import src.util.DatabaseConnection;
import src.util.ResultSetUtil;

public class DeliveryService {

    public static ResultSet getDeliveries() {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetDeliveriesDetails()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static int addDelivery(int deliveryPersonId, int orderId) {
        String sqlQuery = String.format("INSERT INTO Delivery (delivery_person_id, order_id) VALUES (%d, %d)",
                deliveryPersonId, orderId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static int updateDeliveryStatus(int deliveryId, String newStatus) {
        String sqlQuery = String.format("UPDATE Delivery SET delivery_status = \"%s\" WHERE delivery_id = %d",
                newStatus, deliveryId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static int deleteDelivery(int deliveryId) {
        String sqlQuery = String.format("DELETE FROM Delivery WHERE delivery_id = %d", deliveryId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static void unitTest() 
    {
        // Test addDelivery(String deliveryStatus, String deliveryDatetime, int deliveryPersonId, int vehicleId, int orderId)
        int deliveryPersonId = 1;
        int orderId = 3;
        System.out.println("#########################\nADD DELIVERY\n#########################\n");
        int addResult = DeliveryService.addDelivery(deliveryPersonId, orderId);
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