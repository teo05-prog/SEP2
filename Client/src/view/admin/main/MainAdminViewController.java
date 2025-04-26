package view.admin.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.entities.Train;
import view.ViewHandler;

public class MainAdminViewController
{
  @FXML private ListView<Train> trainsListView;

  @FXML private Label messageLabel;

  @FXML private Button trainsButton;
  @FXML private Button myAccountButton;
  @FXML private Button addButton;
  @FXML private Button removeButton;
  @FXML private Button modifyButton;

  public void initialize()
  {
    // Initialize if needed
  }

  public void onTrainsButton(ActionEvent e)
  {
    if (e.getSource() == trainsButton)
      ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
  }

  public void onMyAccountButton(ActionEvent e)
  {
    if (e.getSource() == myAccountButton)
      ViewHandler.showView(ViewHandler.ViewType.ADMIN_ACCOUNT);
  }

  public void onAddButton(ActionEvent e)
  {
    if (e.getSource() == addButton)
      ViewHandler.showView(ViewHandler.ViewType.ADD_TRAIN);
  }

  public void onRemoveButton(ActionEvent e)
  {
    // remove selected train
  }

  public void onModifyButton(ActionEvent e)
  {
    if (e.getSource() == addButton)
      ViewHandler.showView(ViewHandler.ViewType.MODIFY_TRAIN);
  }
}
