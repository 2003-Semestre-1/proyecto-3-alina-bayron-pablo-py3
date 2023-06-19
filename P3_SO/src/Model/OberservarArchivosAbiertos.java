
package Model;

import Controller.JSON;
import Controller.RecibirComando;

/**
 *
 * @author bayro
 */
public class OberservarArchivosAbiertos implements Comando{
    
    RecibirComando comando;
    JSON json;

    public OberservarArchivosAbiertos(RecibirComando viewFilesOpen, JSON json) {
        this.comando = viewFilesOpen;
        this.json = json;
    }

    @Override
    public void execute() {
        comando.viewFilesOpen(json);
    }
}
