/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Giacomo Salvador
 */
public class BuscarController implements Initializable {

    @FXML private Button btnSalir;
    @FXML private AnchorPane anchorPrograma;
    @FXML private TextField txtBuscar;
    @FXML private TextField txtReemplazar;
    @FXML private Button btnBuscar;
    @FXML private Button btnAnterior;
    @FXML private Button btnSiguiente;
    @FXML private Button Reemplazar;
    @FXML private Button ReemplazarTodo;   
    
    private double coordenadaX;
    private double coordenadaY;
    private PrincipalController principal;
    private String textoBase;
    private Matcher selector;
    private Pattern patronBusqueda;
    private int seleccionActual;
    private boolean textoEncontrado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }   
    
    public void recibirControladorPrincipal(PrincipalController principal, String textoBase){
        this.principal = principal;
        this.textoBase = textoBase;
    }
    
    // --------------------------------------- Barra de Programa---------------------------------------------
    
    //Botones de Control:
        
    
    @FXML
    private void clickSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();        
        stage.close();
    }
    
    //Barra de Programa:
    
    @FXML
    private void obtenerCoordenadas(MouseEvent event) {
        coordenadaX = event.getX();
        coordenadaY = event.getY();
    }
    
    @FXML
    private void moverVentana(MouseEvent event) {
        Stage stage = (Stage) anchorPrograma.getScene().getWindow();
        stage.setX(event.getScreenX() - coordenadaX);
        stage.setY(event.getScreenY() - coordenadaY);
    }
    
    
    @FXML
    private void buscarTexto(ActionEvent event){
        if (!principal.getPrograma().getListaDocumento().get(principal.getPanPestannas().getSelectionModel().getSelectedIndex()).getAreaTexto().getText().isEmpty()) {
            patronBusqueda = Pattern.compile(txtBuscar.getText(), Pattern.CASE_INSENSITIVE);
            selector = patronBusqueda.matcher(principal.getPrograma().getListaDocumento().get(principal.getPanPestannas().getSelectionModel().getSelectedIndex()).getAreaTexto().getText());

            if (selector.find()) {                        
                principal.getPrograma().getListaDocumento().get(principal.getPanPestannas().getSelectionModel().getSelectedIndex()).getAreaTexto().selectRange(selector.start(), selector.end());
                seleccionActual = selector.end();
                textoEncontrado = true;
                //busquedaRealizada(true); //Color de los botones Siguiente y Anterior
            }
        }
    }
    
    @FXML
    private void reemplazarTexto(ActionEvent event){
        if(textoEncontrado == true){
            reemplazar(principal.getPrograma().getListaDocumento().get(principal.getPanPestannas().getSelectionModel().getSelectedIndex()).getAreaTexto().getSelectedText(), txtReemplazar.getText());
        
        }
        textoEncontrado = false;
    }
    
    @FXML
    private void reemplazarTodo(ActionEvent event){
        if(textoEncontrado == true && (selector.start() != selector.end())){
            reemplazar(principal.getPrograma().getListaDocumento().get(principal.getPanPestannas().getSelectionModel().getSelectedIndex()).getAreaTexto().getSelectedText(), txtReemplazar.getText());    
            do{                
                if(!principal.getPrograma().getListaDocumento().get(principal.getPanPestannas().getSelectionModel().getSelectedIndex()).getAreaTexto().getText().isEmpty()){
                    patronBusqueda = Pattern.compile(txtBuscar.getText(), Pattern.CASE_INSENSITIVE);
                    selector = patronBusqueda.matcher(principal.getPrograma().getListaDocumento().get(principal.getPanPestannas().getSelectionModel().getSelectedIndex()).getAreaTexto().getText());

                    if (selector.find()) {                        
                        principal.getPrograma().getListaDocumento().get(principal.getPanPestannas().getSelectionModel().getSelectedIndex()).getAreaTexto().selectRange(selector.start(), selector.end());
                        seleccionActual = selector.end();
                        textoEncontrado = true;
                        //busquedaRealizada(true); //Color de los botones Siguiente y Anterior
                    }
                    reemplazar(principal.getPrograma().getListaDocumento().get(principal.getPanPestannas().getSelectionModel().getSelectedIndex()).getAreaTexto().getSelectedText(), txtReemplazar.getText()); 
                }
                
            }while(selector.find());
            
        }
        textoEncontrado = false;
    }
    
    @FXML
    private void irASiguiente(ActionEvent event){
        if (selector.find(seleccionActual)) {
            principal.getPrograma().getListaDocumento().get(principal.getPanPestannas().getSelectionModel().getSelectedIndex()).getAreaTexto().selectRange(selector.start(), selector.end());
            seleccionActual = selector.end();
        }
    }
    
    private void reemplazar(String textoSeleccionado, String reemplazo) {
        String texto =  principal.getPrograma().getListaDocumento().get(principal.getPanPestannas().getSelectionModel().getSelectedIndex()).getAreaTexto().getText();
        principal.getPrograma().getListaDocumento().get(principal.getPanPestannas().getSelectionModel().getSelectedIndex()).getAreaTexto().setText(texto.replaceFirst(textoSeleccionado, reemplazo));
        
        patronBusqueda = Pattern.compile(textoSeleccionado, Pattern.CASE_INSENSITIVE);
        selector = patronBusqueda.matcher(principal.getPrograma().getListaDocumento().get(principal.getPanPestannas().getSelectionModel().getSelectedIndex()).getAreaTexto().getText());
        
    }
    
    
}
