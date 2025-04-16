package view.front;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import view.ViewHandler;

public class FrontViewController
{
  public FrontViewController()
  {
  }

  @FXML Button loginButton;
  @FXML Button registerButton;

  @FXML public void onLogin(javafx.event.ActionEvent e)
  {
    if (e.getSource() == loginButton)
      ViewHandler.showView(ViewHandler.ViewType.LOGIN);
  }

  @FXML public void onRegister(javafx.event.ActionEvent e)
  {
    if (e.getSource() == registerButton)
      ViewHandler.showView(ViewHandler.ViewType.REGISTER);
  }
}
