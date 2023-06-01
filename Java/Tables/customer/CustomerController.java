package Tables.customer;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.net.httpserver.HttpExchange;
import src.ExchangeUtil;
import src.ResultSetConverter;

public class CustomerController
{
    public static void getCustomers(HttpExchange exchange)
    {
        ResultSet customers = CustomerService.getCustomers();
        String response = ResultSetConverter.convertToJson(customers);
        ExchangeUtil.sendResponse(exchange, 200, response);
    }

    public static void addCustomer(HttpExchange exchange)
    {
        // Get request body
        String requestBody = ExchangeUtil.getRequestBody(exchange);
        if (requestBody == null) 
        {
            ExchangeUtil.sendResponse(exchange, 400, "Bad Request");
            return;
        }

        // Get customer name
        String customerName = ExchangeUtil.getFieldFromObject(requestBody, "name").asText();
        if (customerName == null) 
        {
            ExchangeUtil.sendResponse(exchange, 400, "Invalid Request Body");
            return;
        }

        // Add customer
        boolean queryResult = CustomerService.addCustomer(customerName);
        if (queryResult) 
        {
            ExchangeUtil.sendResponse(exchange, 200, "Customer successfully added");
        } 
        else 
        {
            ExchangeUtil.sendResponse(exchange, 500, "Internal Server Error");
        }
    }
    
    public static void deleteCustomer(HttpExchange exchange)
    {
        // Get request body
        String requestBody = ExchangeUtil.getRequestBody(exchange);
        if (requestBody == null) {
            ExchangeUtil.sendResponse(exchange, 400, "Bad Request");
            return;
        }

        // Get customer id
        String customerId = ExchangeUtil.getFieldFromObject(requestBody, "id").asText();
        if (customerId == null) {
            ExchangeUtil.sendResponse(exchange, 400, "Invalid Request Body");
            return;
        }

        // Delete customer
        boolean queryResult = CustomerService.deleteCustomer(Integer.parseInt(customerId));
        if (queryResult) {
            ExchangeUtil.sendResponse(exchange, 200, "Customer successfully deleted");
        } else {
            ExchangeUtil.sendResponse(exchange, 500, "Internal Server Error");
        }
    }
    
    public static void updateCustomerFields(HttpExchange exchange) 
    {
        // Get request body
        String requestBody = ExchangeUtil.getRequestBody(exchange);
        if (requestBody == null) 
        {
            ExchangeUtil.sendResponse(exchange, 400, "Bad Request");
            return;
        }

        // Get customer id
        String customerId = ExchangeUtil.getFieldFromObject(requestBody, "id").asText();
        if (customerId == null) 
        {
            ExchangeUtil.sendResponse(exchange, 400, "Invalid Request Body");
            return;
        }

        // Get patch object
        JsonNode patchJson = ExchangeUtil.getFieldFromObject(requestBody, "patch");
        if (patchJson == null) 
        {
            ExchangeUtil.sendResponse(exchange, 400, "Invalid Request Body");
            return;
        }

        // Update customer fields
        Iterator<Map.Entry<String, JsonNode>> fields = patchJson.fields();
        while (fields.hasNext()) 
        {
            Map.Entry<String, JsonNode> entry = fields.next();
            String field = entry.getKey();
            String value = entry.getValue().asText();
            boolean queryResult = CustomerService.updateCustomerField(Integer.parseInt(customerId), field, value);
            if (!queryResult) 
            {
                ExchangeUtil.sendResponse(exchange, 500, "Internal Server Error");
                return;
            }
        }

        ExchangeUtil.sendResponse(exchange, 200, "Customer fields successfully updated");
    }
}