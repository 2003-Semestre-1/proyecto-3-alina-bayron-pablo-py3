package Model;

import Controller.RecibirComando;
import Controller.JSON;

/**
 * @author bayron
 */
public class CrearDisco implements Comando{
    RecibirComando comando;
    String nombreDisco;
    String tamanioDisco = "128000";
    JSON json;

    public CrearDisco(RecibirComando format, String nombreDisco, String tamanoDisco, JSON json) {
        this.comando = format;
        this.nombreDisco = nombreDisco;
        this.tamanioDisco = tamanoDisco;
        this.json = json;
    }

    @Override
    public void execute() {
        comando.format(nombreDisco,tamanioDisco, json);
    }
}

