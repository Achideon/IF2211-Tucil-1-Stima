package stima.backend;

public class Tile {
    public int row, col;
    public char region;
    public Tile(int row, int col, char region){
        this.row = row;
        this.col = col;
        this.region = region;
    }
}