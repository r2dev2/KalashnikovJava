import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class titleController {
    @FXML
    Button startButton; //need this to get the scene
    @FXML
    protected void startButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("gameboard.fxml"));
        AnchorPane rootLayout = (AnchorPane) loader.load();
        startButton.getScene().setRoot(rootLayout);
    }
    @FXML
    protected void infoButton(ActionEvent event) {

        try {
            System.out.println(System.getProperty("user.dir"));
            String text = new Scanner(new File("src/rules.txt"))
                    .useDelimiter("\\A").next();
            AnchorPane pane = new AnchorPane();
            pane.getChildren().add(new TextArea(text));
            Scene scene = new Scene(pane);


            Stage stage = new Stage();
            stage.setTitle("Kalashnikov Rules");
            stage.setScene(scene);
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
