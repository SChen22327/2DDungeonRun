import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Door extends Entity {
    private Animation open;
    public Door(double xCoord, double yCoord, BufferedImage img) {
        super(xCoord, yCoord, img);
    }

    public Rectangle doorRect() {
        int imageHeight = open.getActiveFrame().getHeight();
        int imageWidth = open.getActiveFrame().getWidth();
        Rectangle rect = new Rectangle((int) getxCoord(), (int) getyCoord(), imageWidth, imageHeight);
        return rect;
    }
}
