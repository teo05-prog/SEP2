package view.buyTicket.trains;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.entities.Train;
import viewmodel.ChooseTrainVM;

public class ChooseTrainController
{
  private ChooseTrainVM viewModel;

  @FXML private Label messageLabel;
  @FXML private ListView<Train> trainListView;
  @FXML private Button continueButton;

  public ChooseTrainController(){
    this.viewModel = new ChooseTrainVM();
  }

  public void init(ChooseTrainVM viewModel){
    if (viewModel != null){
      this.viewModel = viewModel;
      bindProperties();
    }
  }

  public void initialize(){
    if (viewModel == null){
      viewModel = new ChooseTrainVM();
    }
    bindProperties();
  }

  private void bindProperties(){
    messageLabel.textProperty().bind(viewModel.messageProperty());
    trainListView.setItems(viewModel.getTrainList());
    viewModel.selectedTrainProperty().bind(trainListView.getSelectionModel().selectedItemProperty());

    continueButton.disableProperty().bind(viewModel.selectedTrainProperty().isNull());
    continueButton.setOnAction(e -> viewModel.continueWithSelectedTrain());
  }
  public void onContinueButton(){
    viewModel.continueWithSelectedTrain();
  }
}
