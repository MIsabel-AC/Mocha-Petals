/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructura;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Titulo {

    public static Clip musica = null;
    public static Connection conex;
    Cafetera cafetera;

    public static void main(String[] args) {
        JFrame inicio = new JFrame();
        inicio.setTitle("Mocha Petals");
        inicio.setResizable(false);
        inicio.setSize(700, 500);
        inicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicio.setLayout(new BorderLayout());

        //música
        try {
            File musicaFile = new File("./media/musica/titulo/wav/forgotten-melody-148771.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicaFile);
            musica = AudioSystem.getClip();
            musica.open(audioInput);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        musica.loop(Clip.LOOP_CONTINUOUSLY);
        inicio.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                musica.stop();
            }

        });

        //Panel para contener los botones
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
        btnPanel.setOpaque(false);
        inicio.add(btnPanel, BorderLayout.WEST);

        JButton btn = new JButton("Comenzar");
        btn.setBackground(new Color(153, 102, 51));
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(92, 30));

        btnPanel.add(btn);

        //Espacio entre botones
        btnPanel.add(Box.createVerticalStrut(10));

        JButton btn_ayuda = new JButton("Ayuda");
        btn_ayuda.setBackground(new Color(153, 102, 51));
        btn_ayuda.setForeground(Color.WHITE);
        btn_ayuda.setPreferredSize(new Dimension(0, 30));
        btnPanel.add(btn_ayuda);

        btnPanel.add(Box.createVerticalStrut(10));

        JButton btn_salir = new JButton("Salir");
        btn_salir.setBackground(new Color(153, 102, 51));
        btn_salir.setForeground(Color.WHITE);
        btn_salir.setPreferredSize(new Dimension(0, 30));
        btnPanel.add(btn_salir);

        //Imagen en el centro de la ventana
        JLabel lbl_img = new JLabel();
        ImageIcon ico_img = new ImageIcon("./media/fondos/fondo.png");
        lbl_img.setIcon(ico_img);
        inicio.add(lbl_img, BorderLayout.CENTER);

        inicio.setLocationRelativeTo(null);
        inicio.setVisible(true);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musica.stop();
                inicio.dispose();
                JFrame ventana = new JFrame();
                ventana.setTitle("Mocha Petals");
                ventana.setResizable(false);
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                Panel panel = new Panel();
                ventana.add(panel);
                ventana.pack();
                ventana.setLocationRelativeTo(null);
                ventana.setVisible(true);
            }
        });

        btn_ayuda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Guia guia = new Guia();

            }
        });

        btn_salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }
}
