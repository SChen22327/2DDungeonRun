import java.awt.image.BufferedImage;

public class Map {
    private BufferedImage img;
    private BufferedImage walkable;
    public Map(BufferedImage img, BufferedImage walkable) {
        this.img = img;
        this.walkable = walkable;
    }

    public BufferedImage getBG() {
        return img;
    }

    public BufferedImage getWalkable() {
        return walkable;
    }
}
