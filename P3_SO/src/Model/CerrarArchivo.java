
package Model;

import Controller.RecibirComando;
import Controller.JSON;

/**
 * @author bayron
 */
public class CerrarArchivo implements Comando{
    
    RecibirComando comando;
    JSON json;
    private String path;
    private String currentDirectory;

    public CerrarArchivo(RecibirComando closefile,  JSON json, String path, String currentDirectory) {
        this.comando = closefile;
        this.json = json;
        this.path = path;
        this.currentDirectory = currentDirectory;
    }

    @Override
    public void execute() {
        comando.closeFile(json, path, currentDirectory);
    }
    
}

