package dtos;

public interface AuthenticationService
{
  public abstract String login(LoginRequest request);

  public abstract String register(RegisterRequest registerRequest);
}
