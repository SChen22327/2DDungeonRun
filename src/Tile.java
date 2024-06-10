import java.awt.*;

public class Tile {
    private double xCoord;
    private double yCoord;
    private int type;
    private boolean collision;
    public Tile(double x, double y, int i) {
        // 0 is non walkable area
        // 1 is walkable area
        // 2 is door
        xCoord = x;
        yCoord = y;
        type = i;
        if (i == 0) {
            collision = true;
        } else {
            collision = false;
        }
    }
    // if false, player has NOT collided
    WHY WONT YOU WORK
    public boolean collided(Rectangle rect, int i) {
        Rectangle r = rect;
        switch (i) {
            case 1:
                r.x++;
            case 2:
                r.x--;
            case 3:
                r.y--;
            case 4:
                r.y++;
        }
        if (tileRect() == null) {
            return false;
        }
        return tileRect().intersects(rect);
    }
    public Rectangle tileRect() {
        if (collision) {
            return new Rectangle((int) xCoord, (int) yCoord, 48, 48);
        }
        return null;
    }
    @Override
    public String toString() {
        return type + "";
    }
}
