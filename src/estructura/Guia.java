/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructura;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Isabel
 */
public class Guia extends JFrame {

    public Image fondo;
    public Image guia;

    public Guia() {
        setTitle("Guía");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(710, 270);
        setSize(500, 500);

        fondo = new ImageIcon("./media/fondos/fondo.png").getImage();
        guia = new ImageIcon("./media/fondos/guia.png").getImage();
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), null);

        int fanchura = fondo.getWidth(null);
        int faltura = fondo.getHeight(null);

        int ianchura = guia.getWidth(null);
        int ialtura = guia.getHeight(null);
        int x = (fanchura - ianchura) / 2;
        int y = (faltura - ialtura) / 2;
        g.drawImage(guia, x, y, null);
    }
}
