import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    private Player player;
    public Enemy(int xCoord, int yCoord, Player player) {
        super(xCoord, yCoord, "assets/Dino", 3, 3.5);
        this.player = player;
        createAnimations();
    }
    public Enemy(int xCoord, int yCoord, Player player, boolean b) {
        super(xCoord, yCoord, "assets/Dino", 5, 3.5);
        this.player = player;
        createAnimations();
    }

    public void createAnimations() {
        super.createAnimations();
    }

    public void move() {
        if (checkVicinity()) {
            double diffX = player.xCoord - xCoord;
            double diffY = player.yCoord - yCoord;
            float angle = (float) Math.atan2(diffY, diffX);
            if (!hurt()) {
                xCoord += MOVE_AMT * Math.cos(angle);
                yCoord += MOVE_AMT * Math.sin(angle);
                if (angle > Math.PI / 2 || angle < -(Math.PI / 2)) {
                    sendState("left;walk");
                } else {
                    sendState("right;walk");
                }
            } else {
                if (angle > 0 && super.canMove(3)) {
                    yCoord -= KB_AMT * Math.sin(angle);
                } else if (angle < 0 && super.canMove(4)) {
                    yCoord -= KB_AMT * Math.sin(angle);
                }

                if ((angle > Math.PI / 2 || angle < -Math.PI / 2) && super.canMove(1)) {
                    xCoord -= KB_AMT * Math.cos(angle);
                } else if ((angle < Math.PI / 2 || angle > -Math.PI / 2) && super.canMove(2)) {
                    xCoord -= KB_AMT * Math.cos(angle);
                }
            }
        } else {
            sendState(getDir() + ";idle");
        }
    }

    private boolean checkVicinity() {
        return Math.abs(xCoord - player.xCoord) <= 192 && Math.abs(yCoord - player.yCoord) <= 192;
    }

    public BufferedImage getEnemyImage() {
        return currentAnimation.getActiveFrame();
    }
    @Override
    public Rectangle rect() {
        return new Rectangle((int) xCoord + 6 - getWidth() / 2, (int) yCoord + 6 - getHeight() / 2, 36, 36);
    }

}
