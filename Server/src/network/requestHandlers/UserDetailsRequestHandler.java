package network.requestHandlers;

import model.entities.Traveller;
import model.entities.User;
import persistance.user.UserDAO;

import java.util.HashMap;
import java.util.Map;

public class UserDetailsRequestHandler implements RequestHandler
{
  private final UserDAO userDao;

  public UserDetailsRequestHandler(UserDAO userDao)
  {
    this.userDao = userDao;
  }

  @Override public Object handler(String action, Object payload) throws Exception
  {
    if (action.equals("getUserDetails"))
    {
      return getUserDetails(payload);
    }
    return null;
  }

  private Object getUserDetails(Object content)
  {
    if (content instanceof String)
    {
      String email = (String) content;
      User user = userDao.readByEmail(email);

      if (user != null)
      {
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("name", user.getName());
        userDetails.put("email", user.getEmail());

        if (user instanceof Traveller)
        {
          Traveller traveller = (Traveller) user;
          if (traveller.getBirthDate() != null)
          {
            userDetails.put("birthday", traveller.getBirthDate().toString());
          }
        }
        return userDetails;
      }
    }
    return null;
  }
}
