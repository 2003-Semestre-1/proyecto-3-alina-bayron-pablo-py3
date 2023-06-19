package Model;

import Controller.RecibirComando;
import Controller.JSON;

/**
 * @author bayron
 */
public class AbrirArchivo implements Comando{
    
    RecibirComando comando;
    JSON json;
    private String path;
    private String currentDirectory;

    public AbrirArchivo(RecibirComando openFile, JSON json, String path, String currentDirectory) {
        this.comando = openFile;
        this.json = json;
        this.path = path;
        this.currentDirectory = currentDirectory;
    }

    @Override
    public void execute() {
        comando.openFile(json, path, currentDirectory);
    }
    
}

