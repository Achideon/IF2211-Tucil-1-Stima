package stima.GUI.controller;
import java.io.File;

import stima.backend.board_utils;
import stima.backend.io_file;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

public class BoardController{
    private File file;

    @FXML
    private Label fileLabel;

    @FXML
    private Label noInputWarn;

    @FXML
    private Button btnSolve;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnFile;

    @FXML
    private TextArea inputBoard;

    @FXML
    private void fileButton() throws Exception {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        file = fileChooser.showOpenDialog(stage);
        if(file != null){
            fileLabel.setText(file.getName());
        }
        else{
            fileLabel.setText("No file chosen");
        }
    }

    @FXML
    private void solveButton(ActionEvent event) throws Exception {
        if(file == null && inputBoard.getText().isEmpty()){
            noInputWarn.setManaged(true);
            noInputWarn.setVisible(true);
        }
        else{
            char[][] board;
            if(file != null){
                board = io_file.readFile(file);
            }
            else{
                String input = inputBoard.getText();
                String[] rows = input.split("\n");
                board = new char[rows.length][];
                for(int i = 0; i < rows.length; i++){
                    board[i] = rows[i].toCharArray();
                }
            }
            if(!board_utils.isBoardValid(board)){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/stima/GUI/fxml/BoardInvalid.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(loader.load(), 400, 200);
                stage.setScene(scene);
                stage.setTitle("BOARD INVALID!");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(((Node)event.getSource()).getScene().getWindow());
                stage.showAndWait();
            }
            else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/stima/GUI/fxml/SelectMethod.fxml"));
                Parent main = loader.load();
                Stage popup = new Stage();
                popup.setScene(new Scene(main, 400, 250));
                popup.setTitle("Select Solver");
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.initOwner(((Node)event.getSource()).getScene().getWindow());
                popup.showAndWait();
                SelectMethodController ctrl = loader.getController();
                int method = ctrl.getMethod();

                if(method != -1){
                    loader = new FXMLLoader(getClass().getResource("/stima/GUI/fxml/BoardSolver.fxml"));
                    main = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(main, 600, 800);
                    SolverController controller = loader.getController();
                    controller.setBoard(board, method, controller);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
    }

    @FXML
    private void backButton(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/stima/GUI/fxml/Main.fxml"));
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