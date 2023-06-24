package src.vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static String[] getAllVehicleTypes()
    {
        String sqlQuery = "SELECT DISTINCT vehicle_type FROM Vehicle;";
        ResultSet resultSet = DatabaseConnection.query(sqlQuery);
        List<String> typesList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                String vehicleType = resultSet.getString("vehicle_type");
                typesList.add(vehicleType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return typesList.toArray(new String[0]);
    }

    public static String[] getUnassignedVehicles() 
    {
        String sqlQuery = "SELECT CONCAT(vehicle_type, ' - ', vehicle_model) AS vehicle_info " +
                        "FROM Vehicle " +
                        "LEFT JOIN Delivery_Person ON Vehicle.vehicle_id = Delivery_Person.vehicle_id " +
                        "WHERE Delivery_Person.delivery_person_id IS NULL";

        ResultSet resultSet = DatabaseConnection.query(sqlQuery);
        List<String> vehicleList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                String vehicleInfo = resultSet.getString("vehicle_info");
                vehicleList.add(vehicleInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicleList.toArray(new String[0]);
    }


    public static ResultSet getVehicleByName(String vehicleName)
    {
        String sqlQuery = String.format("SELECT * FROM Vehicle WHERE vehicle_type = '%s';", vehicleName);
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

    public static int updateVehicleField(int vehicleId, String field, String value) 
    {
        String sqlQuery = String.format("UPDATE Vehicle SET %s = '%s' WHERE vehicle_id = %d", field, value, vehicleId);
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
        int addResult = VehicleService.addVehicle("CAR");
        System.out.println("Add Vehicle result: " + addResult);

        // Test getVehicles()
        System.out.println("#########################\nGET VEHICLES\n#########################\n");
        ResultSet vehiclesResult = VehicleService.getVehicles();
        ResultSetUtil.printResultSet(vehiclesResult);

        // Test deleteVehicle(int vehicleId)
        System.out.println("#########################\nDELETE VEHICLE\n#########################\n");
        int deleteResult = VehicleService.deleteVehicle(11);
        System.out.println("Delete Vehicle result: " + deleteResult);
    }
}
