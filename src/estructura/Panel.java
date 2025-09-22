/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructura;

import static estructura.Titulo.conex;
import objetos.Jugador;
import objetos.NPC;
import escenario.Escenario;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable, KeyListener {

    static Connection conexion = null;
    final int tamanioGeneral = 8;
    final int nivelar = 3;
    public final int tamanio = tamanioGeneral * nivelar;
    public final int anchura = 948;
    public final int altura = 660;
    final int fps = 60;
    public boolean izquierda, derecha, arriba, abajo;
    public List<Point> destinos = new ArrayList<Point>();
    public List<NPC> npcs = new ArrayList<>();
    public boolean ajustes_visible = false;
    public String[] canciones = {"./media/musica/fondo/wav/aesthetics-138637.wav", "./media/musica/fondo/wav/french-jazz-music-142911.wav", "./media/musica/fondo/wav/lofi-chill-140858.wav", "./media/musica/fondo/wav/weeknds-122592.wav", "./media/musica/fondo/wav/acoustic-folk-music-guitar-141345.wav"};
    public int i_cancion = 0;
    public File cancion;
    public Clip clip;
    Cafetera cafetera;
    JFrame frame = new JFrame("Panel");
    public long tu_npc;
    public double dineroActual;
    public int i_destino = 0;

    public BufferedImage[] elegir_cafe_izq1 = new BufferedImage[4];
    public BufferedImage[] elegir_cafe_der1 = new BufferedImage[4];

    public Panel() {
        this.setPreferredSize(new Dimension(anchura, altura));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
        //Los personajes se dirigen a los destinos
        //Sillas de la izquierda
        destinos.add(new Point(100 - 25, 300 - 32));
        destinos.add(new Point(100 - 25, 500 - 32));
        destinos.add(new Point(600 - 25, 300 - 32));
        destinos.add(new Point(600 - 25, 500 - 32));

        //Sillas de la derecha
        destinos.add(new Point(300 - 25, 300 - 32));
        destinos.add(new Point(300 - 25, 500 - 32));
        destinos.add(new Point(800 - 25, 300 - 32));
        destinos.add(new Point(800 - 25, 500 - 32));

        canciones();
        inicializarTiempo();
        imagenes_NPC();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            esc.dibujar(g);
        } catch (FontFormatException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
        jg.dibujar(g);
        for (int i = 0; i < npcs.size(); i++) {
            NPC npc = npcs.get(i);
            npc.dibujar(g);
        }
        Font fuente;
        try {
            fuente = Font.createFont(Font.TRUETYPE_FONT, new File("./media/fuentes/Pixelade.ttf"));
            Font s_cafe = fuente.deriveFont(Font.PLAIN, 30);
            Font s_dinero = fuente.deriveFont(Font.PLAIN, 35);
            g.setFont(s_cafe);
            g.setColor(Color.WHITE);
            g.drawString("Café requerido: " + queDistancia(), 15, 25);
            g.setFont(s_dinero);

            this.setDineroActual(dineroActual);
            this.repaint();
            String txtdinero = dineroActual + "";
            int x_dinero = 880;
            int y_dinero = 650;

            g.drawString(txtdinero, x_dinero, y_dinero);
            Image imagen = ImageIO.read(new File("./media/extras/moneda.png"));
            int x_moneda = x_dinero - imagen.getWidth(null) - 10;
            int y_moneda = 620;
            g.drawImage(imagen, x_moneda, y_moneda, null);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public double getDineroActual() {
        return dineroActual;
    }

    public void setDineroActual(double dineroActual) {
        this.dineroActual = dineroActual;
    }

    Escenario esc = new Escenario(this);
    Thread tiempo, tiempo_npc;
    Jugador jg = new Jugador(this);
    NPC npc = new NPC(this);

    public void inicializarTiempo() {
        tiempo = new Thread(this);
        tiempo.start();
    }

    public void imagenes_NPC() {
        try {
            String[] pngs = {"ns-dam.png", "ns-des.png", "ns-dla.png", "ns-dma.png"};

            for (int i = 0; i < pngs.length; i++) {
                BufferedImage img = ImageIO.read(new File("./media/personajes/npc/npc1/" + pngs[i]));
                int n_anchura = (int) (img.getWidth() * 1.3);
                int n_altura = (int) (img.getHeight() * 1.3);
                BufferedImage tamanio_cambiado = new BufferedImage(n_anchura, n_altura, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = tamanio_cambiado.createGraphics();
                g2d.drawImage(img, 0, 0, n_anchura, n_altura, null);
                g2d.dispose();
                elegir_cafe_izq1[i] = tamanio_cambiado;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String[] pngs = {"ns-iam.png", "ns-ies.png", "ns-ila.png", "ns-ima.png"};

            for (int i = 0; i < pngs.length; i++) {
                BufferedImage img = ImageIO.read(new File("./media/personajes/npc/npc1/" + pngs[i]));
                int n_anchura = (int) (img.getWidth() * 1.3);
                int n_altura = (int) (img.getHeight() * 1.3);
                BufferedImage tamanio_cambiado = new BufferedImage(n_anchura, n_altura, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = tamanio_cambiado.createGraphics();
                g2d.drawImage(img, 0, 0, n_anchura, n_altura, null);
                g2d.dispose();
                elegir_cafe_der1[i] = tamanio_cambiado;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void canciones() {
        try {
            cancion = new File(canciones[i_cancion]);
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(cancion));
            clip.addLineListener(new javax.sound.sampled.LineListener() {
                public void update(javax.sound.sampled.LineEvent evt) {
                    if (evt.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                        siguienteCancion();
                    }
                }
            });
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void siguienteCancion() {
        clip.stop();
        i_cancion = (i_cancion + 1) % canciones.length;
        canciones();
    }

    public void run() {
        double intervalo = 1000000000 / fps;
        double tiempo2 = System.nanoTime() + intervalo;

        Thread th_jg = new Thread(() -> {
            while (tiempo != null) {
                jg.actualizar();
                jg.imagenes_jugador();
            }
        });
        th_jg.start();

        while (tiempo != null) {

            repaint();
            asignarValores();

            try {
                double cuantoTiempo = tiempo2 - System.nanoTime();
                cuantoTiempo = cuantoTiempo / 1000000;

                if (cuantoTiempo < 0) {
                    cuantoTiempo = 0;
                }
                Thread.sleep((long) cuantoTiempo);
                tiempo2 = tiempo2 + intervalo;
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    //Si la lista está vacía o el último npc no está en movimiento, se añade un npc.
    public boolean agregarNPC() {
        if (npcs.isEmpty() || System.currentTimeMillis() - tu_npc >= 10000) {
            return true;
        } else {
            return false;
        }
    }

    public String queDistancia() {
        if (!npcs.isEmpty()) {
            NPC npc_cercano = null;
            double min_distancia = Double.MAX_VALUE;

            for (int i = 0; i < npcs.size(); i++) {
                NPC npc1 = npcs.get(i);

                double distancia = Math.sqrt(Math.pow(jg.getX() - npc1.getDestinoX(), 3)
                        + Math.pow(jg.getY() - npc1.getDestinoY(), 3));

                if (distancia < min_distancia) {
                    min_distancia = distancia;
                    npc_cercano = npc1;

                }
            }

            if (npc_cercano != null) {
                return npc_cercano.getTipo();
            }
        }

        return "";
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        jg.teclas(e);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int soltado = e.getKeyCode();
        if (soltado == KeyEvent.VK_LEFT) {
            izquierda = false;
        }
        if (soltado == KeyEvent.VK_RIGHT) {
            derecha = false;
        }
        if (soltado == KeyEvent.VK_UP) {
            arriba = false;
        }
        if (soltado == KeyEvent.VK_DOWN) {
            abajo = false;
        }
    }
    //destino aleatorio de destinos y café aleatorio específico para cada personaje.
public void asignarValores() {
    if (agregarNPC()) {
        NPC n_NPC = new NPC(this);
        Point destino = null;
        BufferedImage cafe = null;
        
        //obtengo el destino en orden secuencial
        destino = destinos.get(i_destino);
        
        n_NPC.setDestino(destino.x, destino.y);

        if (destino.x == 775 && destino.y == 268 || destino.x == 775 && destino.y == 468
                || destino.x == 275 && destino.y == 268 || destino.x == 275 && destino.y == 468) {
            cafe = elegir_cafe_der1[i_destino % elegir_cafe_der1.length];
            n_NPC.setImagenAleatoria(cafe);
            switch (i_destino % elegir_cafe_der1.length) {
                case 0:
                    n_NPC.setTipo("Americano");
                    break;
                case 1:
                    n_NPC.setTipo("Espresso");
                    break;
                case 2:
                    n_NPC.setTipo("Latte");
                    break;
                case 3:
                    n_NPC.setTipo("Macchiato");
                    break;
                default:
                    break;
            }
        } else {
            cafe = elegir_cafe_izq1[i_destino % elegir_cafe_izq1.length];
            n_NPC.setImagenAleatoria(cafe);
            switch (i_destino % elegir_cafe_izq1.length) {
                case 0:
                    n_NPC.setTipo("Americano");
                    break;
                case 1:
                    n_NPC.setTipo("Espresso");
                    break;
                case 2:
                    n_NPC.setTipo("Latte");
                    break;
                case 3:
                    n_NPC.setTipo("Macchiato");
                    break;
                default:
                    break;
            }
        }
        npcs.add(n_NPC);
        i_destino = (i_destino + 1) % destinos.size(); //+1 índice de la lista
        tu_npc = System.currentTimeMillis();
    }
}

}
