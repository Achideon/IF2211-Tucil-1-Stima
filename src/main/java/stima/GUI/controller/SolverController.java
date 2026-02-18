package stima.GUI.controller;

import stima.GUI.controller.Colors;
import stima.backend.Region;
import stima.backend.Tile;
import stima.backend.board_solver;
import stima.backend.io_file;
import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.time.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.concurrent.Task;

public class SolverController {
    private char[][] board;

    private Tile[] solution;
    
    private Instant brutestart;

    private Instant bruteend;
    
    private int n;

    private final Image queenImg = new Image(getClass().getResourceAsStream("/stima/GUI/resource/queen.png"));

    @FXML
    private Label noSolution;

    @FXML
    private Label timeLabel;

    @FXML
    private Label stepLabel;

    @FXML
    private HBox infoBox;

    @FXML
    public ProgressBar progBar;
    
    @FXML
    private GridPane boardGrid;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnSave;

    private void initialize(){
        System.out.println(progBar);
    }
    
    public void setBoard(char[][] input, int method, SolverController controller){
        this.board = input;
        this.n = input.length;
        btnSave.setManaged(false);
        btnSave.setVisible(false);
        noSolution.setManaged(false);
        noSolution.setVisible(false);
        buildBoard();
        startSolver(method, controller);
    }
    
    private void startSolver(int method, SolverController controller){
        Tile[] queens = new Tile[n];
        Task<Tile[]> task = new Task<>() {
            @Override
            protected Tile[] call() throws IOException {
                Region[] regions = Region.getRegions(board);
                Tile[] queens = new Tile[n];
                brutestart = Instant.now();
                if(method == 1){
                    board_solver.totalstep = board_solver.howManySteps(board.length);
                    queens = board_solver.bruteMethod(0, board, queens, -1, true, controller);
                }
                else if(method == 2){
                    board_solver.totalstep = board_solver.howManySteps2(regions);
                    queens = board_solver.regionMethod(0, regions, queens, board, true, controller);
                } 
                return queens;
            }
        };

        task.setOnSucceeded(e -> {
            Tile[] solver = task.getValue();  
            bruteend = Instant.now();
            solution = solver;
            if(solution != null){
                btnSave.setManaged(true);
                btnSave.setVisible(true);
            }
            else{
                noSolution.setManaged(true);
                noSolution.setVisible(true);
            }
            timeLabel.setText("Waktu pencarian: " + Duration.between(brutestart, bruteend).toMillis() + " ms");
            stepLabel.setText("Jumlah kasus yang ditinjau: " + board_solver.steps + " / " + board_solver.totalstep);
            infoBox.setManaged(true);
            infoBox.setVisible(true);
            updateBoard(solution);
        });
        new Thread(task).start();
    }

    @FXML
    private void buildBoard(){
        boardGrid.getChildren().clear();
        boardGrid.getColumnConstraints().clear();
        boardGrid.getRowConstraints().clear();
        boardGrid.setHgap(5.0);
        boardGrid.setVgap(5.0);
        Region[] regions = Region.getRegions(board);
        int tileSize;
        if(n <= 7) tileSize = 75;
        else if(n <= 14) tileSize = 50;
        else tileSize = 25;

        for(int row = 0; row < n; row++){
            for(int col = 0; col < n; col++){
                StackPane tile = new StackPane();
                tile.setMinSize(tileSize, tileSize);
                tile.setPrefSize(tileSize, tileSize);
                tile.setMaxSize(tileSize, tileSize);
                for(int reg = 0; reg < regions.length; reg++){
                    if(regions[reg].name == board[row][col]){
                        String RGBcolor = Colors.colorToRGB(Colors.colors[reg]);
                        tile.setStyle("-fx-background-color: " + RGBcolor + ";" + 
                                    "-fx-border-color: #00000000;" + 
                                    "-fx-border-width: 8px;" +
                                    "-fx-border-radius: 10;" +
                                    "-fx-background-radius: 10;");

                        boardGrid.add(tile, col, row);
                    }
                }
            }
        }
        boardGrid.setPrefSize(n * tileSize, n * tileSize);
    }

    public StackPane getTile(GridPane boardGrid, int row, int col){
        for(Node node : boardGrid.getChildren()){
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col){
                return (StackPane) node;
            }
        } return null;
    }

    @FXML
    public void updateBoard(Tile[] queens){
        for(Node node : boardGrid.getChildren()){
            StackPane tile = (StackPane) node;
            tile.getChildren().clear();
        }

        if(queens == null) return;
        for(int i = 0; i < queens.length; i++){
            StackPane tile = getTile(boardGrid, queens[i].row, queens[i].col);

            ImageView queenView = new ImageView(queenImg);
            queenView.fitWidthProperty().bind(tile.widthProperty());
            queenView.fitHeightProperty().bind(tile.heightProperty());
            queenView.setPreserveRatio(true);

            tile.getChildren().add(queenView);
        }
    }

    @FXML
    private void saveButton(ActionEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            System.out.println("Save to: " + file.getAbsolutePath());
            io_file.writeFile(solution, board, file);
        }
    }

    @FXML
    private void backButton(ActionEvent event) throws Exception {
        board_solver.steps = 0;
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
