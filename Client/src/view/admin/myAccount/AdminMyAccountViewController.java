package view.admin.myAccount;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import view.ViewHandler;
import viewmodel.AdminMyAccountVM;

public class AdminMyAccountViewController
{
  private AdminMyAccountVM viewModel;

  @FXML private Button trainsButton;
  @FXML private Button myAccountButton;
  @FXML private Label nameLabel;
  @FXML private Label emailLabel;

  public void init(AdminMyAccountVM viewModel)
  {
    this.viewModel = viewModel;
    setupBindings();
    viewModel.refresh();
  }

  @FXML public void initialize()
  {
    myAccountButton.setDisable(true);
  }

  private void setupBindings()
  {
    nameLabel.textProperty().bind(viewModel.nameProperty());
    emailLabel.textProperty().bind(viewModel.emailProperty());
  }

  @FXML private void onTrainsButton(ActionEvent e)
  {
    if (e.getSource() == trainsButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
    }
  }

  @FXML private void onMyAccountButton()
  {
    //do nothing because we are already on this page
  }
}
