package network.requestHandlers;

import com.google.gson.Gson;
import exceptions.ValidationException;
import services.authentication.AuthenticationService;
import dtos.LoginDTO;
import utilities.LogLevel;
import utilities.Logger;

public class LoginRequestHandler implements RequestHandler
{
  private final AuthenticationService authService;
  private final Gson gson = new Gson();
  private final Logger logger;

  public LoginRequestHandler(AuthenticationService authService, Logger logger)
  {
    this.authService = authService;
    this.logger = logger;
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
    LoginDTO loginRequest = gson.fromJson(gson.toJson(payload), LoginDTO.class);
    String result = authService.login(loginRequest);
    if ("Ok".equals(result))
    {
      logger.log("Login successful for: " + loginRequest.getEmail(), LogLevel.INFO);
      return loginRequest.getEmail();
    }
    logger.log("Login failed for: " + loginRequest.getEmail() + ". Reason: " + result, LogLevel.WARNING);

    throw new ValidationException(result != null ? result : "Unknown error during login");
  }

  private Object handleGetRole(Object payload)
  {
    String email = gson.fromJson(gson.toJson(payload), String.class);
    return authService.getUserRole(email);
  }
}