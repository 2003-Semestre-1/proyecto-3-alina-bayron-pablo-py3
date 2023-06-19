package Model;

import Controller.RecibirComando;

/**
 * @author bayron
 */
public class DatosUsuario implements Comando{
    private RecibirComando comando;
    private String nombreUsuario;
    private String nombreCompleto;

    public DatosUsuario(RecibirComando whoami, String nombreUsuario, String nombreCompleto) {
        this.comando = whoami;
        this.nombreUsuario = nombreUsuario;
        this.nombreCompleto = nombreCompleto;
    }

    @Override
    public void execute() {
        comando.whoAmI(nombreUsuario, nombreCompleto);
    }
}

