package view.traveller.trains;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.entities.Train;
import view.ViewHandler;
import viewmodel.ChooseTrainVM;
import viewmodel.SearchTicketVM;

public class ChooseTrainController
{
  private ChooseTrainVM viewModel;
  private SearchTicketVM searchTicketVM;

  @FXML private Label messageLabel;
  @FXML private ListView<Train> trainListView;
  @FXML private Button continueButton;

  public ChooseTrainController()
  {
    this.viewModel = new ChooseTrainVM();
  }

  public void init(ChooseTrainVM viewModel, SearchTicketVM searchTicketVM)
  {
    if (viewModel != null)
    {
      this.viewModel = viewModel;
    }
    this.searchTicketVM = searchTicketVM;
    bindProperties();
  }

  public void initialize()
  {
    if (viewModel == null)
    {
      viewModel = new ChooseTrainVM();
    }
    //    bindProperties();
  }

  private void bindProperties()
  {
    messageLabel.textProperty().bind(viewModel.messageProperty());
    //    trainListView.setItems(viewModel.getTrainList());
    viewModel.selectedTrainProperty().bind(trainListView.getSelectionModel().selectedItemProperty());

    //    continueButton.disableProperty().bind(viewModel.selectedTrainProperty().isNull());
    continueButton.setOnAction(e -> onContinueButton());
  }

  public void onContinueButton()
  {
    viewModel.continueWithSelectedTrain(); // record selected train

    //    if (viewModel.selectedTrainProperty().get() == null){
    //      return;
    //    }

    if (searchTicketVM != null && searchTicketVM.isSeatOrBicycleSelected())
    {
      ViewHandler.showView(ViewHandler.ViewType.SEAT_SELECTION);
    }
    else
    {
      ViewHandler.showView(ViewHandler.ViewType.CONFIRM_TICKET);
    }
  }
}
