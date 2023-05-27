package customer;

import java.io.IOException;
import java.sql.ResultSet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        String requestBody = ExchangeUtil.getRequestBody(exchange);
        if (requestBody == null)
        {
            ExchangeUtil.sendResponse(exchange, 400, "Bad Request");
            return;
        }

        String customerName = null;
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(requestBody);
            JsonNode nameNode = jsonNode.get("name");
            if (nameNode != null && nameNode.isTextual())
            {
                customerName = nameNode.asText();
            }
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        if (customerName == null) 
        {
            ExchangeUtil.sendResponse(exchange, 400, "Invalid Request Body");
            return;
        }

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
}