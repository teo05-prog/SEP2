package test;

import dtos.LoginRequest;
import model.entities.MyDate;
import dtos.RegisterRequest;
import model.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import persistance.user.UserDAO;
import services.authentication.AuthenticationServiceImpl;
import services.user.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest
{

  private AuthenticationServiceImpl authenticationService;

  @Mock private UserDAO mockUserDAO;

  @Mock private UserService mockUserService;

  @BeforeEach void setUp()
  {
    MockitoAnnotations.openMocks(this);
    authenticationService = new AuthenticationServiceImpl(mockUserDAO, mockUserService);
  }

  // --- Login Tests ---

  @Test void login_successful()
  {
    LoginRequest request = new LoginRequest("test@example.com", "password123");
    User mockUser = new User("Test User", "password123", "test@example.com");
    when(mockUserDAO.readByEmail("test@example.com")).thenReturn(mockUser);

    String result = authenticationService.login(request);

    assertEquals("Ok", result);
    verify(mockUserDAO, times(1)).readByEmail("test@example.com");
  }

  @Test void login_emailNotFound()
  {
    LoginRequest request = new LoginRequest("nonexistent@example.com", "password123");
    when(mockUserDAO.readByEmail("nonexistent@example.com")).thenReturn(null);

    String result = authenticationService.login(request);

    assertEquals("Email not found.", result);
    verify(mockUserDAO, times(1)).readByEmail("nonexistent@example.com");
  }

  @Test void login_incorrectPassword()
  {
    LoginRequest request = new LoginRequest("test@example.com", "wrongpassword");
    User mockUser = new User("Test User", "password123", "test@example.com");
    when(mockUserDAO.readByEmail("test@example.com")).thenReturn(mockUser);

    String result = authenticationService.login(request);

    assertEquals("Incorrect password.", result);
    verify(mockUserDAO, times(1)).readByEmail("test@example.com");
  }

  // --- Register Tests ---

  @Test void register_successful() throws Exception
  {
    MyDate birthDate = new MyDate(1, 1, 2000, 0, 0);
    RegisterRequest request = new RegisterRequest("New User", "new@example.com", "password123", birthDate);
    when(mockUserDAO.readByEmail("new@example.com")).thenReturn(null);
    // Use ArgumentCaptor to verify the RegisterRequest passed to userService
    ArgumentCaptor<RegisterRequest> registerRequestCaptor = ArgumentCaptor.forClass(RegisterRequest.class);

    String result = authenticationService.register(request);

    assertEquals("Success", result);
    verify(mockUserDAO, times(1)).readByEmail("new@example.com");
    verify(mockUserService, times(1)).createTraveller(registerRequestCaptor.capture());
    RegisterRequest capturedRequest = registerRequestCaptor.getValue();
    assertEquals("New User", capturedRequest.getName());
    assertEquals("new@example.com", capturedRequest.getEmail());
    assertEquals("password123", capturedRequest.getPassword());
    assertEquals(birthDate, capturedRequest.getBirthday());
  }

  @Test void register_nameCannotBeEmpty() throws Exception
  {
    MyDate birthDate = new MyDate(1, 1, 2000, 0, 0);
    RegisterRequest request = new RegisterRequest("", "new@example.com", "password123", birthDate);

    String result = authenticationService.register(request);

    assertEquals("Name cannot be empty", result);
    verify(mockUserDAO, never()).readByEmail(anyString());
    verify(mockUserService, never()).createTraveller(any(RegisterRequest.class));
  }

  @Test void register_nameCannotBeNull() throws Exception
  {
    MyDate birthDate = new MyDate(1, 1, 2000, 0, 0);
    RegisterRequest request = new RegisterRequest(null, "new@example.com", "password123", birthDate);

    String result = authenticationService.register(request);

    assertEquals("Name cannot be empty", result);
    verify(mockUserDAO, never()).readByEmail(anyString());
    verify(mockUserService, never()).createTraveller(any(RegisterRequest.class));
  }

  @Test void register_invalidEmail_format() throws Exception
  {
    MyDate birthDate = new MyDate(1, 1, 2000, 0, 0);
    RegisterRequest request = new RegisterRequest("New User", "invalidemail", "password123", birthDate);

    String result = authenticationService.register(request);

    assertEquals("Invalid email address", result);
    verify(mockUserDAO, never()).readByEmail(anyString());
    verify(mockUserService, never()).createTraveller(any(RegisterRequest.class));
  }

  @Test void register_invalidEmail_empty() throws Exception
  {
    MyDate birthDate = new MyDate(1, 1, 2000, 0, 0);
    RegisterRequest request = new RegisterRequest("New User", "", "password123", birthDate);

    String result = authenticationService.register(request);

    assertEquals("Invalid email address", result);
    verify(mockUserDAO, never()).readByEmail(anyString());
    verify(mockUserService, never()).createTraveller(any(RegisterRequest.class));
  }

  @Test void register_invalidEmail_null() throws Exception
  {
    MyDate birthDate = new MyDate(1, 1, 2000, 0, 0);
    RegisterRequest request = new RegisterRequest("New User", null, "password123", birthDate);

    String result = authenticationService.register(request);

    assertEquals("Invalid email address", result);
    verify(mockUserDAO, never()).readByEmail(anyString());
    verify(mockUserService, never()).createTraveller(any(RegisterRequest.class));
  }

  @Test void register_passwordTooShort() throws Exception
  {
    MyDate birthDate = new MyDate(1, 1, 2000, 0, 0);
    RegisterRequest request = new RegisterRequest("New User", "new@example.com", "pass", birthDate);

    String result = authenticationService.register(request);

    assertEquals("Password must be at least 8 characters long",
        result); // Assuming 8 based on your code, adjust if different
    verify(mockUserDAO, never()).readByEmail(anyString());
    verify(mockUserService, never()).createTraveller(any(RegisterRequest.class));
  }

  @Test void register_passwordNull() throws Exception
  {
    MyDate birthDate = new MyDate(1, 1, 2000, 0, 0);
    RegisterRequest request = new RegisterRequest("New User", "new@example.com", null, birthDate);

    String result = authenticationService.register(request);

    assertEquals("Password must be at least 8 characters long", result);
    verify(mockUserDAO, never()).readByEmail(anyString());
    verify(mockUserService, never()).createTraveller(any(RegisterRequest.class));
  }

  @Test void register_emailAlreadyExists() throws Exception
  {
    MyDate birthDate = new MyDate(1, 1, 2000, 0, 0);
    RegisterRequest request = new RegisterRequest("New User", "existing@example.com", "password123", birthDate);
    User existingUser = new User("Existing User", "password123", "existing@example.com");
    when(mockUserDAO.readByEmail("existing@example.com")).thenReturn(existingUser);

    String result = authenticationService.register(request);

    assertEquals("A user with this email already exists", result);
    verify(mockUserDAO, times(1)).readByEmail("existing@example.com");
    verify(mockUserService, never()).createTraveller(any(RegisterRequest.class));
  }

  @Test void register_userServiceThrowsException() throws Exception
  {
    MyDate birthDate = new MyDate(1, 1, 2000, 0, 0);
    RegisterRequest request = new RegisterRequest("New User", "new@example.com", "password123", birthDate);
    when(mockUserDAO.readByEmail("new@example.com")).thenReturn(null);
    doThrow(new RuntimeException("DB error")).when(mockUserService).createTraveller(any(RegisterRequest.class));

    String result = authenticationService.register(request);

    assertTrue(result.startsWith("Registration failed: "));
    assertEquals("Registration failed: DB error", result);
    verify(mockUserDAO, times(1)).readByEmail("new@example.com");
    verify(mockUserService, times(1)).createTraveller(any(RegisterRequest.class));
  }
}