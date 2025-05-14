package network.requestHandlers;

import com.google.gson.Gson;
import model.exceptions.ValidationException;
import services.AuthenticationService;
import dtos.RegisterRequest;

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

      //convert JSON to RegisterRequest
      RegisterRequest registerRequest = gson.fromJson(gson.toJson(payload), RegisterRequest.class);

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
