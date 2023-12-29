/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package controlador;
import java.io.File;
import modelo.Programa;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 *
 * @author Giacomo Salvador
 */
public class PrincipalController implements Initializable {
    // FXML:    
    @FXML private AnchorPane AnchorPane;
    @FXML private AnchorPane anchorPrograma;
    @FXML private Label lblPrograma;
    @FXML private Label lblIcono;
    
    @FXML private Button btnOcultar;
    @FXML private Button btnSalir;
    @FXML private Button btnAumentar;
    @FXML private Button btnCerrarPestanna;
    
    @FXML private Button btnNuevo;
    
    @FXML private TabPane panPestannas;
    @FXML private Slider sliderTexto;
    @FXML private Label lblTamannoTexto;    
    @FXML private Label lblNumeracionPagina;
    
    //Atributos:
    private double coordenadaX;
    private double coordenadaY;
    private boolean maximizado;
    private Programa programa;
    private double tamannoTexto;
    private PrincipalController principal;
    //Métodos:  
    
    // ------------------------------------------- Inicializador ---------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.principal = this;
        this.maximizado = false;
        this.tamannoTexto = 16.0;
        this.programa = new Programa(panPestannas);        
        actualizarIndice();
        
        //Slider Texto
        sliderTexto.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(programa.isExistencia() == true){
                    redimencionarTexto((Double) newValue);
                    lblTamannoTexto.setText(actualizarLabelTexto((Double)newValue));
                    
                }else{
                    sliderTexto.setValue(sliderTexto.getMin());
                    lblTamannoTexto.setText(actualizarLabelTexto(0.0));
                }
            }
        });   
        
        acomodarSliderAlIniciar();
        
        //Cambio de Pestaña
        panPestannas.getSelectionModel().selectedItemProperty().addListener((observavle, oldTab, newTab)->{
            if(programa.isExistencia() == true && newTab != null && programa.getNumPestannas() >= 0){
               sliderTexto.setValue(programa.getListaDocumento().get(
                    panPestannas.getSelectionModel().getSelectedIndex()).getAreaTexto().getFont().getSize()
                );
                
            }else{
                sliderTexto.setValue(sliderTexto.getMin());
                lblTamannoTexto.setText(actualizarLabelTexto(0.0));
            }
            actualizarIndice();
        });
        
    }  
    
    
    // --------------------------------------- Barra de Programa---------------------------------------------
    
    //Botones de Control:

    @FXML
    private void clickOcultar(ActionEvent event) {
        Stage stage = (Stage) btnOcultar.getScene().getWindow();
        stage.setIconified(true);
    }
        
    
    @FXML
    private void clickAumentar(ActionEvent event) {
        Stage stage = (Stage) btnAumentar.getScene().getWindow();
        this.maximizado = !this.maximizado;
        stage.setMaximized(maximizado);
        
        if(this.maximizado == true){
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            this.btnAumentar.setStyle("-fx-background-image:  url('/recursos/imagenes/EnkojerIco.png')");
        }else{
            this.btnAumentar.setStyle("-fx-background-image:  url('/recursos/imagenes/AumentarIco.png')");
        }
    }

    @FXML
    private void clickSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();        
        //stage.close();
        Platform.exit();
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
    
    
    
    // --------------------------------------- Barra de Estado --------------------------------------------
    
    @FXML
    private void actualizarIndice(){
        if(panPestannas.getSelectionModel().getSelectedIndex() >= 0){
            lblNumeracionPagina.setText("Página: "+(panPestannas.getSelectionModel().getSelectedIndex()+1)+"/"+(programa.getNumPestannas()+1));
   
        }else{
            lblNumeracionPagina.setText("Página: 0/0");
   
        }
        
    }
    
    // --------------------------------------- Area de Trabajo ---------------------------------------------
    
    // ------------------------------------- Caja de Herramientas ------------------------------------------
    
    // ------------------------- Archivo:
    @FXML
    private void agregarArchivo(ActionEvent event){
        programa.agregarPestanna();
        sliderTexto.setValue(tamannoTexto);
    }
    
    @FXML
    private void abrirArchivo(ActionEvent event){
        programa.abrirArchivo();
        
        if(programa.isExistencia()){
            sliderTexto.setValue(tamannoTexto);
        }
    }
    
    @FXML
    private void guardarArchivo(ActionEvent event){
        
        try{                    
            if(programa.isExistencia()){   
                File archivo = programa.getListaDocumento().get(panPestannas.getSelectionModel().getSelectedIndex()).getArchivo();
                if (archivo == null || !archivo.exists()) {
                    programa.guardarComo();

                } else {
                    programa.guardar(archivo);
    
                }
            }
        }catch(Exception ex){        
        }
    }
    
    @FXML
    private void guardarArchivoComo(ActionEvent event){
        if(programa.isExistencia()){ 
            programa.guardarComo();
        }
    }
    
    @FXML
    private void cerrarPestanna(ActionEvent event){
        try{
            if(programa.isExistencia() == true){
                programa.cerrarPestanna();  
                if(programa.isExistencia() == false){
                    sliderTexto.setValue(sliderTexto.getMin());
                    
                }
            }  
        } catch(Exception ex){
        }
    }    
        
    // ------------------------- Texto:
    
    private void redimencionarTexto(Double valor) {
        if (programa.getNumPestannas() >= 0 && panPestannas.getSelectionModel().getSelectedIndex() >= 0 
            && panPestannas.getSelectionModel().getSelectedIndex() < programa.getListaDocumento().size()) {  

                programa.getListaDocumento().get(
                    panPestannas.getSelectionModel().getSelectedIndex()).getAreaTexto().styleProperty().bind(Bindings.format("-fx-font-size: %.0f;", valor)
                );
        }
    }
    
    private String actualizarLabelTexto(Double valor){
        int valorEntero = valor.intValue();
        return ("Tamaño: "+(valorEntero));       
    }
    
    private void acomodarSliderAlIniciar(){
        if(programa.isExistencia() == true){
            sliderTexto.setValue(tamannoTexto);
        }
    }
    
    @FXML
    private void actualizarSliderMouse(ScrollEvent event){
        double delta = event.getDeltaY();
    
        if (delta < 0) {
            if (sliderTexto.getValue() > sliderTexto.getMin()) {
                sliderTexto.setValue(sliderTexto.getValue() - 1);
            }
        } else {
            if (sliderTexto.getValue() < sliderTexto.getMax()) {
                sliderTexto.setValue(sliderTexto.getValue() + 1);
            }
        }
    }
    
    @FXML
    private void iniciarPaletaKolores(ActionEvent event){//Paleta de Colores
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLColor.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            //scene.getStylesheets().add("/recursos/estilos.css");
            scene.getStylesheets().add(getClass().getResource("/recursos/estilos.css").toExternalForm());
            
            ColorController controladorColor = (ColorController) loader.getController();
            controladorColor.recibirControladorPrincipal(principal, programa.getListaDocumento().get(panPestannas.getSelectionModel().getSelectedIndex()).getAreaTexto());
                        
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/recursos/imagenes/Kolores1.png"));
            stage.setTitle("Win Block| Paleta de Colores");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            
            stage.show();
        } catch (Exception ex) {
            
        }
    }
    
    @FXML
    private void mostrarFuentes(){
    
    }
    
    @FXML
    private void copiarTexto(ActionEvent event){
        programa.copiar();
    }
    
    @FXML
    private void cortarTexto(ActionEvent event){
        programa.cortar();
    }
    
    @FXML
    private void pegarTexto(ActionEvent event){
        programa.pegar();
    }
    
    // ------------------------- Herramientas:
    
    
    @FXML
    private void deshacer(){
        programa.getListaDocumento().get(panPestannas.getSelectionModel().getSelectedIndex()).getAreaTexto().undo();
    }
    
    @FXML
    private void rehacer(){
        programa.getListaDocumento().get(panPestannas.getSelectionModel().getSelectedIndex()).getAreaTexto().redo();
    }
    
    
    @FXML
    private void iniciarBusqueda(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLBuscar.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            BuscarController controladorBusqueda = (BuscarController) loader.getController();
            controladorBusqueda.recibirControladorPrincipal(principal, programa.getListaDocumento().get(panPestannas.getSelectionModel().getSelectedIndex()).getAreaTexto().getText());
            
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/recursos/imagenes/Pajina2.png"));
            stage.setTitle("Win Block | Buscar y reemplazar");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            
            stage.show();
        } catch (Exception ex) {
            
        }
    }
    
    
    
    
    // -------------------------------------------------- Get & Set ---------------------------------------------------

    public TabPane getPanPestannas() {
        return panPestannas;
    }

    public Programa getPrograma() {
        return programa;
    }
    
    
    
    
    
    
    
}
