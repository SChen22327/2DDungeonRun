import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private final double MOVE_AMT = 0.08;
    private double xCoord;
    private double yCoord;
    private String state;
    private ArrayList<AnimationInfo> animations;
    private Animation currentAnimation;
    public Player() {
        state = "idle";
        xCoord = 15;
        yCoord = 15;
        animations = new ArrayList<AnimationInfo>();
        createAnimations();
    }

    private void createAnimations() {
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
    }
    private Animation getAnimation(String name) {
        for (AnimationInfo a : animations) {
            if (a.getName().equals(name)) {
                return a.getAnimation();
            }
        }
        return currentAnimation;
    }
    public int getxCoord() {
        return (int) xCoord;
    }

    public int getyCoord() {
        return (int) yCoord;
    }
    public void checkState() {
        if (state.equals("idle")) {
            currentAnimation = getAnimation("idle");
            currentAnimation.play();
        }
        if (state.equals("left") || state.equals("right") || state.equals("up") || state.equals("down")) {
            currentAnimation = getAnimation("walk");
            currentAnimation.play();
        }
    }
    public void sendState(String s) {
        state = s;
    }
    public String getState() {
        return state;
    }
    public void moveRight() {
        if (xCoord + MOVE_AMT <= 920) {
            xCoord += MOVE_AMT;
        }
    }

    public void moveLeft() {
        if (xCoord - MOVE_AMT >= 0) {
            xCoord -= MOVE_AMT;
        }
    }

    public void moveUp() {
        if (yCoord - MOVE_AMT >= 0) {
            yCoord -= MOVE_AMT;
        }
    }

    public void moveDown() {
        if (yCoord + MOVE_AMT <= 435) {
            yCoord += MOVE_AMT;
        }
    }

    public BufferedImage getPlayerImage() {
        return currentAnimation.getActiveFrame();
    }
    public int getHeight() {
        return getPlayerImage().getHeight();
    }

    public int getWidth() {
        return getPlayerImage().getWidth();
    }

    // we use a "bounding Rectangle" for detecting collision
    public Rectangle playerRect() {
        int imageHeight = getPlayerImage().getHeight();
        int imageWidth = getPlayerImage().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}
