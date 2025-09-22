package objetos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

 public  class Muebles {
    public int x;
    public int y;
    public int ancho;
    public int alto;

    public Muebles(int x, int y, int ancho, int alto) {
    this.x = x;
    this.y = y;
    this.ancho = ancho;
    this.alto = alto;
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

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }


public  void dibujar(Graphics2D g2d) {
    g2d.setColor(Color.WHITE);
    g2d.fillRect(x, y, ancho, alto);

    

}

public Rectangle getRectangulo() {
    return new Rectangle(x, y, ancho, alto);
}

}
