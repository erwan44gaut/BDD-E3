package src.pizzeria.delivery;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import src.util.DatabaseConnection;
import src.util.ResultSetUtil;

public class DeliveryService {

    public static ResultSet getDeliveries() {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetDeliveriesDetails()}");
        return DatabaseConnection.queryStatement(statement);
    }

    public static ResultSet getDeliveriesOfDeliveryPerson(int deliveryPersonId) {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL GetDeliveriesDetailsOfDeliveryPerson(?)}");
        DatabaseConnection.setStatement(statement, 1, deliveryPersonId);
        return DatabaseConnection.queryStatement(statement);
    }

    public static int addDelivery(int deliveryPersonId, int orderId) {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL AssignDelivery(?, ?)}");
        DatabaseConnection.setStatement(statement, 1, deliveryPersonId);
        DatabaseConnection.setStatement(statement, 2, orderId);        
        return DatabaseConnection.updateStatement(statement);
    }

    public static int updateDeliveryStatus(int deliveryId, String newStatus) {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL UpdateDeliveryStatus(?, ?)}");
        DatabaseConnection.setStatement(statement, 1, deliveryId);
        DatabaseConnection.setStatement(statement, 2, newStatus);        
        return DatabaseConnection.updateStatement(statement);
    }

    public static int deleteDelivery(int deliveryId) {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL DeleteDelivery(?)}");
        DatabaseConnection.setStatement(statement, 1, deliveryId);
        return DatabaseConnection.updateStatement(statement);
    }

    public static int cancelDelivery(int deliveryId) {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL CancelDelivery(?)}");
        DatabaseConnection.setStatement(statement, 1, deliveryId);
        return DatabaseConnection.updateStatement(statement);
    }

    public static int finishDelivery(int deliveryId) {
        CallableStatement statement = DatabaseConnection.prepareCall("{CALL FinishDelivery(?)}");
        DatabaseConnection.setStatement(statement, 1, deliveryId);
        return DatabaseConnection.updateStatement(statement);
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