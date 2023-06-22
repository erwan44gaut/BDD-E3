package src.customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Button;

public class Customer {
    private Integer customerId;
    private String customerName;
    private Float customerBalance;

    private Button rechargeBalance;
    private Button editName;
    private Button delete;

    public Customer(Integer customerId, String customerName, Float customerBalance) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerBalance = customerBalance;

        this.rechargeBalance = new Button("Recharge balance");
        this.editName = new Button("Edit name");
        this.delete = new Button("Delete");
    }

    public Integer getCustomerId()
    {
        return customerId;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public Float getCustomerBalance()
    {
        return customerBalance;
    }

    public Button getRechargeBalance()
    {
        return rechargeBalance;
    }

    public Button getEditName()
    {
        return editName;
    }

    public static Customer createCustomerFromResultSet(ResultSet resultSet) throws SQLException {
        int customerId = resultSet.getInt("customer_id");
        String customerName = resultSet.getString("customer_name");
        Float customerBalance = resultSet.getFloat("customer_balance");

        return new Customer(customerId, customerName, customerBalance);
    }
}