//package services;
//
//import dtos.LoginRequest;
//import dtos.RegisterRequest;
//import dtos.AuthenticationService;
//import model.User;
//import model.exceptions.ValidationException;
//
//public class AuthenticationServiceImpl implements AuthenticationService
//{
//  private final UserDAO userDAO;
//
//  public AuthenticationServiceImpl(UserDAO userDAO){
//    this.userDAO = userDAO;
//  }
//
//  @Override public String login(LoginRequest request) {
//    for (User user : users) {
//      if (user.getEmail().equals(request.getEmail())) {
//        if (user.getPassword().equals(request.getPassword())) {
//          return "Ok";
//        }
//        return "Incorrect password.";
//      }
//    }
//    return "Email not found.";
//  }
//
//  @Override
//  public String register(RegisterRequest registerRequest)
//  {
//    if (!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())){
//       throw new ValidationException("Passwords do not match.");
//    }
//
//
//    User user = new User(
//       registerRequest.getName(),
//        registerRequest.getEmail(),
//       registerRequest.getPassword(),
//       registerRequest.getBirthday()
//    );
//
//    return "Success";
//  }
//
//}
