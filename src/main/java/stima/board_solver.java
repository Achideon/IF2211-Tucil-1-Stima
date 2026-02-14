package stima;
import java.io.IOException;
import java.util.*;

import stima.Tile;
import stima.Region;
import stima.io_file;

public class board_solver {
    public static int steps = 0;
    public static boolean isSolutionValid(Tile[] queens){
        boolean[] row = new boolean[queens.length];
        boolean[] col = new boolean[queens.length];
        for(int i = 0; i < queens.length; i++){
            if(row[queens[i].row]) return false;
            if(col[queens[i].col]) return false;
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

    public static int howManySteps(Region[] regions){
        int result = 1;
        for(int i = 0; i < regions.length; i++){
            result *= regions[i].count;
        }
        return result;
    }

    public static Tile[] getQueenTiles(int depth, Region[] regions, Tile[] current, char[][] board, int totalstep) throws IOException {
        boolean found = false;
        Tile[] result = new Tile[regions.length];
        if(depth == regions.length){
            steps++;
            if(steps % (totalstep / 100) == 0){ 
                io_file.printBoard(current, board);
                System.out.println();
                System.out.print("(");
                int percent = steps * 100 / totalstep;
                for(int prog = 0; prog < percent; prog++) System.out.print("#");
                for(int prog = 0; prog < 100-percent; prog++) System.out.print("-");
                System.out.print(") " + percent + "%");
                System.out.println();
            }
            if(isSolutionValid(current) && isSolutionValid2(current)) return current;
            else return null;
        }
        else{
            int i = 0;
            while(i < regions[depth].count && !found){
                current[depth] = regions[depth].loc[i];
                result = getQueenTiles(depth + 1, regions, current, board, totalstep);
                if(result != null) found = true;
                i++;
            }
            return result;
        }
    }
}
