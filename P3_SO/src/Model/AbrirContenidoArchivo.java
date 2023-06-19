package Model;

import Controller.RecibirComando;
import Controller.JSON;

/**
 * @author bayron
 */
public class AbrirContenidoArchivo implements Comando{
    RecibirComando comando;
    JSON json;
    private String nombreArchivo;
    private String currentDirectory;

    public AbrirContenidoArchivo(RecibirComando cat, JSON json, String nombreArchivo, String currentDirectory) {
        this.comando = cat;
        this.json = json;
        this.nombreArchivo = nombreArchivo;
        this.currentDirectory = currentDirectory;
    }
    
    @Override
    public void execute() {
        comando.cat(nombreArchivo, json, currentDirectory);
    }
    
}

