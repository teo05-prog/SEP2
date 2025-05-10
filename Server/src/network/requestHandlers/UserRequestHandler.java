package network.requestHandlers;

import dtos.error.TravellerRequest;
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
      case "create" -> userService.createTraveller((TravellerRequest) payload);
      case "delete" -> userService.deleteTraveller((TravellerRequest) payload);
      default -> throw new IllegalArgumentException("Invalid action: " + action);
    }
    return null;
  }
}
