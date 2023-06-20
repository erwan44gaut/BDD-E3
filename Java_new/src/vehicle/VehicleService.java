package src.vehicle;

import java.sql.ResultSet;
import src.util.DatabaseConnection;
import src.util.ResultSetUtil;

public class VehicleService {

    public static ResultSet getVehicles()
    {
        return DatabaseConnection.query("SELECT * FROM Vehicle;");
    }

    public static ResultSet getVehicleById(int vehicleId)
    {
        String sqlQuery = String.format("SELECT * FROM Vehicle WHERE vehicle_id = %d", vehicleId);
        return DatabaseConnection.query(sqlQuery);
    }

    public static int addVehicle(String vehicleType) 
    {
        String sqlQuery = String.format("INSERT INTO Vehicle (vehicle_type) VALUES (\"%s\")", vehicleType);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static int deleteVehicle(int vehicleId) 
    {
        String sqlQuery = String.format("DELETE FROM Vehicle WHERE vehicle_id = %d", vehicleId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static void unitTest() 
    {
        // Test getVehicleById(int vehicleId)
        System.out.println("#########################\nGET VEHICLE BY ID\n#########################\n");
        ResultSet vehicleResult = VehicleService.getVehicleById(1);
        ResultSetUtil.printResultSet(vehicleResult);

        // Test addVehicle(String vehicleType)
        System.out.println("#########################\nADD VEHICLE\n#########################\n");
        int addResult = VehicleService.addVehicle("car");
        System.out.println("Add Vehicle result: " + addResult);

        // Test getVehicles()
        System.out.println("#########################\nGET VEHICLES\n#########################\n");
        ResultSet vehiclesResult = VehicleService.getVehicles();
        ResultSetUtil.printResultSet(vehiclesResult);

        // Test deleteVehicle(int vehicleId)
        System.out.println("#########################\nDELETE VEHICLE\n#########################\n");
        int deleteResult = VehicleService.deleteVehicle(4);
        System.out.println("Delete Vehicle result: " + deleteResult);
    }
}
