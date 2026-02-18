package stima.GUI.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController{
    @FXML
    private Button btnStart;

    @FXML
    private void startButton(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/stima/GUI/fxml/BoardSelector.fxml"));
        Parent main = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(main, 500, 600);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void close(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource())
                .getScene()
                .getWindow();
        stage.close();
    }
}