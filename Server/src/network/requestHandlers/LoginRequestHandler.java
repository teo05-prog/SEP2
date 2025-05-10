package network.requestHandlers;

import com.google.gson.Gson;
import model.exceptions.ValidationException;
import dtos.AuthenticationService;
import dtos.LoginRequest;

public class LoginRequestHandler implements RequestHandler
{
  private final AuthenticationService authService;
  private final Gson gson = new Gson();

  public LoginRequestHandler(AuthenticationService authService)
  {
    this.authService = authService;
  }

  @Override public Object handler(String action, Object payload)
  {
    if ("login".equals(action) || "auth".equals(action))
    {
      // Convert JSON to LoginRequest
      LoginRequest loginRequest = gson.fromJson(gson.toJson(payload), LoginRequest.class);

      String result = authService.login(loginRequest);
      if ("Ok".equals(result))
      {
        return "Login successful";
      }
      else
      {
        throw new ValidationException(result);
      }
    }
    throw new IllegalArgumentException("Unknown action: " + action);
  }
}
