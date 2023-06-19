package Model;

import Controller.RecibirComando;
import Controller.JSON;

/**
 * @author bayron
 */
public class InformacionSistema implements Comando{
    private RecibirComando comando;
    private JSON json;

    public InformacionSistema(RecibirComando infoFS, JSON json) {
        this.comando = infoFS;
        this.json = json;
    }
    
    @Override
    public void execute() {
        comando.infoFS(json);
    }   
}
