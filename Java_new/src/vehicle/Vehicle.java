package src.vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Button;

public class Vehicle {
    private Integer vehicleId;
    private String vehicleType;
    private String vehicleModel;

    private Button editName;
    private Button delete;

    public Vehicle(Integer vehicleId, String vehicleType, String vehicleModel) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.vehicleModel = vehicleModel;

        this.editName = new Button("Edit name");
        this.delete = new Button("Delete");
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

    public static Vehicle createVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        int vehicleId = resultSet.getInt("vehicle_id");
        String vehicleType = resultSet.getString("vehicle_type");
        String vehicleModel = resultSet.getString("vehicle_model");

        return new Vehicle(vehicleId, vehicleType, vehicleModel);
    }
}
