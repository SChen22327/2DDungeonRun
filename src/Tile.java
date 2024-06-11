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
    public boolean collided() {
        return collision;
    }

    public int getxCoord() {
        return (int) xCoord;
    }

    public int getyCoord() {
        return (int)yCoord;
    }

    public Rectangle tileRect() {
        if (collision) {
            return new Rectangle((int) xCoord, (int) yCoord, 48, 48);
        }
        return null;
    }
    @Override
    public String toString() {
        switch (type) {
            case 0:
                return "wall";
            case 1:
                return "walk";
            case 2:
                return "door";
            default:
                return "tile";
        }
    }
}
