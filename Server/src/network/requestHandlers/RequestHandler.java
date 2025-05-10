package network.requestHandlers;

public interface RequestHandler
{
  Object handler(String action, Object payload) throws Exception;
}
