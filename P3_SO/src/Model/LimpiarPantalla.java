package Model;

import Controller.RecibirComando;

/**
 * @author alina
 */
public class LimpiarPantalla implements Comando{
    
    private RecibirComando comando;

    public LimpiarPantalla(RecibirComando clear) {
        this.comando = clear;
    }

    @Override
    public void execute() {
        comando.clear();
    }
}
