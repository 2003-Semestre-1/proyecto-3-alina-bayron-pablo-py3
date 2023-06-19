package Model;

import Controller.RecibirComando;
import Controller.JSON;

/**
 * @author alina
 */
public class CambiarPropietario{
    
    RecibirComando comando;
    JSON json;
    private String nombreUsuario;
    private String path;
    private String currentDirectory;

    public CambiarPropietario(RecibirComando chown, JSON json, String nombreUsuario, String path, String currentDirectory) {
        this.comando = chown;
        this.json = json;
        this.nombreUsuario = nombreUsuario;
        this.path = path;
        this.currentDirectory = currentDirectory;
    }
    
}

