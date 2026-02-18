package stima.backend;
import java.util.*;

public class Region {
    public char name;
    public Tile[] loc;
    public int count;
    public Region(char name, Tile[] loc, int count){
        this.name = name;
        this.loc = loc;
        this.count = count;
    }

    public static boolean updateRegionList(char name, Region[] regions){
        // cek apakah region udah ada, kalau belum langsung ditambahin, false kalau region sudah ada, true kalau region belum ada
        int i = 0;
        while(i < regions.length && regions[i] != null){
            if(regions[i].name == name) return false;
            i++;
        } 
        if (i < regions.length) regions[i] = new Region(name, new Tile[1024], 0);
        return true;
    }

    // public static boolean isTileValid(Tile loc, char[][] board){
    //     // cek apakah tile benar berdasarkan region (punya neighboring tile, gak terpisah)
    //     int r = loc.row;
    //     int c = loc.col;
    //     char val = board[r][c];
    //     if(c > 0 && val == board[r][c-1]) return true;
    //     if(c < board[r].length - 1 && val == board[r][c+1]) return true;
    //     if(r > 0 && val == board[r-1][c]) return true;
    //     if(r < board.length - 1 && val == board[r+1][c]) return true;
    //     return false;
    // }

    public static void updateRegion(Tile loc, char[][] board, Region[] regions){
        int i = 0;
        int j = 0;
        char name = board[loc.row][loc.col];
        updateRegionList(name, regions);
        while(i < regions.length && regions[i].name != name) i++;
        if (i == regions.length || regions[i] == null) throw new IllegalArgumentException("Region terlalu banyak"); // region penuh, gak bisa update
        while(regions[i].loc[j] != null) j++;
        if (j < regions[i].loc.length){ 
            regions[i].loc[j] = loc;
            regions[i].count++;
        }
    }

    public static Region[] getRegions(char[][] board){
        int i = 0;
        Region[] regions = new Region[board.length];
        System.out.println();
        while(i < board.length){
            int j = 0;
            while(j < board[i].length){
                Tile loc = new Tile(i, j, board[i][j]);
                updateRegion(loc, board, regions);
                j++;
            }
            i++;
        }
        int regioncount = 0;
        while(regioncount < regions.length){ 
            if(regions[regioncount] == null) throw new IllegalArgumentException("Jumlah region kurang");
            regioncount++;
        }
        for(int k = 0; k < regions.length; k++){
            regions[k].loc = Arrays.copyOf(regions[k].loc, regions[k].count);
        }
        return regions;
    }
}

