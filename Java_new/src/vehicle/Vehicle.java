package src.vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Button;

public class Vehicle {
    private Integer vehicleId;
    private String vehicleType;

    private Button editName;
    private Button delete;

    public Vehicle(Integer vehicleId, String vehicleType) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;

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

    public Button getEditName()
    {
        return editName;
    }

    public static Vehicle createVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        int vehicleId = resultSet.getInt("vehicle_id");
        String vehicleType = resultSet.getString("vehicle_type");

        return new Vehicle(vehicleId, vehicleType);
    }
}
