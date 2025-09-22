/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escenario;

import estructura.Cafetera;
import estructura.Panel;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class Escenario {

    public Cafetera cafetera;


    public Escenario() {
        
    }
    public BufferedImage[] esc = new BufferedImage[10];
    public boolean col = false;
    public JLabel dinero;

    Panel pn;

    public Escenario(Panel pn) {
        this.pn = pn;
        imagenes();
    }

    public void imagenes() {
        try {
            esc[0] = ImageIO.read(new File("./media/paredes/suelo.png"));
            esc[1] = ImageIO.read(new File("./media/extras/alfombra.png"));
            esc[2] = ImageIO.read(new File("./media/paredes/pared.png"));

            // Sillas
            esc[3] = ImageIO.read(new File("./media/sillas/silla-izq-rosa.png"));
            esc[4] = ImageIO.read(new File("./media/sillas/silla-izq-blanca.png"));
            esc[5] = ImageIO.read(new File("./media/sillas/silla-der-rosa.png"));
            esc[6] = ImageIO.read(new File("./media/sillas/silla-der-blanca.png"));

            // Mesas
            esc[7] = ImageIO.read(new File("./media/mesas/mesa-lazo-rosa.png"));
            esc[8] = ImageIO.read(new File("./media/mesas/mesa-lazo-blanco.png"));

            //Mostrador
            esc[9] = ImageIO.read(new File("./media/mesas/mostrador.png"));

        } catch (IOException e) {
            System.out.println("No se pudieron cargar las imágenes del escenario");

        }
    }

    public void dibujar(Graphics g) throws FontFormatException, IOException {
        int ancho_img = (int) (pn.tamanio * 1.5); 
        int n_x = (pn.getWidth() - ancho_img) / 2;
        g.drawImage(esc[1], n_x, 150, ancho_img, 30, null);;

        for (int x = 0; x < pn.getWidth(); x += pn.tamanio) {
            for (int y = 0; y < pn.getHeight(); y += pn.tamanio) {
                if (y < 130) {
                    continue; //sii estoy en la parte superior, no pinto nada.
                }
                g.drawImage(esc[0], x, y, pn.tamanio, pn.tamanio, null);
            }

        }

        //Sillas de la izquierda
        g.drawImage(esc[3], 100, 300, 60, 60, null);
        g.drawImage(esc[4], 100, 500, 60, 60, null);
        g.drawImage(esc[4], 600, 300, 60, 60, null);
        g.drawImage(esc[3], 600, 500, 60, 60, null);

        //Sillas de la derecha
        g.drawImage(esc[5], 300, 300, 60, 60, null);
        g.drawImage(esc[6], 300, 500, 60, 60, null);
        g.drawImage(esc[6], 800, 300, 60, 60, null);
        g.drawImage(esc[5], 800, 500, 60, 60, null);

        //Mesas
        g.drawImage(esc[7], 182, 300, 100, 60, null);
        g.drawImage(esc[8], 182, 500, 100, 60, null);
        g.drawImage(esc[8], 682, 300, 100, 60, null);
        g.drawImage(esc[7], 682, 500, 100, 60, null);

        //Mostrador
        g.drawImage(esc[9], 100, 150, 220, 110, null);

        
        //Pared
        g.drawImage(esc[2], 0, 0, 960, 144, null);

    }

}
