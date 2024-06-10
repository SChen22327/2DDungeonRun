import java.awt.image.BufferedImage;
import java.io.File;

public class Map {
    private BufferedImage img;
    private File walkable;
    public Map(BufferedImage img, File walkable) {
        this.img = img;
        this.walkable = walkable;
    }

    public BufferedImage getBG() {
        return img;
    }

    public File getWalkable() {
        return walkable;
    }
}
