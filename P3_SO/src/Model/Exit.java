package Model;

import Controller.RecibirComando;

/**
 * @author bayron
 */
public class Exit implements Comando{
    private RecibirComando comando;

    public Exit(RecibirComando exit) {
        this.comando = exit;
    }

    @Override
    public void execute() {
        comando.exit();
    }
}

