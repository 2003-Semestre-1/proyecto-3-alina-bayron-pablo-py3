package Model;

import Controller.RecibirComando;
import Controller.JSON;

/**
 *
 * @author alina
 */
public class CrearArchivo implements Comando{
    private RecibirComando comando;
    private JSON json;
    private String nombreUsuario;
    private String nombreArchivo;
    private String directorio;

    public CrearArchivo(RecibirComando touch, JSON json, String nombreUsuario, String nombreArchivo, String directorio) {
        this.comando = touch;
        this.json = json;
        this.nombreUsuario = nombreUsuario;
        this.nombreArchivo = nombreArchivo;
        this.directorio = directorio;
    }
    @Override
    public void execute() {
        comando.touch(nombreArchivo, nombreUsuario, json, directorio);
    }
    
}
