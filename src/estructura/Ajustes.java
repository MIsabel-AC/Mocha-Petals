/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructura;

/**
 *
 * @author isabel
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Ajustes extends JFrame {

    public Clip musica;
    public JSlider slider;
    public Panel panel;

    public Ajustes(Clip musica, Panel panel) {
        this.musica = musica;
        this.panel = panel;
        setTitle("Ajustes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 200);

        JPanel jpanel = new JPanel();
        jpanel.setBackground(new Color(153, 102, 51));
        setContentPane(jpanel);

        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider.setPreferredSize(new Dimension(200, 30));
        slider.setUI(new CustomSliderUI()); 

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int volumen = slider.getValue();
                float vol = volumen / 100.0f;
                if (musica.isOpen()) {
                    FloatControl control = (FloatControl) musica.getControl(FloatControl.Type.MASTER_GAIN);
                    control.setValue(20 * (float) Math.log10(vol));
                }
            }
        });

        JLabel lblvolumen = new JLabel("Volumen:");
        lblvolumen.setForeground(Color.WHITE); 

        jpanel.add(lblvolumen);
        jpanel.add(slider);

        setVisible(true);
    }

    
    private class CustomSliderUI extends javax.swing.plaf.basic.BasicSliderUI {
        public CustomSliderUI() {
            super(null);
        }

        public void dibujar(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(255, 209, 220)); 
            g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
            g2d.dispose();
        }

        public Color getTrackColor() {
            return new Color(255, 209, 220); 
        }
    }
}