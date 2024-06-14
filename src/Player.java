import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity {
    private Weapon weapon;
    public boolean invincible;
    public int counter;
    public Player() {
        super(144, 144, "assets/Wolf", 6, 7);
        weapon = new Weapon();
        state = "right;idle";
        invincible = false;
        counter = 0;
        reset();
        createAnimations();
    }
    public void newMap() {
        reset();
    }
    public void createAnimations() {
        super.createAnimations();
        //attack
        ArrayList<BufferedImage> attack = new ArrayList<>();
        for (File f : new File(filepath + "/attack/").listFiles()) {
            try {
                attack.add(ImageIO.read(f));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        animations.add(new AnimationInfo("attack", new Animation(attack,10)));
    }

    @Override
    public void takeDMG() {
        super.takeDMG();
        invincible = true;
    }

    public void reset() {
        if (GraphicsPanel.map == 0) {
            xCoord = 288;
            yCoord = 288;
        }
        if (GraphicsPanel.map == 1) {
            xCoord = 528;
            yCoord = 624;
        }

    }
    public void sendState(String s) {
        state = s;
        if (getState().equals("death")) {
            currentAnimation = getAnimation("death");
            currentAnimation.play();
        } else if (getState().equals("hurt")) {
            currentAnimation = getAnimation("hurt");
            currentAnimation.play();
        } else if (getState().equals("attack")) {
            currentAnimation = getAnimation("attack");
            currentAnimation.play();
        } else if (getState().equals("walk")) {
            currentAnimation = getAnimation("walk");
            currentAnimation.play();
        } else if (getState().equals("idle")) {
            currentAnimation = getAnimation("idle");
            currentAnimation.play();
        }
    }
    public boolean attacking() {
        return !currentAnimation.finished() && currentAnimationName().equals("attack");
    }

    public BufferedImage getPlayerImage() {
        return currentAnimation.getActiveFrame();
    }

    public Rectangle attack() {
        String dir = getDir();
        sendState(dir + ";attack");
        if (dir.equals("left")) {
            return weapon.getRect((int) xCoord - getWidth() - 10,(int) yCoord - getHeight() / 4);
        }
        return weapon.getRect((int) xCoord - 10, (int) yCoord - getHeight() / 4);
    }
    public Rectangle playerRect() {
        return new Rectangle((int) xCoord - getWidth() / 2, (int) yCoord - getHeight() / 4, 17, 26);
    }
}
