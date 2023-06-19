package Model;

import Controller.RecibirComando;

/**
 * @author alina
 */
public class CambiarDirectorio implements Comando{
    
    private RecibirComando comando;
    private String path;

    public CambiarDirectorio(RecibirComando cd, String path) {
        this.comando = cd;
        this.path = path;
    }

    @Override
    public void execute() {
        comando.cd(path);
    }
    
}

