package network.requestHandlers;

import java.sql.SQLException;

public interface RequestHandler
{
  Object handler(String action, Object payload) throws SQLException;
}
