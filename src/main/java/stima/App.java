package stima;
import java.io.IOException;
import java.util.*;
import java.time.*;

import stima.io_file.*;
import stima.board_utils.*;
import stima.Tile;
import stima.Region;

public class App {
    public static void main(String[] args) throws IOException{
        String path = "data/";
        Scanner filename = new Scanner(System.in);
        System.out.print("Masukkan nama file: ");
        char[][] board = io_file.readFile(path.concat(filename.nextLine()));
        if(!board_utils.isBoardValid(board)){
            throw new IllegalArgumentException("board invalid");
        }
        int n = board.length;
        Region[] regions = new Region[n];
        regions = Region.getRegions(board);
        Tile[] queens = new Tile[n];
        int totalstep = board_solver.howManySteps(regions);
        // shoutout kepada https://stackoverflow.com/questions/4927856/how-can-i-calculate-a-time-difference-in-java untuk perhitungan waktu
        Instant brutestart = Instant.now();
        queens = board_solver.getQueenTiles(0, regions, queens, board, totalstep);
        Instant bruteend = Instant.now();
        if(queens != null){
            System.out.println();
            io_file.printBoard(queens, board);
            System.out.println();
            System.out.println("Waktu pencarian: " + Duration.between(brutestart, bruteend).toMillis() + " ms");
            System.out.println("Jumlah kasus yang ditinjau: " + board_solver.steps);
        } else System.out.println("Tidak ditemukan solusi");
        filename.close();
    }
}
