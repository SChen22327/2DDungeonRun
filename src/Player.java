import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity {
    private Weapon weapon;
    public Player() {
        super(144, 144, "assets/Wolf animations/wolf_Static.png", 6, 0.2);
        weapon = new Weapon();
        state = "right;idle";
        reset();
        createAnimations();
    }

    public void createAnimations() {
        animations = new ArrayList<AnimationInfo>();
        Animation newAnimation;
        //idle
        ArrayList<BufferedImage> idle = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String filename = "assets/Wolf animations/wolf_idle/tile00" + i + ".png";
            try {
                idle.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        newAnimation = new Animation(idle, 20);
        animations.add(new AnimationInfo("idle", newAnimation));
        currentAnimation = newAnimation;
        // walk
        ArrayList<BufferedImage> walk = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String filename = "assets/Wolf animations/wolf_walk/tile00" + i + ".png";
            try {
                walk.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        newAnimation = new Animation(walk,20);
        animations.add(new AnimationInfo("walk", newAnimation));
        //attack
        ArrayList<BufferedImage> attack = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String filename = "assets/Wolf animations/wolf_attack/tile00" + i + ".png";
            try {
                attack.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        newAnimation = new Animation(attack,10);
        animations.add(new AnimationInfo("attack", newAnimation));
        //hurt
        ArrayList<BufferedImage> hurt = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String filename = "assets/Wolf animations/wolf_damage/tile00" + i + ".png";
            try {
                hurt.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        newAnimation = new Animation(hurt, 20);
        animations.add(new AnimationInfo("hurt", newAnimation));
        //death
        ArrayList<BufferedImage> death = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String filename = "assets/Wolf animations/wolf_death/tile00" + i + ".png";
            try {
                death.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        newAnimation = new Animation(death, 20);
        animations.add(new AnimationInfo("death", newAnimation));
    }

    public void newMap(ArrayList<ArrayList<Tile>> t) {
        walkable = t;
        reset();
    }

    public Rectangle attack() {
        sendState(getDir() + ";attack");
        return weapon.getRect((int) xCoord,(int) yCoord);
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

    // we use a "bounding Rectangle" for detecting collision
    public Rectangle playerRect() {
        Rectangle rect = new Rectangle((int) xCoord - getWidth() / 2, (int) yCoord - getHeight() / 4, 17, 26);
        return rect;
    }
}
