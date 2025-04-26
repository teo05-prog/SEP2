package view.admin.modify;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.application.Platform;
public class ModifyViewController
{
  @FXML
  private TableView<?> trainTable;

  @FXML
  public void initialize() {
    // Apply the rounded corners using a clip
    Platform.runLater(() -> {
      applyTableRoundedCorners();
    });
  }

  private void applyTableRoundedCorners() {
    // Create a rounded rectangle for clipping
    Rectangle clip = new Rectangle();
    clip.setWidth(trainTable.getWidth());
    clip.setHeight(trainTable.getHeight());
    clip.setArcWidth(20); // This controls the roundness (2 × the radius)
    clip.setArcHeight(20);

    // Apply the clip to the table
    trainTable.setClip(clip);

    // Make the clip resize with the table
    trainTable.widthProperty().addListener((obs, old, newVal) -> {
      clip.setWidth(newVal.doubleValue());
    });

    trainTable.heightProperty().addListener((obs, old, newVal) -> {
      clip.setHeight(newVal.doubleValue());
    });
  }
}