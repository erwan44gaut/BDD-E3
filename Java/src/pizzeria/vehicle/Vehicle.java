package src.pizzeria.vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private Integer vehicleId;
    private String vehicleType;
    private String vehicleModel;
    private Button editName;

    public Vehicle(Integer vehicleId, String vehicleType, String vehicleModel) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.vehicleModel = vehicleModel;

        this.editName = new Button("Edit name");
        new Button("Delete");
    }

    public Integer getVehicleId()
    {
        return vehicleId;
    }

    public String getVehicleType()
    {
        return vehicleType;
    }

    public String getVehicleModel()
    {
        return vehicleModel;
    }

    public Button getEditName()
    {
        return editName;
    }

    public static String getVehicleDescription(Integer vehicleId)
    {
        try {
            ResultSet rs = VehicleService.getVehicleById(vehicleId);
            if (rs.next()) {
                String description = rs.getString("vehicle_type") + " - " + rs.getString("vehicle_model");
                return description;
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return "No vehicle";
    }

    public static Vehicle createVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        int vehicleId = resultSet.getInt("vehicle_id");
        String vehicleType = resultSet.getString("vehicle_type");
        String vehicleModel = resultSet.getString("vehicle_model");

        return new Vehicle(vehicleId, vehicleType, vehicleModel);
    }

    public static List<Vehicle> vehiclesFromResultSet(ResultSet resultSet) {
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int vehicleId = resultSet.getInt("vehicle_id");
                String vehicleType = resultSet.getString("vehicle_type");
                String vehicleModel = resultSet.getString("vehicle_model");

                Vehicle vehicle = new Vehicle(vehicleId, vehicleType, vehicleModel);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
}
