package network.requestHandlers;

import dtos.RegisterRequest;
import services.user.UserService;

public class UserRequestHandler implements RequestHandler
{
  private final UserService userService;

  public UserRequestHandler(UserService userService)
  {
    this.userService = userService;
  }

  @Override public Object handler(String action, Object payload) throws Exception
  {
    switch (action)
    {
      case "create" -> userService.createTraveller((RegisterRequest) payload);
      case "delete" -> userService.deleteTraveller((RegisterRequest) payload);
      default -> throw new IllegalArgumentException("Invalid action: " + action);
    }
    return null;
  }
}
