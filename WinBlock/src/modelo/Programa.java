package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

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
    
    public void agregarDocumentoAPestanna(File archivo){
        //Aumentar el índice en 1:
        numPestannas++;
        comprobarPrimera();
        
        //Preparar Documento:
        AnchorPane anchorPane = new AnchorPane();
        Documento documento = new Documento();
        documento.setArchivo(archivo);
        panPestannas.getTabs().add(documento.getPestanna());
        documento.getPestanna().setText(archivo.getName());
        
        //Añadir a las Listas:
        listaArchivos.add(documento.getArchivo());
        listaDocumento.add(documento);        
        
        panPestannas.getSelectionModel().select(numPestannas);
        documento.limpiarComponente();        
        
        existencia = true;       
    }
    
    public void comprobarPrimera(){
        primera = (numPestannas == 0);
    }
    
    
    public void guardar(File archivo) {        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            writer.write(listaDocumento.get(panPestannas.getSelectionModel().getSelectedIndex()).getAreaTexto().getText());
            writer.close();
        } catch (Exception ex) {
        }
    }

    public void guardarComo() {
        /*
        this.principal.lblAncla.setIcon(icoAncla1); 
        this.principal.setAlwaysOnTop(false);
        */
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar como...");        
        FileChooser.ExtensionFilter filtroArchivosTxt = new FileChooser.ExtensionFilter("Archivos de Texto (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(filtroArchivosTxt);
        File archivo = fileChooser.showSaveDialog(null);
        
        if (archivo != null) {
            if (!archivo.getName().endsWith(".txt")) {
                archivo = new File(archivo.getAbsolutePath() + ".txt");
            }
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
                writer.write(listaDocumento.get(panPestannas.getSelectionModel().getSelectedIndex()).getAreaTexto().getText());
                writer.close();
            } catch (Exception ex) {
            }
        }
        /*
        
        if(anclaje == true){
            this.principal.lblAncla.setIcon(icoAncla2);
        }else{
            this.principal.lblAncla.setIcon(icoAncla1);
        }
        this.principal.setAlwaysOnTop(anclaje); 
        
        */
    }
    
    public void abrirArchivo() {  
        /*
        this.principal.lblAncla.setIcon(icoAncla1); 
        this.principal.setAlwaysOnTop(false);
        */
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir");
        FileChooser.ExtensionFilter filtroArchivosTxt = new FileChooser.ExtensionFilter("Archivos de Texto (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(filtroArchivosTxt);
        File archivo = fileChooser.showOpenDialog(null);
        
        if (archivo != null) {
            try(BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                agregarDocumentoAPestanna(archivo);                 
                StringBuilder contenido = new StringBuilder();
                String linea;             
                
                while ((linea = reader.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
                reader.close();
                listaDocumento.get(panPestannas.getSelectionModel().getSelectedIndex()).getAreaTexto().setText(contenido.toString());
                                
            } catch (Exception ex) {
            }
        }
        
        /*
        if(anclaje == true){
            this.principal.lblAncla.setIcon(icoAncla2);
        }else{
            this.principal.lblAncla.setIcon(icoAncla1);
        }
        this.principal.setAlwaysOnTop(anclaje);
        */
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
