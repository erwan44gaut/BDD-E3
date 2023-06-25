package src.delivery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javafx.scene.control.Button;

public class Delivery {
    private Integer deliveryId;
    private Integer orderId;
    private Integer deliveryPersonId;
    private String deliveryPersonName;
    private Integer vehicleId;
    private String vehicleType;
    private String deliveryStatus;
    private Timestamp deliveryDate;

    private Button cancel;
    private Button updateStatus;

    public Delivery(Integer deliveryId, Integer orderId, Integer deliveryPersonId, String deliveryPersonName,
            Integer vehicleId, String vehicleType, String deliveryStatus, Timestamp deliveryDate) 
    {
        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.deliveryPersonId = deliveryPersonId;
        this.deliveryPersonName = deliveryPersonName;
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.deliveryStatus = deliveryStatus;
        this.deliveryDate = deliveryDate;

        this.cancel = new Button("CANCEL");
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
    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public Button getCancel() {
        return cancel;
    }

    public Button getUpdateStatus() {
        return updateStatus;
    }

    public String[] possibleUpdates() {
        switch (deliveryStatus) {
            case "COMPLETED":
                return new String[]{};
            case "LATE":
                return new String[]{};
            case "ACCEPTED":
                return new String[]{"IN_PROGRESS", "COMPLETED", "LATE"};
            case "IN_PROGRESS":
                return new String[]{"COMPLETED", "LATE"};
            default:
                return new String[]{};
        }
    }


    public boolean isCancellable() {
        return !deliveryStatus.equals("COMPLETED") && !deliveryStatus.equals("LATE");
    }
    
    public static Delivery createDeliveryFromResultSet(ResultSet resultSet) throws SQLException {
        int deliveryId = resultSet.getInt("delivery_id");
        int orderId = resultSet.getInt("order_id");
        int deliveryPersonId = resultSet.getInt("delivery_person_id");
        String deliveryPersonName = resultSet.getString("delivery_person_name");
        int vehicleId = resultSet.getInt("vehicle_id");
        String vehicleType = resultSet.getString("vehicle_type");
        String deliveryStatus = resultSet.getString("delivery_status");
        Timestamp deliveryDate = resultSet.getTimestamp("delivery_datetime");
        return new Delivery(deliveryId, orderId, deliveryPersonId, deliveryPersonName, vehicleId, vehicleType, deliveryStatus, deliveryDate);
    }
}
