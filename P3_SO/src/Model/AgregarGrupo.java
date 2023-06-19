package Model;

import Controller.RecibirComando;
import Controller.JSON;

/**
 * @author alina
 */
public class AgregarGrupo implements Comando{
    private RecibirComando comando;
    private String nombreGrupo;
    private JSON json;

    public AgregarGrupo(RecibirComando groupAdd, String nombreGrupo, JSON json) {
        this.comando = groupAdd;
        this.nombreGrupo = nombreGrupo;
        this.json = json;
    }

    @Override
    public void execute() {
        comando.groupAdd(nombreGrupo, json);
    }
}

