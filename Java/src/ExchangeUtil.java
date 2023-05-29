package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

public class ExchangeUtil 
{
    public static void sendResponse(HttpExchange exchange, int statusCode, String response) 
    {
        try 
        {
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(response.getBytes());
            output.flush();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            exchange.close();
        }
    }
    
    public static String getRequestBody(HttpExchange exchange) 
    {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
        BufferedReader br = new BufferedReader(isr);
        return br.lines().collect(Collectors.joining());
    }

    public static JsonNode getFieldFromObject(String object, String fieldName)
    {
        try 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode objectNode = objectMapper.readTree(object);
            JsonNode fieldNode = objectNode.get(fieldName);
            if (fieldNode != null) 
            {
                return fieldNode;
            }
            return null;
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            return null;
        }
    }
}
