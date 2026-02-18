package stima.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;

public class BoardInvalidController {
    @FXML
    private void closeBtn(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource())
                .getScene()
                .getWindow();
        stage.close();
    }

    @FXML
    private void close(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource())
                .getScene()
                .getWindow();
        stage.close();
    }
}
