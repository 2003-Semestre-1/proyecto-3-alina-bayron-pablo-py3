package Model;

import Controller.RecibirComando;
import Controller.JSON;

/**
 * @author bayron
 */
public class AgregarUsuario implements Comando {
    private RecibirComando comando;
    private String nombreUsuario;
    private String nombreCompleto;
    private JSON json;

    public AgregarUsuario(RecibirComando userAdd, String nombreUsuario, String nombreCompleto, JSON json) {
        this.comando = userAdd;
        this.nombreUsuario = nombreUsuario;
        this.nombreCompleto = nombreCompleto;
        this.json = json;
    }

    @Override
    public void execute() {
        comando.userAdd(nombreUsuario, nombreCompleto, json);
    }
}

