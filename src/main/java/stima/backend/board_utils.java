package stima.backend;

import stima.backend.Region;

public class board_utils {
    public static boolean isBoardSizeValid(char[][] board){
        int i = 1;
        int Nlength = board[0].length;
        while(i < board.length && board[i] != null){
            if(board[i].length != Nlength) return false;
            i++;
        } 
        return (i == Nlength);
    }

    public static boolean isBoardValid(char[][] board){
        if(!isBoardSizeValid(board)){  
            System.out.println("Ukuran papan tidak valid!");
            return false;
        }
        try {
            Region[] regions = Region.getRegions(board);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}