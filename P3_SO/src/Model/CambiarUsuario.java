
package Model;

import Controller.RecibirComando;
import Controller.JSON;

/**
 * @author bayron
 */
public class CambiarUsuario implements Comando{
    
    RecibirComando comando;
    JSON json;
    private String nombreUsuario;
    private String contrasenia;

    public CambiarUsuario(RecibirComando su, String nombreUsuario, JSON json, String contrasenia) {
        this.comando = su;
        this.nombreUsuario = nombreUsuario;
        this.json = json;
        this.contrasenia = contrasenia;
    }

    @Override
    public void execute() {
        comando.su(nombreUsuario,contrasenia,json);
    }
    
}

