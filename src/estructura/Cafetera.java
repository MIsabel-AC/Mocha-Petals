/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructura;

import static estructura.Titulo.conex;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import objetos.NPC;

public class Cafetera extends JFrame {

    private JPanel jpanel;
    private Panel panel;
    public NPC npc;
    String t_cafe = "";
    public double dineroActual;
    public boolean correcto;

    public Cafetera(Panel panel, NPC npc) {
        this.panel = panel;
        this.npc = npc;
        setTitle("Cafetera");
        setSize(400, 300);
        setLocationRelativeTo(null);
        crearTablas();
        jpanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("./media/fondos/cafeteramenu.png").getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JButton btn_Americano = new JButton("Americano");
        JButton btn_Latte = new JButton("Latte");
        JButton btn_Espresso = new JButton("Espresso");
        JButton btn_Macchiato = new JButton("Macchiato");
        btn_Americano.setBackground(new Color(153, 102, 51));
        btn_Americano.setForeground(Color.WHITE);
        btn_Latte.setBackground(new Color(153, 102, 51));
        btn_Latte.setForeground(Color.WHITE);
        btn_Espresso.setBackground(new Color(153, 102, 51));
        btn_Espresso.setForeground(Color.WHITE);
        btn_Macchiato.setBackground(new Color(153, 102, 51));
        btn_Macchiato.setForeground(Color.WHITE);

        jpanel.add(btn_Americano);
        jpanel.add(btn_Latte);
        jpanel.add(btn_Espresso);
        jpanel.add(btn_Macchiato);
        add(jpanel);
        setVisible(true);

        btn_Americano.addActionListener(e -> {
            t_cafe = "Americano";
            consultando_cafe(t_cafe);
            try {
                comprobar_dinero();
                panel.setDineroActual(dineroActual);
                panel.repaint();
            } catch (SQLException ex) {
                Logger.getLogger(Cafetera.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btn_Latte.addActionListener(e -> {
            t_cafe = "Latte";
            consultando_cafe(t_cafe);
            try {
                comprobar_dinero();
                panel.setDineroActual(dineroActual);
                panel.repaint();
            } catch (SQLException ex) {
                Logger.getLogger(Cafetera.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btn_Espresso.addActionListener(e -> {
            t_cafe = "Espresso";
            consultando_cafe(t_cafe);
            try {
                comprobar_dinero();
                panel.setDineroActual(dineroActual);
                panel.repaint();
            } catch (SQLException ex) {
                Logger.getLogger(Cafetera.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btn_Macchiato.addActionListener(e -> {
            t_cafe = "Macchiato";
            consultando_cafe(t_cafe);
            try {
                comprobar_dinero();
                panel.setDineroActual(dineroActual);
                panel.repaint();
            } catch (SQLException ex) {
                Logger.getLogger(Cafetera.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    public double consultarDineroEnBD() {
        double dinero = 0.0;
        conectar();
        try (Statement st = conex.createStatement();
                ResultSet rs = st.executeQuery("SELECT Cantidad FROM Dinero")) {
            if (rs.next()) {
                dinero = rs.getDouble("Cantidad");
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dinero;
    }

    public String consultando_cafe(String t_cafe) {
        try {
            conectar();
            Statement st = conex.createStatement();
            ResultSet rs = st.executeQuery("SELECT Compra FROM Cafes WHERE Tipo='" + t_cafe + "'");

            if (rs.next()) {
                double p_compra = rs.getDouble("Compra");

                ResultSet drs = st.executeQuery("SELECT Cantidad FROM Dinero");
                dineroActual = drs.getDouble("Cantidad");
                st.executeUpdate("UPDATE Dinero SET Cantidad = Cantidad - " + p_compra);
                drs = st.executeQuery("SELECT Cantidad FROM Dinero");
                dineroActual = drs.getDouble("Cantidad");
                panel.setDineroActual((int) dineroActual);

            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return t_cafe;
    }

    public void comparar_cafes() {
        String tipo = panel.queDistancia();

        if (tipo.equals(t_cafe)) {
            sumar_dinero(tipo);
        } else {
            restar_dinero(tipo);

        }
    }

    public void comprobar_dinero() throws SQLException {
        conectar();
        Statement st = conex.createStatement();
        ResultSet rs = st.executeQuery("SELECT Cantidad FROM Dinero");
        dineroActual = rs.getDouble("Cantidad");
        dineroActual = Math.round(dineroActual * 100.0) / 100.0;
        if (dineroActual <= 0.0) {
            st.executeUpdate("UPDATE Dinero SET Cantidad = 2.0");
            Fin fn = new Fin();
            panel.setVisible(false);
        }

        st.close();
    }

    public boolean sumar_dinero(String t_cafe) {
        try {
            conectar();
            Statement st = conex.createStatement();
            boolean dineroSumado = false;

            switch (t_cafe) {
                case "Americano":
                    ResultSet rs = st.executeQuery("SELECT Cantidad FROM Dinero");
                    dineroActual = rs.getDouble("Cantidad");
                    st.executeUpdate("UPDATE Dinero SET Cantidad = Cantidad + " + 1.20);
                    panel.setDineroActual(dineroActual);
                    comprobar_dinero();
                    panel.setDineroActual(dineroActual);
                    panel.repaint();
                    dineroSumado = true;
                    break;

                case "Macchiato":
                    rs = st.executeQuery("SELECT Cantidad FROM Dinero");
                    dineroActual = rs.getDouble("Cantidad");
                    st.executeUpdate("UPDATE Dinero SET Cantidad = Cantidad + " + 1.50);
                    panel.setDineroActual(dineroActual);
                    comprobar_dinero();
                    panel.setDineroActual(dineroActual);
                    panel.repaint();
                    dineroSumado = true;
                    break;

                case "Latte":
                    rs = st.executeQuery("SELECT Cantidad FROM Dinero");
                    dineroActual = rs.getDouble("Cantidad");
                    st.executeUpdate("UPDATE Dinero SET Cantidad = Cantidad + " + 1.75);
                    panel.setDineroActual(dineroActual);
                    comprobar_dinero();
                    panel.setDineroActual(dineroActual);
                    panel.repaint();
                    dineroSumado = true;

                    break;
                case "Espresso":
                    rs = st.executeQuery("SELECT Cantidad FROM Dinero");
                    dineroActual = rs.getDouble("Cantidad");
                    st.executeUpdate("UPDATE Dinero SET Cantidad = Cantidad + " + 1.50);
                    panel.setDineroActual(dineroActual);
                    comprobar_dinero();
                    panel.setDineroActual(dineroActual);
                    panel.repaint();
                    dineroSumado = true;
                    break;
            }

            conex.close();
            return dineroSumado;
        } catch (SQLException ex) {
            Logger.getLogger(Cafetera.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void restar_dinero(String t_cafe) {
        try {
            conectar();
            Statement st = conex.createStatement();
            switch (t_cafe) {
                case "Americano":
                    ResultSet rs = st.executeQuery("SELECT Cantidad FROM Dinero");
                    dineroActual = rs.getDouble("Cantidad");
                    st.executeUpdate("UPDATE Dinero SET Cantidad = Cantidad - " + 0.80);
                    panel.setDineroActual(dineroActual);
                    comprobar_dinero();
                    panel.setDineroActual(dineroActual);
                    panel.repaint();
                    break;

                case "Macchiato":
                    rs = st.executeQuery("SELECT Cantidad FROM Dinero");
                    dineroActual = rs.getDouble("Cantidad");
                    st.executeUpdate("UPDATE Dinero SET Cantidad = Cantidad - " + 1.00);
                    panel.setDineroActual(dineroActual);
                    comprobar_dinero();
                    panel.setDineroActual(dineroActual);
                    panel.repaint();
                    break;

                case "Latte":
                    rs = st.executeQuery("SELECT Cantidad FROM Dinero");
                    dineroActual = rs.getDouble("Cantidad");
                    st.executeUpdate("UPDATE Dinero SET Cantidad = Cantidad - " + 1.25);
                    panel.setDineroActual(dineroActual);
                    comprobar_dinero();
                    panel.setDineroActual(dineroActual);
                    panel.repaint();
                    break;

                case "Espresso":
                    rs = st.executeQuery("SELECT Cantidad FROM Dinero");
                    dineroActual = rs.getDouble("Cantidad");
                    st.executeUpdate("UPDATE Dinero SET Cantidad = Cantidad - " + 1.00);
                    panel.setDineroActual(dineroActual);
                    comprobar_dinero();
                    panel.setDineroActual(dineroActual);
                    panel.repaint();
                    break;

            }
            conex.close();

        } catch (SQLException ex) {
            Logger.getLogger(Cafetera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crearTablas() {
        Statement st = null;
        ResultSet rs = null;

        try {
            conectar();
            st = conex.createStatement();
            rs = st.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Cafes'");

            if (!rs.next()) {
                st.executeUpdate("CREATE TABLE Cafes (Tipo TEXT, Compra NUMERIC, Venta NUMERIC)");
                st.executeUpdate("INSERT INTO Cafes VALUES ('Americano', 0.20, 1.20),"
                        + "('Latte', 0.75, 1.75),"
                        + "('Espresso', 0.50, 1.50),"
                        + "('Macchiato', 0.50, 1.50)");
                st.executeUpdate("CREATE TABLE Dinero (Cantidad NUMERIC)");
                st.executeUpdate("INSERT INTO Dinero VALUES (2.00)");
            }
        } catch (SQLException e) {
            System.out.println("Error al crear las tablas.");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void conectar() {
        try {
            conex = DriverManager.getConnection("jdbc:sqlite:./src/bdd/MochaPetals.db");
        } catch (SQLException ex) {
            Logger.getLogger(Cafetera.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
