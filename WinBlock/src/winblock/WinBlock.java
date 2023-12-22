package winblock;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Giacomo Salvador
 */
public class WinBlock extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/FXMLPrincipal.fxml"));
        
        Scene scene = new Scene(root);        
        Font.loadFont(getClass().getResourceAsStream("/recursos/FuenteWin.ttf"), 16);
        scene.getStylesheets().add("/recursos/estilos.css");
        
        
        stage.getIcons().add(new Image("/recursos/imagenes/Logo.ico"));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
