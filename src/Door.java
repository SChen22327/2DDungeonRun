import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Door {
    private double xCoord;
    private double yCoord;
    private BufferedImage staticIMG;
    private Animation open;
    public Door() {
        ArrayList<BufferedImage> arr = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            try {
                arr.add(ImageIO.read(new File("assets/Dungeon Gathering/Block Door/door_open/tile00" + i + ".png")));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        open = new Animation(arr,50);
        staticIMG = open.getActiveFrame();
    }

    public void open() {

    }
    public Rectangle doorRect() {
        int imageHeight = open.getActiveFrame().getHeight();
        int imageWidth = open.getActiveFrame().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}
