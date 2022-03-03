package sk.fri.uniza;


import java.awt.*;
import java.io.Serializable;

/**
 * 27. 2. 2022 - 15:54
 *
 * @author richa
 */
public class Pozicia {
    private int x;
    private int y;

    public Pozicia(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
