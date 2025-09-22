/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructura;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;

/**
 *
 * @author Isabel
 */
public class Fin extends JFrame {

    public Fin() {
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Fin del juego");
        setLocation(710,290);
        setSize(500, 500);

        ImageIcon icono = new ImageIcon("./media/fondos/fin.png");

        JLabel lblimg = new JLabel(icono);
        add(lblimg);

        setVisible(true);
    }
}
