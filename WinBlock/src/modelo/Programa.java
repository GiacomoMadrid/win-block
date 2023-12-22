package modelo;

import java.io.File;
import java.util.LinkedList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Giacomo Salvador
 */
public class Programa {
    private TabPane panPestannas;
    private LinkedList<Documento> listaDocumento;
    private LinkedList<File> listaArchivos;
    
    private int numPestannas;
    private boolean existencia;
    private boolean primera;
    
    public Programa(TabPane panPestannas){
        this.panPestannas = panPestannas;
        this.listaArchivos = new LinkedList<>();
        this.listaDocumento = new LinkedList<>();
        
        this.numPestannas = -1;
        this.existencia = false;
        this.primera = false; 
        
        agregarPestanna();
    }
    
    public void agregarPestanna(){
        //Aumentar el índice en 1:
        numPestannas++;
        comprobarPrimera();
        
        //Preparar Documento:
        AnchorPane anchorPane = new AnchorPane();
        Documento documento = new Documento();
        panPestannas.getTabs().add(documento.getPestanna());
        String contador = ""+numPestannas;
        documento.getPestanna().setText("Nota N° "+contador);
        
        //Añadir a las Listas:
        listaArchivos.add(documento.getArchivo());
        listaDocumento.add(documento);        
        
        panPestannas.getSelectionModel().select(numPestannas);
        documento.limpiarComponente();        
        
        existencia = true;       
    }

    public void cerrarPestanna(){
        listaDocumento.remove(panPestannas.getSelectionModel().getSelectedIndex());
        listaArchivos.remove(panPestannas.getSelectionModel().getSelectedIndex());
        panPestannas.getTabs().remove(panPestannas.getSelectionModel().getSelectedIndex());
        numPestannas--;
        
        if(numPestannas < 0){
            existencia = false;
            numPestannas = -1;
        }
        
        comprobarPrimera();    
    }
    
    
    
    
    public void comprobarPrimera(){
        primera = (numPestannas == 0);
    }
    
    
    
    
    // ----------------------------------- GET & SET ----------------------------------------
    
    public TabPane getPanPestannas() {
        return panPestannas;
    }

    public void setPanPestannas(TabPane panPestannas) {
        this.panPestannas = panPestannas;
    }

    public LinkedList<Documento> getListaDocumento() {
        return listaDocumento;
    }

    public void setListaDocumento(LinkedList<Documento> listaDocumento) {
        this.listaDocumento = listaDocumento;
    }

    public LinkedList<File> getListaArchivos() {
        return listaArchivos;
    }

    public void setListaArchivos(LinkedList<File> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public int getNumPestannas() {
        return numPestannas;
    }

    public void setNumPestannas(int numPestannas) {
        this.numPestannas = numPestannas;
    }

    public boolean isExistencia() {
        return existencia;
    }

    public void setExistencia(boolean existencia) {
        this.existencia = existencia;
    }

    public boolean isPrimera() {
        return primera;
    }

    public void setPrimera(boolean primera) {
        this.primera = primera;
    }
    
    
    
    
    
    
    
    
}
