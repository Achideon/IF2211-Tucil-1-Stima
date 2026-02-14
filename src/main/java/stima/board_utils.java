package stima;
import stima.Region;

public class board_utils {
    public static boolean isBoardValid(char[][] board){
        // ada n x n tile, berarti row = column
        // ya gitu aja sih
        int i = 1;
        int Nlength = board[0].length;
        while(i < board.length && board[i] != null){
            if(board[i].length != Nlength) return false;
            i++;
        } 
        return (i == Nlength);
    }
}