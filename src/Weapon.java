import java.awt.*;

public class Weapon {
    private int dmg;
    public Weapon() {
        dmg = 1;
    }

    public Rectangle getRect(int x, int y) {
        return new Rectangle(x,y, 36,30);
    }
}
