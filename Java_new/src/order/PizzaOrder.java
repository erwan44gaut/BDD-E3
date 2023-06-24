package src.order;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;

public class PizzaOrder {
    private Integer orderId;
    private String customerName;
    private String pizzaName;
    private String pizzaSize;
    private Float totalPrice;
    private String orderStatus;
    private String deliveryStatus;
    private Date orderDate;

    private Button updateStatus;
    private Button cancel;
    private Button assignDelivery;

    public PizzaOrder(int orderId, String customerName, String pizzaName, String pizzaSize, Float totalPrice, String orderStatus, String deliveryStatus, Date orderDate) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.pizzaName = pizzaName;
        this.pizzaSize = pizzaSize;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
        this.orderDate = orderDate;
        this.cancel = new Button("CANCEL");
        this.updateStatus = new Button("UPDATE STATUS");
        this.assignDelivery = new Button("ASSIGN DELIVERY");
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public String getPizzaSize() {
        return pizzaSize;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
    
    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Button getCancel() {
        return cancel;
    }

    public Button getUpdateStatus() {
        return updateStatus;
    }
    
    public Button getAssignDelivery() {
        return assignDelivery;
    }

    public List<String> possibleUpdates() {
        List<String> result = new ArrayList<>();
        switch (orderStatus) {
            case "COMPLETED":
                return result;
            case "CANCELED":
                return result;
            case "ACCEPTED":
                result.add("IN_PREPARATION");
                if (deliveryStatus != null)
                {
                    result.add("IN_DELIVERY");
                }
                result.add("COMPLETED");
                result.add("CANCELED");
                return result;
            case "IN_PREPARATION":
                if (deliveryStatus != null)
                {
                    result.add("IN_DELIVERY");
                }
                result.add("COMPLETED");
                result.add("CANCELED");
                return result;
            case "IN_DELIVERY":
                result.add("COMPLETED");
                result.add("CANCELED");
                return result;
            default:
                return result;
        }
    }


    public boolean isCancellable() {
        return orderStatus.equals("ACCEPTED");
    }

    public boolean canAssignDelivery() {
        return "ACCEPTED, IN_PREPARATION".contains(orderStatus) && (deliveryStatus == null || deliveryStatus.equals(""));
    }

    public static PizzaOrder createOrderFromResultSet(ResultSet resultSet) throws SQLException {
        int orderId = resultSet.getInt("order_id");
        String customerName = resultSet.getString("customer_name");
        String pizzaName = resultSet.getString("pizza_name");
        String pizzaSize = resultSet.getString("pizza_size");
        float totalPrice = resultSet.getInt("adjusted_price");
        String orderStatus = resultSet.getString("order_status");
        String deliveryStatus = resultSet.getString("delivery_status");
        Date orderDate = resultSet.getDate("order_datetime");

        return new PizzaOrder(orderId, customerName, pizzaName, pizzaSize, totalPrice, orderStatus, deliveryStatus, orderDate);
    }
}