import network.SocketServer;
import services.ServiceProvider;

import java.io.IOException;

public class ServerMain
{
  public static void main(String[] args) throws IOException
  {
    ServiceProvider serviceProvider = new ServiceProvider();
    SocketServer server = new SocketServer(serviceProvider);
    server.start();
  }
}
