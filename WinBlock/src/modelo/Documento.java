package modelo;

import java.io.File;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
/**
 *
 * @author Giacomo Salvador
 */
public final class Documento extends AnchorPane{
    private AnchorPane areaDocumento;
    private TextArea areaTexto;
    private ScrollPane scroll;
    private File archivo;
    //private LinkedList<UndoManager> listaUndo;
        
    private boolean existencia = false;
    private boolean primera = false;
    private Tab pestanna;
    
    public Documento(){
        //Inicializar:
        this.pestanna = new Tab("ţⱽⱹⱲŢⱽ#|¤|Ⱪ¢ժ");
        this.areaDocumento = new AnchorPane();
        this.areaTexto = new TextArea ();
        this.archivo = new File("ţⱽⱹⱲŢⱽ#|¤|Ⱪ¢ժ");
        this.primera = true;
        this.existencia = true;
        
        //Agregar:
        pestanna.setContent(areaDocumento);
        areaDocumento.getChildren().add(areaTexto);
        asignarMedidasAlAreaDeTexto();
        
        
        //Estilos:
        getStylesheets().add("/recursos/estilos.css");
        getStyleClass().add("documento");
        asignarEstilos();
        
        pestanna.setClosable(false);
    }  
    
        
    public void asignarMedidasAlAreaDeTexto(){
        areaTexto.layoutXProperty().setValue(64);
        areaTexto.layoutXProperty().setValue(41);
        AnchorPane.setTopAnchor(areaTexto, 8.0);
        AnchorPane.setLeftAnchor(areaTexto, 8.0);
        AnchorPane.setRightAnchor(areaTexto, 8.0);
        AnchorPane.setBottomAnchor(areaTexto, 8.0);
        areaTexto.setMinSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        areaTexto.setMaxSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        
    }
    
    public void asignarEstilos(){        
        areaTexto.getStylesheets().add("/recursos/estilos.css");        
        pestanna.getStyleClass().add("pestanna");
        areaTexto.getStyleClass().add("documento");
        areaTexto.getStyleClass().add("fondo");
        areaTexto.getStyleClass().add("color-texto");
    }
    
    public void limpiarComponente(){
        this.areaTexto.setText("");        
    }
    
    
    // ----------------------------------- GET & SET ----------------------------------------
    
    public AnchorPane getAreaDocumento() {
        return areaDocumento;
    }

    public void setAreaDocumento(AnchorPane areaDocumento) {
        this.areaDocumento = areaDocumento;
    }

    public TextArea  getAreaTexto() {
        return areaTexto;
    }

    public void setAreaTexto(TextArea  areaTexto) {
        this.areaTexto = areaTexto;
    }

    public ScrollPane getScroll() {
        return scroll;
    }

    public void setScroll(ScrollPane scroll) {
        this.scroll = scroll;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
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

    public Tab getPestanna() {
        return pestanna;
    }

    public void setPestanna(Tab pestanna) {
        this.pestanna = pestanna;
    }
  
    
    
    
    
}
