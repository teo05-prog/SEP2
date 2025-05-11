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
    return switch (action)
    {
      case "login" -> handleLogin(payload);
      case "getRole" -> handleGetRole(payload);
      default -> throw new IllegalArgumentException("Unknown action: " + action);
    };
  }

  private Object handleLogin(Object payload)
  {
    LoginRequest loginRequest = gson.fromJson(gson.toJson(payload), LoginRequest.class);
    String result = authService.login(loginRequest);
    if ("Ok".equals(result))
    {
      return "Login successful";
    }
    throw new ValidationException(result);
  }

  private Object handleGetRole(Object payload)
  {
    String email = gson.fromJson(gson.toJson(payload), String.class);
    return authService.getUserRole(email);
  }
}