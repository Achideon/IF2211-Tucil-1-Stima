package stima.GUI.controller;
import javafx.scene.paint.Color;

public class Colors {
    public static Color[] colors = new Color[]{
        Color.RED,
        Color.BLUE,
        Color.GREEN,
        Color.YELLOW,
        Color.ORANGE,
        Color.PURPLE,
        Color.PINK,
        Color.BROWN,
        Color.CYAN,
        Color.MAGENTA,
        Color.LIME,
        Color.NAVY,
        Color.TEAL,
        Color.GOLD,
        Color.CORAL,
        Color.SALMON,
        Color.KHAKI,
        Color.VIOLET,
        Color.TURQUOISE,
        Color.PLUM,
        Color.SKYBLUE,
        Color.DARKGREEN,
        Color.DARKBLUE,
        Color.DARKRED,
        Color.DARKORANGE,
        Color.DARKMAGENTA,
        Color.DEEPPINK,
        Color.DODGERBLUE,
        Color.FORESTGREEN,
        Color.INDIGO,
        Color.OLIVE,
        Color.SLATEBLUE
    };

    public static String colorToRGB(Color color){
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        return String.format("#%02X%02X%02X", r, g, b);
    }
}
