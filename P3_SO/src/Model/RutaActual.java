package Model;

import Controller.RecibirComando;

/**
 * @author bayron
 */
public class RutaActual implements Comando{
    private RecibirComando comando;
    private String path;

    public RutaActual(RecibirComando pwd, String path) {
        this.comando = pwd;
        this.path = path;
    }

    @Override
    public void execute() {
        comando.pwd(path);
    }   
}
