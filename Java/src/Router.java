package src;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import customer.CustomerController;

public class Router 
{
    public static void route(HttpExchange exchange) throws IOException 
    {
        // Log request
        String remoteAddress = exchange.getRemoteAddress().toString();
        String requestMethod = exchange.getRequestMethod();
        String requestURI = exchange.getRequestURI().toString();
        System.out.println(String.format("%s request from %s: %s", requestMethod, remoteAddress, requestURI));
        

        // Create endpoints here
        if (requestURI.equals("/customers")) 
        {
            CustomerController.getCustomers(exchange);
        }
        else if (requestURI.equals("/customers/add"))
        {
            CustomerController.addCustomer(exchange);
        }
        else
        {
            ExchangeUtil.sendResponse(exchange, 404, "404: Not found");
        }
    }
}
