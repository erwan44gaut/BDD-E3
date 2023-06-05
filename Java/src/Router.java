package src;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import Tables.customer.CustomerController;
import Tables.deliveryPerson.DeliveryPersonController;
import Tables.ingredient.IngredientController;

public class Router 
{
    public static void route(HttpExchange exchange) throws IOException 
    {
        // Log request
        String remoteAddress = exchange.getRemoteAddress().toString();
        String requestMethod = exchange.getRequestMethod();
        String requestURI = exchange.getRequestURI().toString();
        System.out.println(String.format("%s request from %s: %s", requestMethod, remoteAddress, requestURI));

        // Handle OPTIONS request
        if (requestMethod.equalsIgnoreCase("OPTIONS")) 
        {
            ExchangeUtil.sendPreflightResponse(exchange);
            return;
        }

        // Create endpoints here
        if (requestURI.equals("/customers")) {
            CustomerController.getCustomers(exchange);
        } else if (requestURI.equals("/customers/add")) {
            CustomerController.addCustomer(exchange);
        } else if (requestURI.equals("/customers/delete")) {
            CustomerController.deleteCustomer(exchange);
        } else if (requestURI.equals("/customers/update")) {
            CustomerController.updateCustomerFields(exchange);
        } else if (requestURI.equals("/deliveryPersons")) {
            DeliveryPersonController.getDeliveryPersons(exchange);
        } else if (requestURI.equals("/ingredients")) {
            IngredientController.getIngredients(exchange);
        } else {
            ExchangeUtil.sendResponse(exchange, 404, "404: Not found");
        }
    }
}
