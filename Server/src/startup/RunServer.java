package startup;

import network.SocketServer;
import utilities.LogLevel;

import java.sql.SQLException;

public class RunServer {
  public static void main(String[] args) {
    try {
      // Get service provider
      ServiceProvider serviceProvider = ServiceProvider.getInstance();

      // Initialize and start the socket server
      SocketServer server = new SocketServer(serviceProvider);

      // Start the server
      server.start();
    } catch (SQLException e) {
      System.err.println("Failed to initialize services: " + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      System.err.println("Server error: " + e.getMessage());
      e.printStackTrace();
    }
  }
}