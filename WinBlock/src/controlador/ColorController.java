/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modelo.Documento;

/**
 * FXML Controller class
 *
 * @author Giacomo Salvador
 */
public class ColorController implements Initializable {
    @FXML private Button btnSalir;
    @FXML private Button btnOcultar;
    @FXML private AnchorPane anchorPrograma;
    @FXML private Slider sliderRojo, sliderVerde, sliderAzul;
    @FXML private Label lblSliderRojo, lblSliderVerde, lblSliderAzul;
    @FXML private Pane panNuevo;
    @FXML private Label lblHexaColor;
    @FXML Pane pan00, pan01, pan02, pan03, pan10, pan11, pan12, pan13, pan20, pan21, pan22, pan23, pan30, pan31, pan32, pan33, 
               pan40, pan41, pan42, pan43, pan50, pan51, pan52, pan53, pan60, pan61, pan62, pan63, pan70, pan71, pan72, pan73;
    @FXML Pane panRecientes0, panRecientes1, panRecientes2, panRecientes3, panRecientes4, panRecientes5, panRecientes6, panRecientes7, 
               panRecientes8, panRecientes9, panRecientesA, panRecientesB, panRecientesC, panRecientesD, panRecientesE, panRecientesF;
    
    private double coordenadaX;
    private double coordenadaY;
    private boolean maximizado;
    
    private Documento documento;
    private Pane[] listaColores;
    private ArrayList<Pane> listaRecientes;
    private int indicador;
    
    private double R;
    private double G;
    private double B;
    private double alpha;
    
    // ------------------------------------------- Inicializador ---------------------------------------------
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.alpha = 1.0;
        this.indicador = 0;
           
        this.listaColores  = new Pane[32];
        this.listaRecientes = new ArrayList<>();
        
        colorearPaneles();
        llenarListasDeColores();
        acomodarSlidersAlIniciar();
        agregarEventos();
        
        sliderRojo.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lblSliderRojo.setText("Rojo: "+actualizarLabelSlider((Double)newValue));
                actualizarLabelNuevo();
                actualizarPanNuevo();
            }
        }); 
        
        sliderVerde.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lblSliderVerde.setText("Verde: "+actualizarLabelSlider((Double)newValue));
                actualizarLabelNuevo();
                actualizarPanNuevo();
            }
        }); 
        
        sliderAzul.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lblSliderAzul.setText("Azul: "+actualizarLabelSlider((Double)newValue));
                actualizarLabelNuevo();
                actualizarPanNuevo();
            }
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
    private void clickSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();        
        stage.close();
    }
    
    //Barra de Programa:
    
    @FXML
    private void getCoordinates(MouseEvent event) {
        coordenadaX = event.getX();
        coordenadaY = event.getY();
    }
    
    @FXML
    private void moverVentana(MouseEvent event) {
        Stage stage = (Stage) anchorPrograma.getScene().getWindow();
        stage.setX(event.getScreenX() - coordenadaX);
        stage.setY(event.getScreenY() - coordenadaY);
    }
    
    //---------------------------------------- VENTANA ----------------------------------------
    
    @FXML
    private void pintarTexto(){
        
        guardarColorReciente();
    }
    
    
    @FXML
    private void resaltarTexto(){
        
        guardarColorReciente();
    }   
    
    @FXML
    private void actualizarSliderRojoMouse(ScrollEvent event){
        double delta = event.getDeltaY();
    
        if (delta < 0) {
            if (sliderRojo.getValue() > sliderRojo.getMin()) {
                sliderRojo.setValue(sliderRojo.getValue() - 1);
            }
        } else {
            if (sliderRojo.getValue() < sliderRojo.getMax()) {
                sliderRojo.setValue(sliderRojo.getValue() + 1);
            }
        }
    }
    
    @FXML
    private void actualizarSliderVerdeMouse(ScrollEvent event){
        double delta = event.getDeltaY();
    
        if (delta < 0) {
            if (sliderVerde.getValue() > sliderVerde.getMin()) {
                sliderVerde.setValue(sliderVerde.getValue() - 1);
            }
        } else {
            if (sliderVerde.getValue() < sliderVerde.getMax()) {
                sliderVerde.setValue(sliderVerde.getValue() + 1);
            }
        }
    }
    
    @FXML
    private void actualizarSliderAzulMouse(ScrollEvent event){
        double delta = event.getDeltaY();
    
        if (delta < 0) {
            if (sliderAzul.getValue() > sliderAzul.getMin()) {
                sliderAzul.setValue(sliderAzul.getValue() - 1);
            }
        } else {
            if (sliderAzul.getValue() < sliderAzul.getMax()) {
                sliderAzul.setValue(sliderAzul.getValue() + 1);
            }
        }
    }
    
    
    // --------------------------------- Métodos auxiliares:
    
    public void actualizarPanNuevo(){
        R = sliderRojo.getValue();
        G = sliderVerde.getValue();
        B = sliderAzul.getValue(); 
        
        panNuevo.setBackground(generarBackground(R,G,B));        
    }
    
    public void colorearPaneles(){
        this.pan00.setBackground(generarBackground(255,255,255));
        this.pan01.setBackground(generarBackground(234,234,190));
        this.pan02.setBackground(generarBackground(217,218,171));
        this.pan03.setBackground(generarBackground(135,135,92));
        this.pan10.setBackground(generarBackground(255,0,0));
        this.pan11.setBackground(generarBackground(182,0,0));
        this.pan12.setBackground(generarBackground(255,72,0));
        this.pan13.setBackground(generarBackground(170,68,0));
        this.pan20.setBackground(generarBackground(0,0,255));
        this.pan21.setBackground(generarBackground(0,183,255));
        this.pan22.setBackground(generarBackground(99,0,99));
        this.pan23.setBackground(generarBackground(255,129,140));
        this.pan30.setBackground(generarBackground(255,255,0));
        this.pan31.setBackground(generarBackground(255,204,0));
        this.pan32.setBackground(generarBackground(255,204,51));
        this.pan33.setBackground(generarBackground(204,204,0));
        this.pan40.setBackground(generarBackground(0,255,0));
        this.pan41.setBackground(generarBackground(0,169,0));
        this.pan42.setBackground(generarBackground(0,89,0));
        this.pan43.setBackground(generarBackground(86,174,27));
        this.pan50.setBackground(generarBackground(153,102,0));
        this.pan51.setBackground(generarBackground(120,88,0));
        this.pan52.setBackground(generarBackground(109,57,0));
        this.pan53.setBackground(generarBackground(204,153,0));
        this.pan60.setBackground(generarBackground(153,153,153));
        this.pan61.setBackground(generarBackground(102,102,102));
        this.pan62.setBackground(generarBackground(51,51,51));
        this.pan63.setBackground(generarBackground(204,204,204));
        this.pan70.setBackground(generarBackground(0,0,0));
        this.pan71.setBackground(generarBackground(17,17,17));
        this.pan72.setBackground(generarBackground(58,45,26));
        this.pan73.setBackground(generarBackground(126,126,85));
    }
    
    public void llenarListasDeColores(){
        this.listaColores[0] = pan00;
        this.listaColores[1] = pan01;
        this.listaColores[2] = pan02;
        this.listaColores[3] = pan03;
        this.listaColores[4] = pan10;
        this.listaColores[5] = pan11;
        this.listaColores[6] = pan12;
        this.listaColores[7] = pan13;
        this.listaColores[8] = pan20;
        this.listaColores[9] = pan21;
        this.listaColores[10] = pan22;
        this.listaColores[11] = pan23;
        this.listaColores[12] = pan30;
        this.listaColores[13] = pan31;
        this.listaColores[14] = pan32;
        this.listaColores[15] = pan33;
        this.listaColores[16] = pan40;
        this.listaColores[17] = pan41;
        this.listaColores[18] = pan42;
        this.listaColores[19] = pan43;
        this.listaColores[20] = pan50;
        this.listaColores[21] = pan51;
        this.listaColores[22] = pan52;
        this.listaColores[23] = pan53;
        this.listaColores[24] = pan60;
        this.listaColores[25] = pan61;
        this.listaColores[26] = pan62;
        this.listaColores[27] = pan63;
        this.listaColores[28] = pan70;
        this.listaColores[29] = pan71;
        this.listaColores[30] = pan72;
        this.listaColores[31] = pan73;
                
        this.listaRecientes.add(panRecientes0);
        this.listaRecientes.add(panRecientes1);
        this.listaRecientes.add(panRecientes2);
        this.listaRecientes.add(panRecientes3);
        this.listaRecientes.add(panRecientes4);
        this.listaRecientes.add(panRecientes5);
        this.listaRecientes.add(panRecientes6);
        this.listaRecientes.add(panRecientes7);
        this.listaRecientes.add(panRecientes8);
        this.listaRecientes.add(panRecientes9);
        this.listaRecientes.add(panRecientesA);
        this.listaRecientes.add(panRecientesB);
        this.listaRecientes.add(panRecientesC);
        this.listaRecientes.add(panRecientesD);
        this.listaRecientes.add(panRecientesE);
        this.listaRecientes.add(panRecientesF);
    }
 
    public void guardarColorReciente(){
        double red = sliderRojo.getValue();
        double green = sliderVerde.getValue();
        double blue = sliderAzul.getValue(); 
        
        listaRecientes.get(this.indicador).setBackground(generarBackground(red, green, blue));
        
        if(this.indicador == 15){
            this.indicador = 0;
            
        }else{
            indicador++;
        }
    }
    
    public String convertirColorAHexadecimal(int red, int green, int blue){
        String strColor = "#"+conversorHexaText(red) + conversorHexaText(green) + conversorHexaText(blue);        
        return strColor;
    }
    
    public Color obtenerFondoPane(Pane panel){
        Background fondo = panel.getBackground();
        
        if (fondo != null && !fondo.getFills().isEmpty()) {
            BackgroundFill fill = fondo.getFills().get(0);
            Color backgroundColor = (Color) fill.getFill();            
            return backgroundColor;    
        }
        
        return (new Color(1.0, 1.0, 1.0, alpha));
    }
    
    public Background generarBackground(double red, double green, double blue){
        red /= 255.0;
        green /= 255.0;
        blue /= 255.0;
        Color color = new Color(red, green, blue, alpha);
        BackgroundFill backgroundFill = new BackgroundFill(color, null, null);
        Background background = new Background(backgroundFill);
        return background;
    }
    
    public String actualizarLabelSlider(Double valor){
        int valorEntero = valor.intValue();
        return ""+valorEntero;       
    }
    
    public void actualizarLabelNuevo(){       
        int rojo = ((Double) sliderRojo.getValue()).intValue();
        int verde = ((Double) sliderVerde.getValue()).intValue();
        int azul = ((Double) sliderAzul.getValue()).intValue();
        String strRed = conversorHexaText(rojo);
        String strGreen = conversorHexaText(verde);
        String strBlue = conversorHexaText(azul);
        
        if(rojo < 16){
            strRed = "0"+conversorHexaText(rojo);
        }
        
        if(verde < 16){
            strGreen = "0"+conversorHexaText(verde);
        }
        
        if(azul < 16){
            strBlue = "0"+conversorHexaText(azul);
        }
        
        lblHexaColor.setText(strRed+strGreen+strBlue);
    }
    
    private void acomodarSlidersAlIniciar(){
        sliderRojo.setValue((obtenerFondoPane(panNuevo)).getRed());
        sliderVerde.setValue((obtenerFondoPane(panNuevo)).getGreen());
        sliderAzul.setValue((obtenerFondoPane(panNuevo)).getBlue());
        
        lblSliderRojo.setText("Rojo: "+actualizarLabelSlider((Double)sliderRojo.getValue()));
        lblSliderVerde.setText("Verde: "+actualizarLabelSlider((Double)sliderVerde.getValue()));
        lblSliderAzul.setText("Azul "+actualizarLabelSlider((Double)sliderAzul.getValue()));
        
        actualizarLabelNuevo();
    }
    
    public void agregarEventos(){
        for(int i=0; i<32; i++){
            final int indice = i;
            this.listaColores[i].setOnMouseClicked(e -> pintarPanelNuevo(this.listaColores[indice]));
        }
        
        for(int i=0; i<16; i++){
            final int indice = i;
            this.listaRecientes.get(i).setOnMouseClicked(e -> pintarPanelNuevo(this.listaRecientes.get(indice)));
        }
        
    }
    
    public void pintarPanelNuevo(Pane panel){
        Color color = obtenerFondoPane(panel);
        sliderRojo.setValue(color.getRed()*255);
        sliderVerde.setValue(color.getGreen()*255);
        sliderAzul.setValue(color.getBlue()*255);
  
    }
    
    public String conversorHexaText(int decimal){
        int resto;
        String numWNam = "";
        char[] hexa = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        
        if(decimal == 0){
            numWNam = "0";
            
        }else if(decimal<0){
            decimal = decimal*(-1);
            
            while(decimal>0){
                resto = decimal%16;
                numWNam = hexa[resto]+numWNam;
                decimal=decimal/16;
            }
            
            numWNam = "-"+numWNam;
            
        }else{         
            while(decimal>0){
                resto = decimal%16;
                numWNam = hexa[resto]+numWNam;
                decimal=decimal/16;
            }
        }        
        
        return numWNam; 
    }
    
}
