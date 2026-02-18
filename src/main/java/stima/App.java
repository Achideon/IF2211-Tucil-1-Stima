package stima;
import java.io.IOException;
import java.util.*;
import java.time.*;

import stima.backend.*;

public class App {
    public static void main(String[] args) throws IOException{
        String path = "data/";
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan nama file: ");
        char[][] board = io_file.readFileFromPath(path.concat(input.nextLine()));
        if(!board_utils.isBoardValid(board)){
            input.close();
            System.out.println("Papan invalid! Keluar dari program...");
            return;
        }
        int n = board.length;
        Region[] regions = new Region[n];
        regions = Region.getRegions(board);

        System.out.println("========================= Metode Penyelesaian =========================");
        System.out.println("1. Brute force murni (tidak direkomendasikan untuk board 8 x 8 ke atas)");
        System.out.println("2. Brute force berdasarkan warna pada papan");
        System.out.print("Pilih metode penyelesaian (1/2): ");
        int method = input.nextInt();
        input.nextLine();
        Tile[] queens = new Tile[n];
        Instant brutestart = Instant.now();
        if(method == 1){
            board_solver.totalstep = board_solver.howManySteps(board.length);
            queens = board_solver.bruteMethod(0, board, queens, -1, false, null);
        }
        else if(method == 2){
            board_solver.totalstep = board_solver.howManySteps2(regions);
            queens = board_solver.regionMethod(0, regions, queens, board, false, null);
        } 
        else{
            input.close();
            throw new IllegalArgumentException("method invalid");
        }
        Instant bruteend = Instant.now();

        if(queens != null){
            System.out.println();
            io_file.printBoard(queens, board);
            System.out.println("Waktu pencarian: " + Duration.between(brutestart, bruteend).toMillis() + " ms");
            System.out.println("Jumlah kasus yang ditinjau: " + board_solver.steps + " / " + board_solver.totalstep);
            System.out.print("Apakah anda ingin menyimpan solusi? (Y/N): ");
            String savefile = input.nextLine();
            if(savefile.equalsIgnoreCase("y")) io_file.writeFile(queens, board, null);
        } else System.out.println("Tidak ditemukan solusi");
        input.close();
    }
}
