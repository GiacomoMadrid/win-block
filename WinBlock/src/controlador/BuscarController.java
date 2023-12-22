/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author Giacomo Salvador
 */
public class BuscarController implements Initializable {

    
    @FXML private TextArea txtEntrada;
    @FXML private TextFlow textflowSalida;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtEntrada.textProperty().addListener((observable, oldValue, newValue) -> {
            // Borra el contenido actual del TextFlow
            textflowSalida.getChildren().clear();

            // Analiza el texto y agrega Text con estilos según tus necesidades
            parseAndAddTextToFlow(newValue, textflowSalida);
        });
        
    }   
    
    
    
    private void parseAndAddTextToFlow(String text, TextFlow textFlow) {
        String[] words = text.split("\\s");

        for (String word : words) {
            // Crear un nuevo Text con la palabra actual
            Text textNode = new Text(word + " ");

            // Aplicar estilos directamente al Text
            textNode.setFill(Color.web("#BB0000"));  // Cambiar color del texto
            textNode.setStyle("-fx-background-color: yellow");  // Cambiar color de fondo
            textNode.getStyleClass().add("documento");
            // Agregar el Text al TextFlow
            textFlow.getChildren().add(textNode);
        }
    }
    
}
