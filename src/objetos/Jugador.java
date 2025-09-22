/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import estructura.Ajustes;
import estructura.Cafetera;
import estructura.Panel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Isabel
 */
public class Jugador {

    Panel pn;
    public int x = 805;
    public int y = 100;
    int velocidad = 25; // La velocidad era 8.
    public String direccion = "";
    public BufferedImage[] f_animacion = new BufferedImage[4];
    public BufferedImage[] d_animacion = new BufferedImage[4];
    public BufferedImage[] i_animacion = new BufferedImage[4];
    public BufferedImage[] a_animacion = new BufferedImage[4];
    public List<Point> interaccion_NPC = new ArrayList<Point>();
    int f_contador = 0;
    int d_contador = 0;
    int i_contador = 0;
    int a_contador = 0;
    public Ajustes ajustes;
    public Cafetera cafetera;
    public NPC npc;

    public static Muebles[] colision = new Muebles[]{
        //Mesas
        new Muebles(98, 300, 240, 3),
        new Muebles(98, 500, 240, 5),
        new Muebles(598, 300, 240, 5),
        new Muebles(598, 500, 240, 5),
        //Mostrador
        new Muebles(100, 200, 190, 0)

    };

    public Point getPuntojg() {
        return new Point(x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Jugador(Panel pn) {
        this.pn = pn;
        imagenes_jugador();

        //Interacciones con los personajes
        //Primera mesa
        interaccion_NPC.add(new Point(80, 200));
        interaccion_NPC.add(new Point(80, 325));
        interaccion_NPC.add(new Point(280, 200));
        interaccion_NPC.add(new Point(280, 325));

        //Segunda mesa
        interaccion_NPC.add(new Point(80, 400));
        interaccion_NPC.add(new Point(80, 525));
        interaccion_NPC.add(new Point(280, 400));
        interaccion_NPC.add(new Point(255, 525));

        //Tercera mesa
        interaccion_NPC.add(new Point(555, 200));
        interaccion_NPC.add(new Point(580, 325));
        interaccion_NPC.add(new Point(755, 200));
        interaccion_NPC.add(new Point(755, 325));

        //Cuarta mesa
        interaccion_NPC.add(new Point(580, 400));
        interaccion_NPC.add(new Point(580, 525));
        interaccion_NPC.add(new Point(755, 400));
        interaccion_NPC.add(new Point(755, 525));

    }

    public void teclas(KeyEvent e) {
        int presionado = e.getKeyCode();
        switch (presionado) {
            case KeyEvent.VK_LEFT:
                pn.izquierda = true;
                break;
            case KeyEvent.VK_RIGHT:
                pn.derecha = true;
                break;
            case KeyEvent.VK_UP:
                pn.arriba = true;
                break;
            case KeyEvent.VK_DOWN:
                pn.abajo = true;
                break;
            case KeyEvent.VK_W:
                if (x == 230 && y == 200 || x == 205 && y == 200
                        || x == 255 && y == 200) {
                    cafetera = new Cafetera(pn, npc);
                }
                break;
            case KeyEvent.VK_O:
                ajustes = new Ajustes(pn.clip, pn);
                break;
            case KeyEvent.VK_E:
                int x_jg = getPuntojg().x;
                int y_jg = getPuntojg().y;
                for (int i = 0; i < interaccion_NPC.size(); i++) {
                    Point punto = interaccion_NPC.get(i);
                    int x_pto = (int) punto.getX();
                    int y_pto = (int) punto.getY();
                    if (x_jg == x_pto && y_jg == y_pto) {
                        cafetera.comparar_cafes();
                    }
                }
                break;
        }
    }

    public void actualizar() {
        //Margen superior
        if (y < 80) {
            y = 80;
        }

        //Margen izquierdo
        if (x < -28) {
            x = -28;
        }

        //Margen derecha
        if (x > 950 - 80) {
            x = 950 - 80;
        }

        //Margen inferior
        if (y > 660 - 100) {
            y = 660 - 100;
        }
        //Colisiones.
        if (colisiona()) {
            if (direccion.equals("izquierda")) {
                x += velocidad;

            } else if (direccion.equals("derecha")) {
                x -= velocidad;

            } else if (direccion.equals("atras")) {
                y += velocidad;

            } else if (direccion.equals("frente")) {
                y -= velocidad;

            }
        } else {
            if (pn.izquierda) {
                x = x - velocidad;
                direccion = "izquierda";
                i_contador++;
                f_contador = 0;
                d_contador = 0;
                a_contador = 0;
            } else if (pn.derecha) {
                x = x + velocidad;
                direccion = "derecha";
                d_contador++;
                f_contador = 0;
                i_contador = 0;
                a_contador = 0;
            } else if (pn.arriba) {
                y = y - velocidad;
                direccion = "atras";
                a_contador++;
                f_contador = 0;
                i_contador = 0;
                d_contador = 0;
            } else if (pn.abajo) {
                y = y + velocidad;
                direccion = "frente";
                f_contador++;
                i_contador = 0;
                d_contador = 0;
                a_contador = 0;
            }
            if (f_contador == 4) {
                f_contador = 0;
            }
            if (i_contador == 4) {
                i_contador = 0;
            }
            if (d_contador == 4) {
                d_contador = 0;
            }
            if (a_contador == 4) {
                a_contador = 0;
            }

            try {
                Thread.sleep(50); //era 90
            } catch (InterruptedException ae) {
                ae.printStackTrace();
            }
        }
    }

    public boolean colisiona() {
        for (int i = 0; i < colision.length; i++) {
            Muebles mueble = colision[i];
            if (x < mueble.getX() + mueble.getAncho() && x + 80 > mueble.getX() && y < mueble.getY() + mueble.getAlto()
                    && y + 90 > mueble.getY()) {
                return true;
            }
        }
        return false;
    }

    public void imagenes_jugador() {
        try {
            String[] pngs = {"f-0.png", "f-1.png", "f-0.png", "f-2.png"};
            for (int i = 0; i < pngs.length; i++) {
                Image img = ImageIO.read(new File("./media/personajes/jugador/" + pngs[i])).getScaledInstance((int) (ImageIO.read(new File("./media/personajes/jugador/" + pngs[i])).getWidth() * 1.3),
                        (int) (ImageIO.read(new File("./media/personajes/jugador/" + pngs[i])).getHeight() * 1.3),
                        Image.SCALE_DEFAULT);
                BufferedImage tamanio_cambiado = new BufferedImage(img.getWidth(null), img.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = tamanio_cambiado.createGraphics();
                g2.drawImage(img, 0, 0, null);
                g2.dispose();
                f_animacion[i] = tamanio_cambiado;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String[] pngs = {"d-0.png", "d-1.png", "d-0.png", "d-2.png"};
            for (int i = 0; i < pngs.length; i++) {
                Image img = ImageIO.read(new File("./media/personajes/jugador/" + pngs[i])).getScaledInstance((int) (ImageIO.read(new File("./media/personajes/jugador/" + pngs[i])).getWidth() * 1.3),
                        (int) (ImageIO.read(new File("./media/personajes/jugador/" + pngs[i])).getHeight() * 1.3),
                        Image.SCALE_DEFAULT);
                BufferedImage tamanio_cambiado = new BufferedImage(img.getWidth(null), img.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = tamanio_cambiado.createGraphics();
                g2.drawImage(img, 0, 0, null);
                g2.dispose();
                d_animacion[i] = tamanio_cambiado;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String[] pngs = {"i-0.png", "i-1.png", "i-0.png", "i-2.png"};
            for (int i = 0; i < pngs.length; i++) {
                Image img = ImageIO.read(new File("./media/personajes/jugador/" + pngs[i])).getScaledInstance((int) (ImageIO.read(new File("./media/personajes/jugador/" + pngs[i])).getWidth() * 1.3),
                        (int) (ImageIO.read(new File("./media/personajes/jugador/" + pngs[i])).getHeight() * 1.3),
                        Image.SCALE_DEFAULT);
                BufferedImage tamanio_cambiado = new BufferedImage(img.getWidth(null), img.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = tamanio_cambiado.createGraphics();
                g2.drawImage(img, 0, 0, null);
                g2.dispose();
                i_animacion[i] = tamanio_cambiado;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String[] pngs = {"e-0.png", "e-1.png", "e-0.png", "e-2.png"};
            for (int i = 0; i < pngs.length; i++) {
                Image img = ImageIO.read(new File("./media/personajes/jugador/" + pngs[i])).getScaledInstance((int) (ImageIO.read(new File("./media/personajes/jugador/" + pngs[i])).getWidth() * 1.3),
                        (int) (ImageIO.read(new File("./media/personajes/jugador/" + pngs[i])).getHeight() * 1.3),
                        Image.SCALE_DEFAULT);
                BufferedImage tamanio_cambiado = new BufferedImage(img.getWidth(null), img.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = tamanio_cambiado.createGraphics();
                g2.drawImage(img, 0, 0, null);
                g2.dispose();
                a_animacion[i] = tamanio_cambiado;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dibujar(Graphics g) {
        if (direccion.equals("frente")) {
            g.drawImage(f_animacion[f_contador], x, y, null);
        } else if (direccion.equals("derecha")) {
            g.drawImage(d_animacion[d_contador], x, y, null);
        } else if (direccion.equals("izquierda")) {
            g.drawImage(i_animacion[i_contador], x, y, null);
        } else if (direccion.equals("atras")) {
            g.drawImage(a_animacion[a_contador], x, y, null);
        } else {
            g.drawImage(f_animacion[f_contador], x, y, null);
        }
    }
}
