package network;

import services.ServiceProvider;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer
{
  private final ServiceProvider serviceProvider;

  public SocketServer(ServiceProvider serviceProvider)
  {
    this.serviceProvider = serviceProvider;
  }

  public void start() throws IOException
  {
    ServerSocket serverSocket = new ServerSocket(4892);
    System.out.println("Server started, listening for connections...");
    while (true)
    {
      Socket socket = serverSocket.accept();
      ClientHandler clientHandler = new ClientHandler(socket, serviceProvider);
      Thread socketThread = new Thread(clientHandler);
      socketThread.start();
    }
  }
}
