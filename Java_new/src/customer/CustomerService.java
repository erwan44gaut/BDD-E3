package src.customer;
import src.util.DatabaseConnection;
import src.util.ResultSetUtil;

import java.sql.ResultSet;

public class CustomerService
{
    public static ResultSet getCustomer(int id)
    {
        return DatabaseConnection.query(String.format("SELECT * FROM Customer WHERE Customer.customer_id = %d", id));
    }

    public static ResultSet getCustomers()
    {
        return DatabaseConnection.query("SELECT * FROM Customer;");
    }

    public static int addCustomer(String customerName)
    {
        String sqlQuery = String.format("INSERT INTO Customer (customer_name) VALUES (\"%s\")", customerName);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static int deleteCustomer(int customerId)
    {
        String sqlQuery = String.format("DELETE FROM Customer WHERE customer_id=%d", customerId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static int updateCustomerField(int customerId, String field, String value) 
    {
        String sqlQuery = String.format("UPDATE Customer SET %s = '%s' WHERE customer_id = %d", field, value,
                customerId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }
    
    public static int rechargeBalance(int customerId, float amount)
    {
        String sqlQuery = String.format(
                "UPDATE Customer SET customer_balance = customer_balance + GREATEST(0, %s) WHERE customer_id = %d",
                String.valueOf(amount), customerId);
        int queryResult = DatabaseConnection.executeUpdate(sqlQuery);
        return queryResult;
    }

    public static void unitTest()
    {
        // Test getCustomer(int id)
        System.out.println("#########################\nGET CUSTOMER\n#########################\n");
        ResultSet customerResult = CustomerService.getCustomer(1);
        ResultSetUtil.printResultSet(customerResult);

        // Test getCustomers()
        System.out.println("#########################\nGET CUSTOMERS\n#########################\n");
        ResultSet customersResult = CustomerService.getCustomers();
        ResultSetUtil.printResultSet(customersResult);

        // Test addCustomer(String customerName)
        int addResult = CustomerService.addCustomer("John Doe");
        System.out.println("#########################\nADD CUSTOMER\n#########################\n" + addResult);

        // Test deleteCustomer(int customerId)
        int deleteResult = CustomerService.deleteCustomer(1);
        System.out.println("#########################\nGET CUSTOMERS\n#########################\n" + deleteResult);

        // Test updateCustomerField(int customerId, String field, String value)
        int updateResult = CustomerService.updateCustomerField(2, "customer_name", "Jane Smith");
        System.out.println("#########################\nGET CUSTOMERS\n#########################\n" + updateResult);
    }
}