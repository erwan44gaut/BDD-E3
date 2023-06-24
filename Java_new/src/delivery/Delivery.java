package src.delivery;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Button;

public class Delivery {
    private Integer deliveryId;
    private Integer orderId;
    private Integer deliveryPersonId;
    private String deliveryPersonName;
    private Integer vehicleId;
    private String vehicleType;
    private String deliveryStatus;
    private Date deliveryDate;

    private Button delete;
    private Button updateStatus;

    public Delivery(Integer deliveryId, Integer orderId, Integer deliveryPersonId, String deliveryPersonName,
            Integer vehicleId, String vehicleType, String deliveryStatus, Date deliveryDate) 
    {
        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.deliveryPersonId = deliveryPersonId;
        this.deliveryPersonName = deliveryPersonName;
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.deliveryStatus = deliveryStatus;
        this.deliveryDate = deliveryDate;

        this.delete = new Button("DELETE");
        this.updateStatus = new Button("UPDATE STATUS");
    }

    public Integer getDeliveryId() {
        return deliveryId;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public Integer getDeliveryPersonId() {
        return deliveryPersonId;
    }
    public String getDeliveryPersonName() {
        return deliveryPersonName;
    }
    public Integer getVehicleId() {
        return vehicleId;
    }
    public String getVehicleType() {
        return vehicleType;
    }
    public String getDeliveryStatus() {
        return deliveryStatus;
    }
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Button getDelete() {
        return delete;
    }

    public Button getUpdateStatus() {
        return updateStatus;
    }
    
    public static Delivery createDeliveryFromResultSet(ResultSet resultSet) throws SQLException {
        int deliveryId = resultSet.getInt("delivery_id");
        int orderId = resultSet.getInt("order_id");
        int deliveryPersonId = resultSet.getInt("delivery_person_id");
        String deliveryPersonName = resultSet.getString("delivery_person_name");
        int vehicleId = resultSet.getInt("vehicle_id");
        String vehicleType = resultSet.getString("vehicle_type");
        String deliveryStatus = resultSet.getString("delivery_status");
        Date deliveryDate = resultSet.getDate("delivery_datetime");

        return new Delivery(deliveryId, orderId, deliveryPersonId, deliveryPersonName, vehicleId, vehicleType, deliveryStatus, deliveryDate);
    }
}
