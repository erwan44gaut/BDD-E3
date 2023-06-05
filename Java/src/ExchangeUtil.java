package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class ExchangeUtil 
{
    public static void sendResponse(HttpExchange exchange, int statusCode, String response) {
        try {
            byte[] responseBytes = response.getBytes();
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*"); // Allow requests from any origin
            exchange.getResponseHeaders().add("Content-Type", "application/json"); // Set the response content type
            exchange.sendResponseHeaders(statusCode, responseBytes.length);
            OutputStream output = exchange.getResponseBody();
            output.write(responseBytes);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }
    
    public static void sendPreflightResponse(HttpExchange exchange) {
        try {
            Headers headers = exchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Origin", "*");
            headers.add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            headers.add("Access-Control-Allow-Headers", "Content-Type, Authorization");
            headers.add("Access-Control-Max-Age", "86400"); // Optional: specify the maximum time in seconds that the preflight response can be cached

            // Send the headers and a 200 OK status code for the preflight response
            exchange.sendResponseHeaders(200, -1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
