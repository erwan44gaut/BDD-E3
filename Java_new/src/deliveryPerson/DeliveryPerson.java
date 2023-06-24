package src.deliveryPerson;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Button;
import src.vehicle.VehicleService;

public class DeliveryPerson {
    private Integer deliveryPersonId;
    private String deliveryPersonName;
    private Integer deliveryPersonVehicle;
    private Integer deliveryPersonVehicleModel;

    private Button editName;
    private Button delete;

    public DeliveryPerson(Integer deliveryPersonId, String deliveryPersonName, Integer deliveryPersonVehicle) {
        this.deliveryPersonId = deliveryPersonId;
        this.deliveryPersonName = deliveryPersonName;
        this.deliveryPersonVehicle = deliveryPersonVehicle;
        this.deliveryPersonVehicleModel = deliveryPersonVehicleModel;

        this.editName = new Button("Edit name");
        this.delete = new Button("Delete");
    }

    public Integer getDeliveryPersonId()
    {
        return deliveryPersonId;
    }

    public String getDeliveryPersonName()
    {
        return deliveryPersonName;
    }

    public String getDeliveryPersonVehicle()
    {
        String deliveryPersonVehicleType = "";
        
        try {
            ResultSet rs = VehicleService.getVehicleById(deliveryPersonVehicle);
            if (rs.next()) {
                deliveryPersonVehicleType = rs.getString("vehicle_type") + " - " + rs.getString("vehicle_model");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return deliveryPersonVehicleType;
    }

    public Button getEditName()
    {
        return editName;
    }

    public static DeliveryPerson createDeliveryPersonFromResultSet(ResultSet resultSet) throws SQLException {
        int deliveryPersonId = resultSet.getInt("delivery_person_id");
        String deliveryPersonName = resultSet.getString("delivery_person_name");
        int deliveryPersonVehicle = resultSet.getInt("vehicle_id");

        return new DeliveryPerson(deliveryPersonId, deliveryPersonName, deliveryPersonVehicle);
    }
}
