package View;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * @author bayron
 */
public class BloquesMemoria extends JPanel{
    
    private static final int PANEL_SIZE = 10;

    public BloquesMemoria(Color color) {
        setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        setBackground(color);
    }   
}
