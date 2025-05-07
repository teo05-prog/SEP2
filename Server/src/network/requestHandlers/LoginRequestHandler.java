package network.requestHandlers;

import model.services.AuthenticationService;
import model.services.LoginRequest;

public class LoginRequestHandler implements RequestHandler
{
  private final AuthenticationService authService;

  public LoginRequestHandler(AuthenticationService authService){
    this.authService = authService;
  }

  public Object handler(String action, Object payload){
    if ("attempt".equals(action)){
      String[] data = (String[]) payload;
      String email = data[0];
      String password = data[1];

      LoginRequest loginRequest = new LoginRequest(email,password);
      String result = authService.login(loginRequest); // returns "OK", or error message

      return "Ok".equals(result); //return boolean true if success
    }
    throw new IllegalArgumentException("Unknown action: "+action);
  }
}
