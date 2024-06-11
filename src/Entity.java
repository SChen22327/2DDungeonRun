import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    private double xCoord;
    private double yCoord;
    private BufferedImage img;
    public Entity(double xCoord, double yCoord, BufferedImage img) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.img = img;
    }
    public BufferedImage getImg() {
        return img;
    }
    public double getxCoord() {
        return xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }
    public Rectangle rect() {
        return new Rectangle();
    }
}
