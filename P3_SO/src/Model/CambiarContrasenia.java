package Model;

import Controller.RecibirComando;
import Controller.JSON;

/**
 * @author bayron
 */
public class CambiarContrasenia implements Comando{
    RecibirComando comando;
    JSON json;
    private String nombreUsuario;

    public CambiarContrasenia(RecibirComando passwd, String nombreUsuario, JSON json) {
        this.comando = passwd;
        this.nombreUsuario = nombreUsuario;
        this.json = json;
    }

    @Override
    public void execute() {
        comando.passwd(nombreUsuario, json);
    }
}

