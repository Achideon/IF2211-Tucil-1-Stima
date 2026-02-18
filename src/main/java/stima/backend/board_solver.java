package stima.backend;
import java.io.IOException;
import java.util.*;
import stima.GUI.controller.SolverController;
import javafx.application.Platform;

public class board_solver {
    public static long steps = 0;
    public static long totalstep = 0;
    public static boolean isSolutionValid(Tile[] queens){
        int regioncount = 0;
        boolean[] row = new boolean[queens.length];
        boolean[] col = new boolean[queens.length];
        char[] region = new char[queens.length];
        for(int i = 0; i < queens.length; i++){
            int j = 0;
            if(row[queens[i].row]) return false;
            if(col[queens[i].col]) return false;
            while(j < regioncount){
                if(queens[i].region == region[j]) return false;
                j++;
            } 
            region[j] = queens[i].region;
            regioncount++;
            row[queens[i].row] = true;
            col[queens[i].col] = true;
        }
        return true;
    }

    public static boolean isSolutionValid2(Tile[] queens){
        boolean[][] illegal = new boolean[queens.length][queens.length];
        int i = 0;
        while(i < queens.length){
            int row = queens[i].row;
            int col = queens[i].col;
            if(illegal[row][col]) return false;
            if(col > 0 && row > 0) illegal[row-1][col-1] = true;
            if(col < queens.length-1 && row > 0) illegal[row-1][col+1] = true;
            if(col > 0 && row < queens.length-1) illegal[row+1][col-1] = true;
            if(col < queens.length-1 && row < queens.length-1) illegal[row+1][col+1] = true;
            i++;
        }
        return true;
    }

    public static long howManySteps(int n){
        long result = 1;
        for(int i = 1; i <= n; i++){
            result *= (n * (n-1) + i);
            result /= i;
        } return result;
    }

    public static long howManySteps2(Region[] regions){
        long result = 1;
        for(int i = 0; i < regions.length; i++){
            result *= regions[i].count;
        }
        return result;
    }

    public static Tile[] bruteMethod(int depth, char[][] board, Tile[] current, int prev, boolean isGUI, SolverController controller) throws IOException{
        int n = board.length;
        if(depth == n){
            steps++;
            if(steps % (totalstep / 100) == 0){ 
                io_file.updateBoard(isGUI, current, board, steps, totalstep, controller);
            }
            if(isSolutionValid(current) && isSolutionValid2(current)) return current;
            else return null;
        }
        else{
            Tile[] result = new Tile[n];
            for(int i = prev + 1; i <= n * (n-1) + depth; i++){
                int row = i / n;
                int col = i % n;
                Tile queen = new Tile(row, col, board[row][col]);
                current[depth] = queen;
                result = bruteMethod(depth + 1, board, current, i, isGUI, controller);
                if(result != null) return result;
            }
            return result;
        }
    }

    public static Tile[] regionMethod(int depth, Region[] regions, 
                                        Tile[] current, char[][] board, 
                                        boolean isGUI, SolverController controller) throws IOException {
        boolean found = false;
        Tile[] result = new Tile[regions.length];
        if(depth == regions.length){
            steps++;
            if(steps % (totalstep / 100) == 0){ 
                io_file.updateBoard(isGUI, current, board, steps, totalstep, controller);
            }
            if(isSolutionValid(current) && isSolutionValid2(current)) return current;
            else return null;
        }
        else{
            int i = 0;
            while(i < regions[depth].count && !found){
                current[depth] = regions[depth].loc[i];
                result = regionMethod(depth + 1, regions, current, board, isGUI, controller);
                if(result != null) found = true;
                i++;
            }
            return result;
        }
    }
}
