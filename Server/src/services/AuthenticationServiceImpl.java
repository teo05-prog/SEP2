package services;
//
//import dtos.LoginRequest;
//import dtos.RegisterRequest;
//import dtos.AuthenticationService;
//import model.User;
//import model.exceptions.NotFoundException;
//import model.exceptions.ValidationException;
//import persistance.user.UserDAO;
//
//public class AuthenticationServiceImpl implements AuthenticationService
//{
//  private final UserDAO userDAO;
//
//  public AuthenticationServiceImpl(UserDAO userDAO){
//    this.userDAO = userDAO;
//  }
//
////  @Override public String login(LoginRequest request) {
//////    User user = userDAO.readByEmail(request.getEmail());
////
////    if (user == null){
////      throw new NotFoundException("Email not found.");
////    }
////    if (!user.getPassword().equals(request.getPassword())){
////      throw new ValidationException("Incorrect password.");
////    }
////    return "Ok";
////  }
//
//  @Override
//  public String register(RegisterRequest registerRequest)
//  {
//    if (!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())){
//       throw new ValidationException("Passwords do not match.");
//    }
//
//    // to do emailExists
//
//
////    User user = new User(
////      registerRequest.getName(),
////        registerRequest.getEmail(),
////        registerRequest.getPassword()
////        registerRequest.getBirthday()
////    );
//
//    // to do insert
//    return "Success";
//  }
//
//}
