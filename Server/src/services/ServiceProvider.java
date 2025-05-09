package services;

import network.requestHandlers.LoginRequestHandler;
import network.requestHandlers.RequestHandler;
import utilities.ConsoleLogger;
import utilities.LogLevel;
import utilities.Logger;

public class ServiceProvider
{
  public RequestHandler getLoginRequestHandler(){
    return new LoginRequestHandler(new AuthenticationServiceImpl());
  }

  public RequestHandler getRegisterRequestHandler(){
    return null;
  }

  public RequestHandler getSearchRequestHandler(){
    return null;
  }

  public RequestHandler getTrainsRequestHandler(){
    return null;
  }

  public RequestHandler getSeatRequestHandler(){
    return null;
  }

  public RequestHandler getConfirmRequestHandler(){
    return null;
  }

  public RequestHandler getAddRequestHandler(){
    return null;
  }

  public RequestHandler getMainAdminRequestHandler(){
    return null;
  }

  public RequestHandler getModifyRequestHandler(){
    return null;
  }

  public RequestHandler getMyAccountRequestHandler(){
    return null;
  }

  public Logger getLogger(){
    return new ConsoleLogger(LogLevel.INFO);
  }
}
