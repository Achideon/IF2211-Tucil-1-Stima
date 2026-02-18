package stima;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.fxml.FXMLLoader;

public class App2 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Tugas Kecil 1 IF2211 - Strategi Algoritma");
        stage.setResizable(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/stima/GUI/fxml/Main.fxml"));
        Font.loadFont(getClass().getResourceAsStream("/stima/GUI/resource/TwCenMT.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/stima/GUI/resource/TwCenMT_bold.ttf"),12);
        // System.out.println(TW_Cen_bold.getName());
        Parent main = loader.load();

        Scene scene = new Scene(main, 500, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
