package stima.backend;
import java.io.*;
import java.util.*;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import stima.GUI.controller.SolverController;

public class io_file {
    public static char[][] readFileFromPath(String path) throws IOException{
        File board = new File(path);
        return readFile(board);
    }

    public static char[][] readFile(File board) throws IOException{
        char[][] data = new char[32][32];
        Scanner boardscanner = new Scanner(board);
        int i = 0;
        while(boardscanner.hasNextLine()){
            String row = boardscanner.nextLine();
            data[i] = row.toCharArray();
            i++;
        }
        boardscanner.close();
        return Arrays.copyOf(data, i);
    }

    public static void writeFile(Tile[]queens, char[][] board, File file) throws IOException{
        FileWriter writer;
        if(file == null){
            Scanner input = new Scanner(System.in);
            System.out.print("Masukkan nama file: ");
            String filename = input.nextLine();
            writer = new FileWriter("test/" + filename);
            input.close();
        }
        else writer = new FileWriter(file);
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board.length; col++){
                boolean queen = false;
                for(Tile tile : queens){
                    if(row == tile.row && col == tile.col){ 
                        writer.write("#");
                        queen = true;
                        break;
                    }
                }
                if(!queen) writer.write(board[row][col]);
            }
            writer.write("\n");
        }
        writer.close();
    }

    public static void printBoard(Tile[] queens, char[][] board) throws IOException{
        // System.out.println("\033[H\033[2J");
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                boolean isQueen = false;
                for(int k = 0; k < queens.length; k++){
                    if(queens[k].row == i && queens[k].col == j) isQueen = true;
                }
                if(isQueen) System.out.print("#");
                else System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void updateBoard(boolean isGUI, Tile[] queens, char[][] board, long steps, long totalstep, SolverController controller) throws IOException {
        if(!isGUI){
            printBoard(queens, board);
            System.out.println();
            System.out.print("(");
            long percent = steps * 100 / totalstep;
            for(int prog = 0; prog < percent; prog++) System.out.print("#");
            for(int prog = 0; prog < 100-percent; prog++) System.out.print("-");
            System.out.print(") " + percent + "%");
            System.out.println();
        }
        else{
            Platform.runLater(() -> {
                controller.updateBoard(queens);
                controller.progBar.setProgress((double) steps/totalstep);
            });
        }
    }
}