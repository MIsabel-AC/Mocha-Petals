package objetos;

import estructura.Cafetera;
import estructura.Panel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NPC extends JPanel implements Runnable {

    public static final int ANCHO = 600;
    public static final int ALTO = 600;
    public static final int VELOCIDAD = 2;
    public int x = 805;
    public int y = 100;
    public int destinoX;
    public int destinoY;
    public String tipo;
    public boolean mover = true;
    public long tiempo_destino = 0;
    public Panel pn;
    public Cafetera cafetera;
    public BufferedImage[] f_animacion = new BufferedImage[4];
    public BufferedImage[] d_animacion = new BufferedImage[4];
    public BufferedImage[] i_animacion = new BufferedImage[4];
    public BufferedImage[] a_animacion = new BufferedImage[4];
    public BufferedImage[] elegir_cafe = new BufferedImage[4];
    public BufferedImage[] l_animacion = new BufferedImage[2];
    public BufferedImage[] h_animacion = new BufferedImage[2];

    public int indice_animacion;
    public int contador;
    public BufferedImage img_aleatoria;
    public boolean destino_alcanzado = false;
    public int final_x = 805;
    public int final_y = 100;

    public BufferedImage getImagenAleatoria() {
        return img_aleatoria;
    }

    public void setImagenAleatoria(BufferedImage imagenAleatoria) {
        this.img_aleatoria = imagenAleatoria;

    }

    public NPC(Panel panel) {

        this.setPreferredSize(new Dimension(ANCHO, ALTO));
        this.setBackground(Color.WHITE);
        pn = panel;
        indice_animacion = 0;
        contador = 0;

        imagenes_npc1();

        Thread hilo = new Thread(this);
        hilo.start();
    }

    public int getDestinoX() {
        return destinoX;
    }

    public int getDestinoY() {
        return destinoY;
    }

    public void setDestino(int x, int y) {
        destinoX = x;
        destinoY = y;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void imagenes_npc1() {
        try {
            String[] pngs = {"nf-0.png", "nf-1.png", "nf-0.png", "nf-2.png"};
            for (int i = 0; i < pngs.length; i++) {
                Image img = ImageIO.read(new File("./media/personajes/npc/npc1/" + pngs[i])).getScaledInstance((int) (ImageIO.read(new File("./media/personajes/npc/npc1/" + pngs[i])).getWidth() * 1.3),
                        (int) (ImageIO.read(new File("./media/personajes/npc/npc1/" + pngs[i])).getHeight() * 1.3),
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
            String[] pngs = {"nd-0.png", "nd-1.png", "nd-0.png", "nd-2.png"};
            for (int i = 0; i < pngs.length; i++) {
                Image img = ImageIO.read(new File("./media/personajes/npc/npc1/" + pngs[i])).getScaledInstance((int) (ImageIO.read(new File("./media/personajes/npc/npc1/" + pngs[i])).getWidth() * 1.3),
                        (int) (ImageIO.read(new File("./media/personajes/npc/npc1/" + pngs[i])).getHeight() * 1.3),
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
            String[] pngs = {"ni-0.png", "ni-1.png", "ni-0.png", "ni-2.png"};
            for (int i = 0; i < pngs.length; i++) {
                Image img = ImageIO.read(new File("./media/personajes/npc/npc1/" + pngs[i])).getScaledInstance((int) (ImageIO.read(new File("./media/personajes/npc/npc1/" + pngs[i])).getWidth() * 1.3),
                        (int) (ImageIO.read(new File("./media/personajes/npc/npc1/" + pngs[i])).getHeight() * 1.3),
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
            String[] pngs = {"ns-ilove.png", "ns-dlove.png"};
            for (int i = 0; i < pngs.length; i++) {
                Image img = ImageIO.read(new File("./media/personajes/npc/npc1/" + pngs[i])).getScaledInstance((int) (ImageIO.read(new File("./media/personajes/npc/npc1/" + pngs[i])).getWidth() * 1.3),
                        (int) (ImageIO.read(new File("./media/personajes/npc/npc1/" + pngs[i])).getHeight() * 1.3),
                        Image.SCALE_DEFAULT);
                BufferedImage tamanio_cambiado = new BufferedImage(img.getWidth(null), img.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = tamanio_cambiado.createGraphics();
                g2.drawImage(img, 0, 0, null);
                g2.dispose();
                l_animacion[i] = tamanio_cambiado;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dibujar(Graphics g) {
        super.paintComponent(g);

        BufferedImage[] animacion;
        String direccion = direccion();
        switch (direccion) {
            case "frente":
                animacion = f_animacion;
                break;
            case "derecha":
                animacion = d_animacion;
                break;
            case "izquierda":
                animacion = i_animacion;
                break;
            case "atras":
                animacion = a_animacion;
                break;
            case "sentado":
                animacion = elegir_cafe;
                g.drawImage(img_aleatoria, x, y, null);
                break;
            default:
                animacion = f_animacion;
                break;
        }
        g.drawImage(animacion[indice_animacion], x, y, null);

    }

    public String direccion() {
        if (x < destinoX) {
            return "derecha";
        } else if (x > destinoX) {
            return "izquierda";
        } else if (y < destinoY) {
            return "frente";
        } else if (y > destinoY) {
            return "atras";
        } else if (destino_alcanzado && x == destinoX && y == destinoY && destinoX != 805 && destinoY != 100) {
            return "sentado";
        } else {
            return "";
        }
    }

    //el npc está sentado y  cambiará el destino para irse.
    public void sentado() {
        destinoX = x;
        destinoY = y;
        mover = true;

        try {
            Thread.sleep(30000);
            destinoX = final_x;
            destinoY = final_y;
            destino_alcanzado = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (mover) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mover();
            if (!mover) {
                if (tiempo_destino == 0) {
                    tiempo_destino = System.currentTimeMillis();
                } else if (System.currentTimeMillis() - tiempo_destino >= 5000) {
                    volver();
                    tiempo_destino = 0;
                    if (destino_alcanzado && x == destinoX && y == destinoY) {
                        destinoX = final_x;
                        destinoY = final_y;
                    }
                }
            }

            if (x == final_x && y == final_y) {
                mover = false;
            }

            if (contador % 8 == 0) {
                indice_animacion = (indice_animacion + 1) % f_animacion.length;
            }
            contador++;
            repaint();
        }
    }

    public void volver() {
        destinoX = x;
        destinoY = y;
        mover = true;
    }

    public void mover() {

        if (x < destinoX) {
            x += VELOCIDAD;
        } else if (x > destinoX) {
            x -= VELOCIDAD;
        }
        if (y < destinoY) {
            y += VELOCIDAD;
        } else if (y > destinoY) {
            y -= VELOCIDAD;
        }
        if (Math.abs(x - destinoX) <= VELOCIDAD && Math.abs(y - destinoY) <= VELOCIDAD) {
            mover = false;
            destino_alcanzado = true;
            if (destino_alcanzado) {
                try {
                    Thread.sleep(1000);
                    volver();
                    sentado();
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }
        if (mover) {

            repaint();

        }
    }
}
