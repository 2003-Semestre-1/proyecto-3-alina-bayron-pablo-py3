package Model;

import Controller.RecibirComando;
import Controller.JSON;

/**
 * @author alina
 */
public class CrearDirectorio implements Comando{
    
    RecibirComando comando;
    private String path;
    private String nombreCreador;
    private String nombreDirectorio;
    JSON json;
    
    public CrearDirectorio(RecibirComando mkdir, JSON json, String path, String nombreUsuario, String nombreDireciotrio) {
        this.comando = mkdir;
        this.json = json;
        this.path = path;
        this.nombreCreador = nombreUsuario;
        this.nombreDirectorio = nombreDireciotrio;
    }
    @Override
    public void execute() {
        comando.mkdir(nombreDirectorio, path, json, nombreCreador, nombreDirectorio);
    }
    
}
