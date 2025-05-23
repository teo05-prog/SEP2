package network.requestHandlers;

import com.google.gson.Gson;
import exceptions.ValidationException;
import services.authentication.AuthenticationService;
import dtos.RegisterDTO;

public class RegisterRequestHandler implements RequestHandler
{
  private final AuthenticationService authService;
  private final Gson gson = new Gson();

  public RegisterRequestHandler(AuthenticationService authService)
  {
    this.authService = authService;
  }

  @Override public Object handler(String action, Object payload)
  {
    if ("create".equals(action))
    {
      RegisterDTO registerRequest = gson.fromJson(gson.toJson(payload), RegisterDTO.class);
      String result = authService.register(registerRequest);
      if ("Success".equals(result))
      {
        return "Success";
      }
      else
      {
        throw new ValidationException(result);
      }
    }
    throw new IllegalArgumentException("Unknown action: " + action);
  }
}
