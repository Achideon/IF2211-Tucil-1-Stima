package stima.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SelectMethodController {

    private int method = -1;

    public int getMethod() {
        return method;
    }

    @FXML
    private void chooseBrute(ActionEvent e) {
        method = 1;
        close(e);
    }

    @FXML
    private void chooseRegion(ActionEvent e) {
        method = 2;
        close(e);
    }

    @FXML
    private void close(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource())
                .getScene()
                .getWindow();
        stage.close();
    }
}
