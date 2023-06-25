package src.deliveryPerson;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Button;
import src.vehicle.Vehicle;

public class DeliveryPerson {
    private Integer deliveryPersonId;
    private String deliveryPersonName;
    private Integer deliveryPersonVehicleId;
    private String deliveryPersonVehicleDescription;

    private Button editNameButton;

    public DeliveryPerson(Integer deliveryPersonId, String deliveryPersonName, Integer deliveryPersonVehicleId) {
        this.deliveryPersonId = deliveryPersonId;
        this.deliveryPersonName = deliveryPersonName;
        this.deliveryPersonVehicleId = deliveryPersonVehicleId;

        this.deliveryPersonVehicleDescription = Vehicle.getVehicleDescription(deliveryPersonVehicleId);

        this.editNameButton = new Button("Edit name");
    }

    public Integer getDeliveryPersonId()
    {
        return deliveryPersonId;
    }

    public String getDeliveryPersonName()
    {
        return deliveryPersonName;
    }

    public Integer getDeliveryPersonVehicleId()
    {
        return deliveryPersonVehicleId;
    }

    public String getDeliveryPersonVehicleDescription()
    {
        return deliveryPersonVehicleDescription;
    }

    public Button getEditNameButton()
    {
        return editNameButton;
    }

    public static DeliveryPerson createDeliveryPersonFromResultSet(ResultSet resultSet) throws SQLException {
        int deliveryPersonId = resultSet.getInt("delivery_person_id");
        String deliveryPersonName = resultSet.getString("delivery_person_name");
        int deliveryPersonVehicle = resultSet.getInt("vehicle_id");

        return new DeliveryPerson(deliveryPersonId, deliveryPersonName, deliveryPersonVehicle);
    }
}
