package stima;
import java.io.*;
import java.util.*;

public class io_file {
    public static char[][] readFile(String path) throws IOException{
        File board = new File(path);
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
}