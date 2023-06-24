package src.deliveryPerson;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Button;

public class DeliveryPerson {
    private Integer deliveryPersonId;
    private String deliveryPersonName;

    private Button editName;
    private Button delete;

    public DeliveryPerson(Integer deliveryPersonId, String deliveryPersonName) {
        this.deliveryPersonId = deliveryPersonId;
        this.deliveryPersonName = deliveryPersonName;

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

    public Button getEditName()
    {
        return editName;
    }

    public static DeliveryPerson createDeliveryPersonFromResultSet(ResultSet resultSet) throws SQLException {
        int deliveryPersonId = resultSet.getInt("delivery_person_id");
        String deliveryPersonName = resultSet.getString("delivery_person_name");

        return new DeliveryPerson(deliveryPersonId, deliveryPersonName);
    }
}
