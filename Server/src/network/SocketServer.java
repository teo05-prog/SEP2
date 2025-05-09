package network;

import startup.ServiceProvider;
import utilities.LogLevel;
import utilities.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer
{
  private final ServiceProvider serviceProvider;
  private final Logger logger;
  private final int port;

  public SocketServer(ServiceProvider serviceProvider)
  {
    this(serviceProvider, 4892);
  }

  public SocketServer(ServiceProvider serviceProvider, int port)
  {
    this.serviceProvider = serviceProvider;
    this.logger = serviceProvider.getLogger();
    this.port = port;
  }

  public void start() throws IOException
  {
    ServerSocket serverSocket = new ServerSocket(port);
    logger.log("Server started, listening for connections on port " + port + "...", LogLevel.INFO);

    while (true)
    {
      Socket socket = serverSocket.accept();
      String clientAddress = socket.getInetAddress().getHostAddress();
      logger.log("New connection from " + clientAddress, LogLevel.INFO);

      ClientHandler clientHandler = new ClientHandler(socket, serviceProvider);
      Thread socketThread = new Thread(clientHandler);
      socketThread.start();
    }
  }
}