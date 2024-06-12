import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Door extends Tile {
    private BufferedImage open;
    private BufferedImage closed;
    public Door(double x, double y){
        //accidentally swapped them around and too lazy to fix
        super(y, x, 2);
        try {
            open = ImageIO.read(new File("assets/Dungeon Gathering/door.png"));
            closed = ImageIO.read(new File("assets/Dungeon Gathering/closed.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public BufferedImage getImg() {
        return open;
    }

    public Rectangle doorRect() {
        int imageHeight = open.getHeight();
        int imageWidth = open.getWidth();
        Rectangle rect = new Rectangle(getxCoord(), getyCoord() - 5 + imageHeight, imageWidth, 3);
        return rect;
    }
}
