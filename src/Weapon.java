import java.awt.*;

public class Weapon {
    public Weapon() {}

    public Rectangle getRect(int x, int y) {
        return new Rectangle(x,y, 1,30);
    }
}
