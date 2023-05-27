package src;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


import java.io.IOException;
import java.net.InetSocketAddress;
public class PizzeriaWebApp
{
    public static void main(String[] args)
    {
        // Setup
        Config.load();
        DatabaseConnection.connect();

        // Start web server
        HttpServer server;
        try
        {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/", new MyHandler());
            server.setExecutor(null); // Use the default executor
            server.start();
            System.out.println("Server is running on port 8000");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
    }

    static class MyHandler implements HttpHandler 
    {
        @Override
        public void handle(HttpExchange exchange) throws IOException 
        {
            Router.route(exchange);
        }
    }
}