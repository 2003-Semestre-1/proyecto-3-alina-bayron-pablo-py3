/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author bayro
 */
public class EjecucionComando {
    private Comando comando;
    
    public EjecucionComando(){
    }
	
    public void ejecutarComando(){
        this.comando.execute();
    }

    public Comando getCommand() {
        return comando;
    }

    public void setCommand(Comando command) {
        this.comando = command;
    }
    
}
